package com.carmanconsulting.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Status;
import akka.actor.UntypedActor;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.pattern.AskTimeoutException;
import akka.testkit.TestActor;
import akka.util.Timeout;
import org.junit.Test;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.*;
import static org.junit.Assert.assertTrue;

public class AskTest extends AkkaTestCase {

    @Test
    public void testSuccessfulAsk() {
        ActorRef hello = system().actorOf(HelloAkka.props());
        final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        final Future<Object> future = ask(hello, "Akka", timeout);
        future.onSuccess(new OnSuccess<Object>() {
            public void onSuccess(Object result) {
                // Do something!
                testActor().tell("Success", testActor());
            }
        }, system().dispatcher());
        expectMsg("Success");
    }

    @Test
    public void testAskWithTimeout() {
        ActorRef devNull = system().actorOf(DevNull.props());
        final Timeout timeout = new Timeout(Duration.create(200, TimeUnit.MILLISECONDS));
        final Future<Object> future = ask(devNull, "Akka", timeout);
        future.onFailure(new OnFailure() {
            @Override
            public void onFailure(Throwable failure) throws Throwable {
                assertTrue(failure instanceof AskTimeoutException);
                testActor().tell("Failure", testActor());
            }
        }, system().dispatcher());
        expectMsg("Failure");
    }

    @Test
    public void testAskWithFailure() {
        ActorRef failure = system().actorOf(Props.create(FailureActor.class));
        final Timeout timeout = new Timeout(Duration.create(200, TimeUnit.MILLISECONDS));
        final Future<Object> future = ask(failure, "Akka", timeout);
        future.onFailure(new OnFailure() {
            @Override
            public void onFailure(Throwable failure) throws Throwable {
                assertTrue(failure instanceof IllegalArgumentException);
                testActor().tell("Failure", testActor());
            }
        }, system().dispatcher());
        expectMsg("Failure");
    }

    @Test
    public void testAskWhenActorThrowsException() {
        ActorRef failure = system().actorOf(Props.create(ExceptionActor.class));
        final Timeout timeout = new Timeout(Duration.create(200, TimeUnit.MILLISECONDS));
        final Future<Object> future = ask(failure, "Akka", timeout);
        future.onFailure(new OnFailure() {
            @Override
            public void onFailure(Throwable failure) throws Throwable {
                assertTrue(failure instanceof AskTimeoutException);
                testActor().tell("Failure", testActor());
            }
        }, system().dispatcher());
        expectMsg("Failure");
    }

    @Test
    public void testWithPipe() {
        ActorRef hello = system().actorOf(HelloAkka.props());
        final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        final Future<Object> future = ask(hello, "Akka", timeout);
        pipe(future, system().dispatcher()).to(testActor());
        expectMsg("Hello, Akka!");
    }

    @Test
    public void testWithPipeOnFailure() {
        ActorRef failure = system().actorOf(Props.create(FailureActor.class));
        final Timeout timeout = new Timeout(Duration.create(200, TimeUnit.MILLISECONDS));
        final Future<Object> future = ask(failure, "Akka", timeout);
        pipe(future, system().dispatcher()).to(testActor());
        expectMsgClass(Status.Failure.class);
        TestActor.Message message = lastMessage();
        assertTrue(((Status.Failure)message.msg()).cause() instanceof IllegalArgumentException);
    }

    public static class FailureActor extends UntypedActor {
        @Override
        public void onReceive(Object message) throws Exception {
            sender().tell(new Status.Failure(new IllegalArgumentException()), self());
        }
    }

    public static class ExceptionActor extends UntypedActor {
        @Override
        public void onReceive(Object message) throws Exception {
            throw new IllegalArgumentException();
        }
    }

}
