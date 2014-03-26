package com.carmanconsulting.akka.scala

import akka.actor.Actor

class ParameterizedHelloAkka(format: String) extends Actor {

  def this() = {
    this("Hello, %s!")
  }

  override def receive: Receive = {
    case name: String =>
      val message: String = String.format(format, name)
      sender ! message
  }
}
