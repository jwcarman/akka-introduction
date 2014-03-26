package com.carmanconsulting.akka.java;

import akka.actor.UntypedActor;

public class ClarkGable extends UntypedActor {

    public void onReceive(Object message) throws Exception {
        if (message instanceof Action) {
            System.out.println("Frankly my dear, I don't give a damn.");
        } else {
            unhandled(message);
        }
    }
}
