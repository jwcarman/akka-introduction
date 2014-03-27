package com.carmanconsulting.akka;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class LifecycleLogger extends UntypedActor {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    protected final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private static final AtomicInteger instanceNumberSequence = new AtomicInteger();
    protected final int instanceNumber = instanceNumberSequence.incrementAndGet();
    
    protected LifecycleLogger() {
        log.info("{} new {}()", instanceNumber, getClass().getSimpleName());
    }

    @Override
    public void preStart() throws Exception {
        log.info("{} preStart()", instanceNumber);
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        log.info("{} postStop()", instanceNumber);
        super.postStop();
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        log.info("{} postRestart({})", instanceNumber, reason);
        super.postRestart(reason);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        log.info("{} preRestart({}, {})", instanceNumber, reason, message);
        super.preRestart(reason, message);
    }
}
