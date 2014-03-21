package com.carmanconsulting.akka.scala

import akka.actor.Actor

class HelloAkka extends Actor {
  override def receive: Receive = {
    case name =>
      sender ! s"Hello, $name!"
  }
}
