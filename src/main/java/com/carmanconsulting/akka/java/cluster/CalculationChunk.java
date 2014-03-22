package com.carmanconsulting.akka.java.cluster;

import java.io.Serializable;

public class CalculationChunk implements Serializable {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final int CHUNK_SIZE = 1000;

    private final int chunkIndex;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public CalculationChunk(int chunkIndex) {
        this.chunkIndex = chunkIndex;
    }

//----------------------------------------------------------------------------------------------------------------------
// Getter/Setter Methods
//----------------------------------------------------------------------------------------------------------------------

    public int getChunkIndex() {
        return chunkIndex;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public CalculationResult evaluate() {
        double result = 0.0;
        final int baseIndex = CHUNK_SIZE * chunkIndex;
        for (int i = 0; i < CHUNK_SIZE; ++i) {
            result += seriesValue(baseIndex + i);
        }
        return new CalculationResult(result, chunkIndex);
    }

    private static double seriesValue(int index) {
        final double sign = index % 2 == 0 ? 1 : -1;
        return sign / (2 * index + 1);
    }
}
