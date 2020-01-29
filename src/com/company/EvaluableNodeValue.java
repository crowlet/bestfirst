package com.company;

import java.util.Optional;

public interface EvaluableNodeValue<T> {
    int evaluate();
    Optional<T> generateNext();
}
