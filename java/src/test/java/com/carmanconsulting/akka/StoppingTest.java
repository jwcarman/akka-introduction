package com.carmanconsulting.akka;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import org.junit.Test;

public class StoppingTest extends AkkaTestCase {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    public void testStopMethod() {
        ActorRef echo = system().actorOf(Props.create(EchoAndStop.class), "echo");
        watch(echo);
        echo.tell("Hello", testActor());
        expectMsg("Hello");
        expectMsgClass(Terminated.class);
    }

//----------------------------------------------------------------------------------------------------------------------
// Inner Classes
//----------------------------------------------------------------------------------------------------------------------

    public static class EchoAndStop extends UntypedActor {
        @Override
        public void onReceive(Object message) throws Exception {
            sender().tell(message, self());
            context().stop(self());
        }
    }

    public static class EchoAndStopAfterN extends UntypedActor {
        private int n;

        public EchoAndStopAfterN(int n) {
            this.n = n;
        }

        @Override
        public void onReceive(Object message) throws Exception {
            sender().tell(message, self());
            if (n-- == 0) {
                self().tell(PoisonPill.getInstance(), ActorRef.noSender());
            }

        }
    }
}
