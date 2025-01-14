package com.example.resume.core;
import java.util.List;

public class Pipeline<I, O> {
    private final List<Stage<I, O>> stages;

    public Pipeline(List<Stage<I, O>> stages) {
        this.stages = stages;
    }

    public O execute(I input) {
        O result = null;
        for (Stage<I, O> stage : stages) {
            result = stage.process(input);
            input = (I) result;
        }
        return result;
    }
}


