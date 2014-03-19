package com.carmanconsulting.akka.scala

import akka.actor.Props
import org.junit.Test

class HelloAkkaTest extends AkkaTestCase {

  @Test
  def testHelloAkka(): Unit = {
    val hello = system.actorOf(Props[HelloAkka])
    hello ! "Akka"
    expectMsg("Hello, Akka!")
  }
}
