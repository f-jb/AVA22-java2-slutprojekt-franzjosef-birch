package se.kaiserbirch;

import se.kaiserbirch.model.Work;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class Main {
    public static void main(String[] args) {
        /*
        * Use LinkedBlockingQueue instead of ConcurrentLinkedQueue
        * to be able to set the size of the queue.
         */
        BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
    }
}