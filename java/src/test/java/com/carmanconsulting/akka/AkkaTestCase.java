package com.carmanconsulting.akka;

import akka.actor.ActorSystem;
import akka.testkit.TestKit;
import org.junit.After;
import scala.concurrent.duration.Duration;

public abstract class AkkaTestCase extends TestKit {

    public AkkaTestCase() {
        super(ActorSystem.apply("AkkaTesting"));
    }

    @After
    public void shutdownActorSystem() {
        TestKit.shutdownActorSystem(system(), Duration.create("3 seconds"), true);
    }
}
