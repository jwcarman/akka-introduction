package com.carmanconsulting.akka.java.cluster;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class CalculationWorker extends UntypedActor {
//----------------------------------------------------------------------------------------------------------------------
// Canonical Methods
//----------------------------------------------------------------------------------------------------------------------

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof CalculationChunk) {
            final CalculationChunk chunk = (CalculationChunk) message;
            sender().tell(chunk.evaluate(), self());
        } else {
            unhandled(message);
        }
    }

//----------------------------------------------------------------------------------------------------------------------
// main() method
//----------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        final Config config = ConfigFactory.parseResources(CalculationWorker.class, "worker.conf");
        ActorSystem system = ActorSystem.create("PiCalculator", config);
        //system.actorOf(Props.create(CalculationWorker.class), "calculationWorker");
    }
}
