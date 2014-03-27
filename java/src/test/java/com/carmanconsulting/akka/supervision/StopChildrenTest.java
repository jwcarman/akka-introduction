package com.carmanconsulting.akka.supervision;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.carmanconsulting.akka.AkkaTestCase;
import com.carmanconsulting.akka.LifecycleLogger;
import org.junit.Test;
import scala.Option;

public class StopChildrenTest extends AkkaTestCase {


    @Test
    public void testStoppingChildren() {
        final ActorRef parent = system().actorOf(Props.create(Parent.class), "parent");
        parent.tell("Hello", testActor());
        expectNoMsg();
    }

    public static class Parent extends LifecycleLogger {

        @Override
        public void preStart() throws Exception {
            super.preStart();
            context().actorOf(Props.create(Child.class), "child1");
            context().actorOf(Props.create(Child.class), "child2");
        }

        @Override
        public void onReceive(Object message) throws Exception {
            throw new IllegalArgumentException(String.valueOf(message));
        }
    }


    public static class RestartingParent extends LifecycleLogger {

        public RestartingParent() {
            context().actorOf(Props.create(Child.class), "child");
            //context().actorOf(Props.create(Child.class), "child2");
        }

        @Override
        public void onReceive(Object message) throws Exception {
            throw new IllegalArgumentException(String.valueOf(message));
        }

        @Override
        public void preRestart(Throwable reason, Option<Object> message) throws Exception {
            log.info("preRestart({}, {})", reason, message);
            postStop();
        }
    }

    public static class Child extends LifecycleLogger {
        @Override
        public void onReceive(Object message) throws Exception {
            unhandled(message);
        }
    }
}
