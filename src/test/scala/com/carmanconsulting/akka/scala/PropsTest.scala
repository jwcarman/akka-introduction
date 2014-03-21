package com.carmanconsulting.akka.scala

import org.junit.Test
import akka.actor.Props

class PropsTest extends AkkaTestCase {
  @Test
  def testWithNoArguments(): Unit = {
    val hello = system.actorOf(Props[ParameterizedHelloAkka], "hello")
    hello ! "Akka"
    expectMsg("Hello, Akka!")
  }

  @Test
  def testWithArguments(): Unit = {
    val hello = system.actorOf(Props(classOf[ParameterizedHelloAkka], "Hola, %s!"), "hello")
    hello ! "Akka"
    expectMsg("Hola, Akka!")
  }

  @Test
  def testWithCreator(): Unit = {
    val hello = system.actorOf(Props(PropsTest.create()), "hello")
    hello ! "Akka"
    expectMsg("Bonjour, Akka!")
  }
}

object PropsTest {
  def create() = {
    new ParameterizedHelloAkka("Bonjour, %s!")
  }
}
