package com.silence.akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import scala.io.StdIn
import akka.http.scaladsl.server.Directives._

object Server extends App {

    implicit val actorSystem = ActorSystem("akka-system")
    implicit val flowMaterializer = ActorMaterializer()

    val interface = "localhost"
    val port = 8080


    val route = get {
        pathEndOrSingleSlash {
            complete("Welcome to websocket server")
        }
    }
    
    val binding = Http().bindAndHandle(route, interface, port)
    println(s"Server is now online at http://$interface:$port\nPress RETURN to stop...")
    StdIn.readLine()

    import actorSystem.dispatcher

    binding.flatMap(_.unbind()).onComplete(_ => actorSystem.shutdown())
    println("Server is down...")

}