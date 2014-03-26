package com.carmanconsulting;

import akka.actor.UntypedActor;

public class HelloAkka extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        sender().tell("Hello, " + message + "!", self());
    }
}
