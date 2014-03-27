package com.carmanconsulting.akka;

import akka.actor.ActorRef;
import akka.actor.Kill;
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
        ActorRef echo = system().actorOf(Props.create(StopOnHalt.class), "stopMethod");
        watch(echo);
        echo.tell("Hello", testActor());
        echo.tell("Halt", testActor());
        echo.tell("Hello", testActor());


        expectMsg("Hello");
        expectMsg("Halt");
        expectMsgClass(Terminated.class);
        expectNoMsg();
    }

    @Test
    public void testPoisonPill() {
        ActorRef echo = system().actorOf(Props.create(PoisonPillOnHalt.class), "poisonPill");
        watch(echo);
        echo.tell("Hello", testActor());
        echo.tell("Halt", testActor());
        echo.tell("Hello", testActor());

        expectMsg("Hello");
        expectMsg("Halt");
        expectMsg("Hello");
        expectMsgClass(Terminated.class);
        expectNoMsg();
    }

    @Test
    public void testKill() {
        ActorRef echo = system().actorOf(Props.create(KillOnHalt.class), "kill");
        watch(echo);
        echo.tell("Hello", testActor());
        echo.tell("Halt", testActor());
        echo.tell("Hello", testActor());

        expectMsg("Hello");
        expectMsg("Halt");
        expectMsg("Hello");
        expectMsgClass(Terminated.class);
        expectNoMsg();
    }

//----------------------------------------------------------------------------------------------------------------------
// Inner Classes
//----------------------------------------------------------------------------------------------------------------------

    public static class StopOnHalt extends LifecycleLogger {
        @Override
        public void onReceive(Object message) throws Exception {
            sender().tell(message, self());
            if("Halt".equals(message)) {
                context().stop(self());
            }
        }
    }

    public static class PoisonPillOnHalt extends LifecycleLogger {
        @Override
        public void onReceive(Object message) throws Exception {
            sender().tell(message, self());
            if("Halt".equals(message)) {
                self().tell(PoisonPill.getInstance(), self());

            }
        }
    }

    public static class KillOnHalt extends LifecycleLogger {
        @Override
        public void onReceive(Object message) throws Exception {
            sender().tell(message, self());
            if("Halt".equals(message)) {
                self().tell(Kill.getInstance(), self());

            }
        }
    }
}
