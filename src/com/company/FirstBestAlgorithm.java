package com.company;

import java.util.PriorityQueue;

public class FirstBestAlgorithm<T extends EvaluableNodeValue<T>> {

    private PriorityQueue<Node<T>> priorityQueue;
    private int spectre;

    public FirstBestAlgorithm(Node<T> startNode, int spectre) {
        this.spectre = spectre;
        this.priorityQueue = new PriorityQueue<>((n1, n2) -> n2.getTarget() - n1.getTarget());
        priorityQueue.add(startNode);
    }

    public T getResult() {
        Node<T> winningNode = null;
        while (priorityQueue.peek() != null) {
            var nextNode = priorityQueue.poll();
            if(nextNode != null) {
                if(winningNode == null || nextNode.getTarget() > winningNode.getTarget()) {
                    nextNode.initSubNodes(spectre);
                    winningNode = nextNode;
                    priorityQueue.addAll(winningNode.getSubNodes());
                }
            }
        }
        return winningNode.getValue();
    }
}
