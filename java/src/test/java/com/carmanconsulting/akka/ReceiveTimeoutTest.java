package com.carmanconsulting.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import org.junit.Test;
import scala.concurrent.duration.Duration;

public class ReceiveTimeoutTest extends AkkaTestCase {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    public void testReceiveTimeout() throws Exception {
        system().actorOf(Props.create(ReceiveTimeoutActor.class, testActor()), "receiveTimeout");
        expectMsg("ReceiveTimeout!");
    }

//----------------------------------------------------------------------------------------------------------------------
// Inner Classes
//----------------------------------------------------------------------------------------------------------------------

    public static class ReceiveTimeoutActor extends UntypedActor {
        private final ActorRef listener;

        public ReceiveTimeoutActor(ActorRef listener) {
            this.listener = listener;
        }

        @Override
        public void preStart() throws Exception {
            context().setReceiveTimeout(Duration.create("500 milliseconds"));
        }

        @Override
        public void onReceive(Object message) throws Exception {
            if (message instanceof ReceiveTimeout) {
                listener.tell("ReceiveTimeout!", self());
            } else {
                unhandled(message);
            }
        }
    }
}
