package com.example.resume.core;

public interface Stage<I, O> {
    O process(I input);
}


