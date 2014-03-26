package com.carmanconsulting.akka.java;

import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.Scheduler;
import akka.actor.UntypedActor;
import org.junit.Test;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class ReceiveTimeoutTest extends AkkaTestCase {

    @Test
    public void testReceiveTimeout() throws Exception {
        system().actorOf(Props.create(ReceiveTimeoutActor.class));
        Thread.sleep(2000);
    }

    public static class ReceiveTimeoutActor extends UntypedActor {

        @Override
        public void preStart() throws Exception {
            context().setReceiveTimeout(Duration.create("1 second"));
        }

        @Override
        public void onReceive(Object message) throws Exception {

            ActorSystem system = context().system();
            Scheduler scheduler = system.scheduler();
            Cancellable cancellable = scheduler.scheduleOnce(
                    Duration.create(1, TimeUnit.SECONDS),
                    self(),
                    "Hello",
                    system.dispatcher(),
                    self());
            if (message instanceof ReceiveTimeout) {
                System.out.println("I haven't received anything for a while.");
                context().setReceiveTimeout(Duration.Undefined());
            }
        }
    }
}
