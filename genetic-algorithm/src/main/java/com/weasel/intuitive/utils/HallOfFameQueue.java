package com.weasel.intuitive.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;


@Getter
@Setter
public class HallOfFameQueue<U> {
    private Queue<U> queue;
    private Integer size;

    public HallOfFameQueue(Integer size) {
        queue = new LinkedList<>();
        this.size = size;
    }

    public void add(U element) {
        if (queue.size() >= size) {
            queue.poll();
            queue.add(element);
        } else {
            queue.add(element);
        }
    }

    public boolean isQueueFull() {
        return this.queue.size() == this.size;
    }

    public Integer numberOfUniqueElements() {
        return new HashSet<>(queue).size();
    }

    public void emptyQueue() {
        this.queue = new LinkedList<>();
    }
}
