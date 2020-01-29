package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Node<T extends EvaluableNodeValue<T>> {

    private T value;
    private int target;
    private List<Node<T>> subNodes;

    public Node(T value) {
        this.value = value;
        this.target = value.evaluate();
        subNodes = new LinkedList<>();
    }

    public void initSubNodes(int count) {
        for (int i = 0; i< count; i++) {
            value.generateNext().ifPresent(v -> subNodes.add(new Node<>(v)));
        }
    }

    public T getValue() {
        return value;
    }

    public int getTarget() {
        return target;
    }

    public List<Node<T>> getSubNodes() {
        return subNodes.stream().collect(Collectors.toUnmodifiableList());
    }
}
