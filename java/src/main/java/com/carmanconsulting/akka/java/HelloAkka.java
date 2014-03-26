package com.carmanconsulting.akka.java;

import akka.actor.UntypedActor;

public class HelloAkka extends UntypedActor {

    @Override
    public void onReceive(Object message) {
        final String response = "Hello, " + message + "!";
        getSender().tell(response, getSelf());
    }
}
