package com.carmanconsulting.akka.java;

import akka.actor.ActorRef;
import akka.actor.Props;
import org.junit.Test;

public class HelloAkkaTest extends AkkaTestCase {

    @Test
    public void testHelloAkka() {
        ActorRef hello = system().actorOf(Props.create(HelloAkka.class));
        hello.tell("Akka", testActor());
        expectMsg("Hello, Akka!");
    }
}
