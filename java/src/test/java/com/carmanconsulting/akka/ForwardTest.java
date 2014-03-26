package com.carmanconsulting.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.testkit.TestActor;
import org.junit.Test;
import static org.junit.Assert.*;

public class ForwardTest extends AkkaTestCase {

    @Test
    public void testForwarding() {
        ActorRef forwarder = system().actorOf(Props.create(Forwarder.class, testActor()));
        forwarder.tell("Hello", testActor());
        expectMsg("Hello");
        final TestActor.Message message = lastMessage();
        assertEquals(testActor(), message.sender());
    }

    public static class Forwarder extends UntypedActor {
        private final ActorRef target;

        public Forwarder(ActorRef target) {
            this.target = target;
        }

        @Override
        public void onReceive(Object message) throws Exception {
            target.forward(message, context());
        }
    }
}
