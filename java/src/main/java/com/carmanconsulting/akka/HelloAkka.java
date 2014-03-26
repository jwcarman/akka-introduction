package com.carmanconsulting.akka;

import akka.actor.Props;
import akka.actor.UntypedActor;

public class HelloAkka extends UntypedActor {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final String DEFAULT_FORMAT = "Hello, %s!";

    private final String format;

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    public static Props props() {
        return Props.create(HelloAkka.class);
    }

    public static Props props(String format) {
        return Props.create(HelloAkka.class, format);
    }

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public HelloAkka() {
        this(DEFAULT_FORMAT);
    }

    public HelloAkka(String format) {
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
