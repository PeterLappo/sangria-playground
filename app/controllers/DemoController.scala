package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import play.api._
import play.api.libs.json._
import play.api.mvc._

import sangria.execution.Executor
import sangria.introspection.introspectionQuery
import sangria.parser.{SyntaxError, QueryParser}
import sangria.integration.playJson._

import models.{FriendsResolver, CharacterRepo, SchemaDefinition}
import sangria.renderer.SchemaRenderer

import scala.concurrent.Future
import scala.util.{Failure, Success}

class DemoController @Inject() (system: ActorSystem) extends Controller {
  import system.dispatcher
  
  def graph = Action { implicit request =>
    //log.info("UserController.getInstruments: ")
    val data = """
        {
	        nodes: [
                { name: "Adam" },
                { name: "Bob" },
                { name: "Carrie" },
                { name: "Donovan" },
                { name: "Edward" },
                { name: "Felicity" },
                { name: "George" },
                { name: "Hannah" },
                { name: "Iris" },
                { name: "Jerry" }
                ],
            edges: [
                { source: 0, target: 1 },
                { source: 0, target: 2 },
                { source: 0, target: 3 },
                { source: 0, target: 4 },
                { source: 1, target: 5 },
                { source: 2, target: 5 },
                { source: 2, target: 5 },
                { source: 3, target: 4 },
                { source: 5, target: 8 },
                { source: 5, target: 9 },
                { source: 6, target: 7 },
                { source: 7, target: 8 },
                { source: 8, target: 9 }
            ]
        }      """
    //val json = Json.fromJson(data)
    Ok(data)
  }

  
}