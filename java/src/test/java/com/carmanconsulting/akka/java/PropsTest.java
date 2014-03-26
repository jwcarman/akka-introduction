package com.carmanconsulting.akka.java;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Creator;
import org.junit.Test;

public class PropsTest extends AkkaTestCase {

    @Test
    public void testWithNoArguments() {
        ActorRef hello = system().actorOf(Props.create(ParameterizedHelloAkka.class), "hello");
        hello.tell("Akka", testActor());
        expectMsg("Hello, Akka!");
    }

    @Test
    public void testWithArguments() {
        ActorRef hello = system().actorOf(Props.create(ParameterizedHelloAkka.class, "Hola, %s!"), "hello");
        hello.tell("Akka", testActor());
        expectMsg("Hola, Akka!");
    }

    @Test
    public void testWithCreator() {
        ActorRef hello = system().actorOf(Props.create(new HelloAkkaActorCreator()), "hello");
        hello.tell("Akka", testActor());
        expectMsg("Bonjour, Akka!");
    }

    public static class HelloAkkaActorCreator implements Creator<ParameterizedHelloAkka> {
        @Override
        public ParameterizedHelloAkka create() throws Exception {
            return new ParameterizedHelloAkka("Bonjour, %s!");
        }
    }
}
