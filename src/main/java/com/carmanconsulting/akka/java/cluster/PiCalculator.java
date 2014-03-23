package com.carmanconsulting.akka.java.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.FromConfig;
import akka.routing.GetRoutees;
import akka.routing.Routees;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class PiCalculator extends UntypedActor {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final int CHUNK_COUNT = 10000;

    private final LoggingAdapter logging = Logging.apply(this);
    private final ActorRef workerRouter = context().actorOf(FromConfig.getInstance().props(Props.create(CalculationWorker.class)), "workerRouter");
    private BigDecimal pi = new BigDecimal(0.0);
    private int resultCount = 0;

//----------------------------------------------------------------------------------------------------------------------
// Canonical Methods
//----------------------------------------------------------------------------------------------------------------------

    @Override
    public void onReceive(Object message) {
        if (message instanceof Routees) {
            Routees routees = (Routees) message;
            if (!routees.getRoutees().isEmpty()) {
                logging.info("Beginning to calculate {} chunks...", CHUNK_COUNT);
                for (int i = 0; i < CHUNK_COUNT; ++i) {
                    workerRouter.tell(new CalculationChunk(i), self());
                }
            } else {
                logging.info("No workers found checking back in 1 second...");
                checkForWorkers();
            }
        } else if (message instanceof CheckForWorkers) {
            workerRouter.tell(GetRoutees.getInstance(), self());
        } else if (message instanceof CalculationResult) {
            final CalculationResult result = (CalculationResult) message;
            resultCount++;
            pi = pi.add(new BigDecimal(result.getResult()));
            if (result.getChunkIndex() == CHUNK_COUNT - 1) {
                logging.info("Calculated PI as {} after receiving {} results.", (pi.multiply(BigDecimal.valueOf(4.0))), resultCount);
                context().stop(self());
            }
        } else {
            unhandled(message);
        }
    }

    private void checkForWorkers() {
        context().system().scheduler().scheduleOnce(Duration.create(1, TimeUnit.SECONDS), self(), new CheckForWorkers(), context().dispatcher(), self());
    }

    @Override
    public void preStart() {
        checkForWorkers();
    }

//----------------------------------------------------------------------------------------------------------------------
// main() method
//----------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        final Config config = ConfigFactory.parseResources(PiCalculator.class, "master.conf");
        final ActorSystem system = ActorSystem.create("PiCalculator", config);
        system.actorOf(Props.create(PiCalculator.class), "piCalculator");
    }
}
