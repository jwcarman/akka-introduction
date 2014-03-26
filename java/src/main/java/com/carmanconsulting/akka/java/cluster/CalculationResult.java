package com.carmanconsulting.akka.java.cluster;

import java.io.Serializable;

public class CalculationResult implements Serializable {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final double result;
    private final int chunkIndex;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public CalculationResult(double result, int chunkIndex) {
        this.result = result;
        this.chunkIndex = chunkIndex;
    }

//----------------------------------------------------------------------------------------------------------------------
// Getter/Setter Methods
//----------------------------------------------------------------------------------------------------------------------

    public int getChunkIndex() {
        return chunkIndex;
    }

    public double getResult() {
        return result;
    }
}
