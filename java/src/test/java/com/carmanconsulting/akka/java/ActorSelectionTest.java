package com.carmanconsulting.akka.java;

import akka.actor.ActorIdentity;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import akka.actor.Props;
import org.junit.Test;
import scala.collection.immutable.Seq;

import static org.junit.Assert.*;

public class ActorSelectionTest extends AkkaTestCase {

    @Test
    public void testSelectingOneActor() {
        system().actorOf(Props.create(ParameterizedHelloAkka.class), "english");
        system().actorOf(Props.create(ParameterizedHelloAkka.class, "Bonjour, %s!"), "french");

        final ActorSelection selection = system().actorSelection("/user/english");
        selection.tell("Akka", testActor());
        expectMsg("Hello, Akka!");
    }

    @Test
    public void testSelectingMultipleActors() {
        system().actorOf(Props.create(ParameterizedHelloAkka.class), "english");
        system().actorOf(Props.create(ParameterizedHelloAkka.class, "Bonjour, %s!"), "french");

        final ActorSelection selection = system().actorSelection("/user/*");
        selection.tell("Akka", testActor());
        final Seq<Object> messages = receiveN(2);
        assertTrue(messages.contains("Bonjour, Akka!"));
        assertTrue(messages.contains("Hello, Akka!"));
    }

    @Test
    public void testIdentifying() {
        system().actorOf(Props.create(ParameterizedHelloAkka.class), "english");
        final ActorSelection selection = system().actorSelection("/user/english");
        selection.tell(new Identify("English"), testActor());
        final Seq<Object> seq = receiveN(1);
        assertTrue(seq.apply(0) instanceof ActorIdentity);
    }
}
