package com.carmanconsulting.akka;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

public abstract class LifecycleLogger extends UntypedActor {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    protected final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    protected LifecycleLogger() {
        log.info("new {}()", getClass().getSimpleName());
    }

    @Override
    public void preStart() throws Exception {
        log.info("preStart()");
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        log.info("postStop()");
        super.postStop();
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        log.info("postRestart({})", reason);
        super.postRestart(reason);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        log.info("preRestart({}, {})", reason, message);
        super.preRestart(reason, message);
    }
}
