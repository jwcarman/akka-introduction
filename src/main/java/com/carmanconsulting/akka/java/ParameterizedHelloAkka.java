package com.carmanconsulting.akka.java;

import akka.actor.UntypedActor;

public class ParameterizedHelloAkka extends UntypedActor {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final String DEFAULT_FORMAT = "Hello, %s!";

    private final String format;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public ParameterizedHelloAkka() {
        this(DEFAULT_FORMAT);
    }

    public ParameterizedHelloAkka(String format) {
        this.format = format;
    }

//----------------------------------------------------------------------------------------------------------------------
// Canonical Methods
//----------------------------------------------------------------------------------------------------------------------

    @Override
    public void onReceive(Object message) {
        final String response = String.format(format, message);
        getSender().tell(response, getSelf());
    }
}
