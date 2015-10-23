package controllers

import scala.concurrent.Future

import akka.actor.ActorSystem
import javax.inject.Inject
import play.api._
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._
import sangria.integration.playJson._
import sangria.introspection.introspectionQuery
import play.api.libs.functional.syntax._
import org.json4s._
import org.json4s.native.JsonMethods

case class Vertex(name:String, id:Long)
case class Edge(source:Long, target:Long)
case class Graph(nodes:List[Vertex], edges:List[Edge])

case class D3Vertex(name:String, id:Option[Long], performances:Option[Long])
case class D3Edge(source:Long, target:Long, id:Option[Long], label:Option[String])
case class D3Graph(nodes:List[D3Vertex], edges:List[D3Edge])

case class RexsVertices(version:String, results:List[RexsVertex])
case class RexsEdges(version:String, results:List[RexsEdge])
case class RexsVertex(name:Option[String], _id:Option[String], performances:Option[String]) {
  def toD3:Option[D3Vertex] = {
    name.map(n => D3Vertex(n, _id.map[Long](x => x.toLong), performances.map[Long](x => x.toLong)))
  }
}
case class RexsEdge(_outV:Option[String], _inV:Option[String], _label:Option[String], _id:Option[String]) {
  def toD3:Option[D3Edge] = {
    for {
      source <- _outV
      target <- _inV
      if (source.toLong >= 0 && target.toLong >=0)
    } yield {
      D3Edge(source.toLong, target.toLong, _id.map[Long](x => x.toLong), _label)
    }
  }
}

object ToFromJson {
    implicit val edgeReads = Json.reads[Edge]
    implicit val edgeWrites = Json.writes[Edge]
    implicit val vertexReads = Json.reads[Vertex]
    implicit val vertexWrites = Json.writes[Vertex]
    implicit val graphReads = Json.reads[Graph]
    implicit val graphWrites = Json.writes[Graph]

    implicit val redgeReads = Json.reads[RexsEdge]
    implicit val redgeWrites = Json.writes[RexsEdge]
    
    implicit val dr3vertexWrites = Json.writes[D3Vertex]
    implicit val dr3edgeWrites = Json.writes[D3Edge]
    implicit val dr3graphWrites = Json.writes[D3Graph]
}

class DemoController @Inject() (system: ActorSystem) extends Controller {
  import system.dispatcher


  def graph = Action.async  { implicit request =>
    import ToFromJson._
    implicit val formats = DefaultFormats
    //val base = "http://ojuba.org:9915"
    val base = "http://localhost:8182"
    val vertexurl = base + "/graphs/gratefulgraph/vertices"
    val edgeurl = base + "/graphs/gratefulgraph/edges"
    val vertexResponseF: Future[WSResponse] = WS.url(vertexurl).get()
    val edgeResponseF: Future[WSResponse] = WS.url(edgeurl).get()
    for {
      vertexResponse <- vertexResponseF
      edgeResponse <- edgeResponseF
    } yield {
      val rvertices = JsonMethods.parse(vertexResponse.body).extract[RexsVertices]
      val redges = JsonMethods.parse(edgeResponse.body).extract[RexsEdges]
      val vertices = rvertices.results.collect { case x:RexsVertex => x.toD3 }.flatten
      val edges = redges.results.collect { case x:RexsEdge => x.toD3 }.flatten
      val graph = D3Graph(vertices, edges)
      //println("vertices " + vertices.toString())
      //println("edges " + edges.toString())
      Ok(Json.toJson(graph))
    }
  }

   def mockgraph = Action.async  { implicit request =>
    val data = """
        {
	        "nodes": [
                { "name": "Adam" },
                { "name": "Bob" },
                { "name": "Carrie" },
                { "name": "Donovan" },
                { "name": "Edward" },
                { "name": "Felicity" },
                { "name": "George" },
                { "name": "Hannah" },
                { "name": "Iris" },
                { "name": "Jerry" }
                ],
            "edges": [
                { "source": 0, "target": 1 },
                { "source": 0, "target": 2 },
                { "source": 0, "target": 3 },
                { "source": 0, "target": 4 },
                { "source": 1, "target": 5 },
                { "source": 2, "target": 5 },
                { "source": 2, "target": 5 },
                { "source": 3, "target": 4 },
                { "source": 5, "target": 8 },
                { "source": 5, "target": 9 },
                { "source": 6, "target": 7 },
                { "source": 7, "target": 8 },
                { "source": 8, "target": 9 }
            ]
        }      """
    import ToFromJson._
    val e3 = Json.parse(data)
    Future(Ok(Json.toJson(e3)))
  }
 
}

/*
vertices look like this

 {
  "version": "2.6.0",
  "results": [
    {
      "_id": "0",
      "_type": "vertex"
    },
    {
      "song_type": "cover",
      "name": "HEY BO DIDDLEY",
      "type": "song",
      "performances": 5,
      "_id": "1",
      "_type": "vertex"
    },
    {
      "song_type": "original",
      "name": "IT MUST HAVE BEEN THE ROSES",
      "type": "song",
      "performances": 159,
      "_id": "98",
      "_type": "vertex"
    },
    {
      "song_type": "original",
      "name": "MIGHT AS WELL",
      "type": "song",
      "performances": 111,
      "_id": "99",
      "_type": "vertex"
    }
  ],
  "totalSize": 809,
  "queryTime": 28.612934
}

edges look like this

{
  "version": "2.6.0",
  "results": [
    {
      "weight": 1,
      "_id": "4970",
      "_type": "edge",
      "_outV": "236",
      "_inV": "4",
      "_label": "followed_by"
    },
    {
      "weight": 3,
      "_id": "3640",
      "_type": "edge",
      "_outV": "24",
      "_inV": "72",
      "_label": "followed_by"
    },
    {
      "weight": 1,
      "_id": "3627",
      "_type": "edge",
      "_outV": "24",
      "_inV": "235",
      "_label": "followed_by"
    }
  ],
  "totalSize": 8049,
  "queryTime": 220.536702
}
   */
//    val vertices = List(Vertex("name1", 1), Vertex("name2", 2))
//    val edges = List(Edge(1, 1), Edge(2, 2))
//    val graph = Graph(vertices, edges)
//    //val e1 = Edge("name", 1)
//    val e2 = Json.toJson(graph)
    //val e3 = Json.toJson(vertices)
    //val json = Json.fromJson(data)
    //val xx = graphs.map(x => x.json)
    //Future(Ok(Json.toJson("hello world")))
    //Future(Ok(Json.toJson(e3)))
    // type mismatch; found : scala.concurrent.Future[play.api.libs.ws.WSResponse] required: scala.concurrent.Future[play.api.mvc.Result]
    //val xx:scala.concurrent.Future[play.api.mvc.Result] = 
