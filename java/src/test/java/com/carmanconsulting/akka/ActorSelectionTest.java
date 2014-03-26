package com.carmanconsulting.akka;

import akka.actor.ActorIdentity;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import org.junit.Test;
import scala.collection.immutable.Seq;

import static org.junit.Assert.*;

public class ActorSelectionTest extends AkkaTestCase {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    public void testIdentifying() {
        system().actorOf(ConstantEcho.props("foo"), "foo");

        final ActorSelection selection = system().actorSelection("/user/foo");
        selection.tell(new Identify("identifyFoo"), testActor());

        final Seq<Object> seq = receiveN(1);

        ActorIdentity identity = (ActorIdentity) seq.apply(0);
        assertEquals("identifyFoo", identity.correlationId());

        identity.getRef().tell("baz", testActor());
        expectMsg("foo");
    }

    @Test
    public void testSelectingMultipleActors() {
        system().actorOf(ConstantEcho.props("foo"), "foo");
        system().actorOf(ConstantEcho.props("bar"), "bar");

        final ActorSelection selection = system().actorSelection("/user/*");
        selection.tell("baz", testActor());
        final Seq<Object> messages = receiveN(2);
        assertTrue(messages.contains("foo"));
        assertTrue(messages.contains("bar"));
    }

    @Test
    public void testSelectingOneActor() {
        system().actorOf(ConstantEcho.props("foo"), "foo");
        system().actorOf(ConstantEcho.props("bar"), "bar");
        ActorSelection selection = system().actorSelection("/user/foo");
        selection.tell("baz", testActor());
        expectMsg("foo");
    }
}
