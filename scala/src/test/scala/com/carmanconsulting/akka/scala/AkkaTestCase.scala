package com.carmanconsulting.akka.scala

import akka.testkit.{ImplicitSender, TestKit}
import akka.actor.ActorSystem
import org.junit.After

abstract class AkkaTestCase extends TestKit(ActorSystem("AkkaTesting")) with ImplicitSender {

  @After
  def shutdownActorSystem(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

}
