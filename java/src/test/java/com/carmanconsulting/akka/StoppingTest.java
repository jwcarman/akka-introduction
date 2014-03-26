package com.carmanconsulting.akka;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import org.junit.Test;

public class StoppingTest extends AkkaTestCase {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    public void testStoppingWithSystem() {
        ActorRef ref = system().actorOf(HelloAkka.props(), "hello");
    }

//----------------------------------------------------------------------------------------------------------------------
// Inner Classes
//----------------------------------------------------------------------------------------------------------------------

    public static class MyActor extends UntypedActor {
        @Override
        public void onReceive(Object message) throws Exception {
            if ("Done".equals(message)) {
                context().stop(self());
            } else {
                unhandled(message);
            }
        }
    }
}
