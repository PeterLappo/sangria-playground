package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import models.FriendsResolver
import play.api._
import play.api.libs.json._
import play.api.mvc._
import sangria.integration.playJson._
import sangria.introspection.introspectionQuery
//import uk.gov.hmrc.play.http.{ HttpGet, HttpPost }
import scala.concurrent.Future
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.ws.ning.NingAsyncHttpClientConfigBuilder


case class Vertex(name:String, id:Long)
case class Edge(source:Long, target:Long)
case class Graph(nodes:List[Vertex], edges:List[Edge])

object ToFromJson {
    implicit val edgeReads = Json.reads[Edge]
    implicit val edgeWrites = Json.writes[Edge]
    implicit val vertexReads = Json.reads[Vertex]
    implicit val vertexWrites = Json.writes[Vertex]
    implicit val graphReads = Json.reads[Graph]
    implicit val grapgWrites = Json.writes[Graph]
}

class DemoController @Inject() (system: ActorSystem) extends Controller { //  with HttpGet with HttpPost
  import system.dispatcher
  //implicit val ec = scala.concurrent.ExecutionContext.global

  def graph = Action.async  { implicit request =>
    //log.info("UserController.getInstruments: ")
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
    val vertices = List(Vertex("name1", 1), Vertex("name2", 2))
    val edges = List(Edge(1, 1), Edge(2, 2))
    val graph = Graph(vertices, edges)
    //val e1 = Edge("name", 1)
    import ToFromJson._
    val e2 = Json.toJson(graph)
    val e3 = Json.parse(data)
    var url = "http://ojuba.org:9910/graphs/demo"
    val holder: WSRequestHolder = WS.url(url)
    val futureResponse: Future[WSResponse] = holder.get()
    //val e3 = Json.toJson(vertices)
    //val json = Json.fromJson(data)
    //val xx = graphs.map(x => x.json)
    //Future(Ok(Json.toJson("hello world")))
    //Future(Ok(Json.toJson(e3)))
    // type mismatch; found : scala.concurrent.Future[play.api.libs.ws.WSResponse] required: scala.concurrent.Future[play.api.mvc.Result]
    //val xx:scala.concurrent.Future[play.api.mvc.Result] = 
    futureResponse.map(res => Ok(res.json))
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