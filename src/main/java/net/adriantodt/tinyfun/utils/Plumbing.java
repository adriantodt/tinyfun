package net.adriantodt.tinyfun.utils;

import java.io.*;
import java.util.ArrayList;

public class Plumbing {
    private final ArrayList<InputStream> inputs;
    private final ArrayList<OutputStream> outputs;

    public Plumbing(int size, InputStream inputStream, OutputStream outputStream) throws IOException {
        var inputPipes = new ArrayList<PipedInputStream>(size - 1);
        var outputPipes = new ArrayList<PipedOutputStream>(size - 1);
        this.inputs = new ArrayList<>(size - 1);
        this.outputs = new ArrayList<>(size - 1);

        for (int i = 0; i < size - 1; i++) {
            var pipeIn = new PipedInputStream();
            var pipeOut = new PipedOutputStream(pipeIn);
            inputPipes.add(pipeIn);
            outputPipes.add(pipeOut);
        }

        this.inputs.add(inputStream);
        this.inputs.addAll(inputPipes);

        this.outputs.addAll(outputPipes);
        this.outputs.add(outputStream);
    }

    public InputStream input(int index) {
        return this.inputs.get(index);
    }

    public OutputStream output(int index) {
        return this.outputs.get(index);
    }
}
