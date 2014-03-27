package com.carmanconsulting.akka;

import akka.actor.ActorSystem;
import akka.testkit.TestKit;
import org.junit.After;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.Duration;

public abstract class AkkaTestCase extends TestKit {

    public AkkaTestCase() {
        super(ActorSystem.apply("AkkaTesting"));
    }

    @After
    public void shutdownActorSystem() {
        LoggerFactory.getLogger(getClass()).info("Shutting down ActorSystem...");
        TestKit.shutdownActorSystem(system(), Duration.create("3 seconds"), true);
    }
}
