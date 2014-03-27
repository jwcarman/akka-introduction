package com.carmanconsulting.akka;

import akka.actor.Props;
import akka.actor.UntypedActor;

public class DevNull extends LifecycleLogger {
//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    public static Props props() {
        return Props.create(DevNull.class);
    }

//----------------------------------------------------------------------------------------------------------------------
// Canonical Methods
//----------------------------------------------------------------------------------------------------------------------

    @Override
    public void onReceive(Object message) throws Exception {
        unhandled(message);
    }
}
