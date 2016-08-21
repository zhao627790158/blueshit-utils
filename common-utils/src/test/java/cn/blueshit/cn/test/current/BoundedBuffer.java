package cn.blueshit.cn.test.current;

import java.util.concurrent.Semaphore;

/**
 * Created by zhaoheng on 16/8/21.
 */
public class BoundedBuffer<E> {

    private final Semaphore availableItems, avaliableSpaces;
    private E[] items;
    private int putPosition = 0, takePosition = 0;

    public BoundedBuffer(int capacity) {
        this.availableItems = new Semaphore(0);
        this.avaliableSpaces = new Semaphore(capacity);
        this.items = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return avaliableSpaces.availablePermits() == 0;
    }

    private void put(E x) throws InterruptedException {
        avaliableSpaces.acquire();
        doInsert(x);
        availableItems.release();
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E item = doExtract();
        avaliableSpaces.release();
        return item;
    }

    private synchronized void doInsert(E x) {
        int i = putPosition;
        items[i] = x;
        System.out.println(items.length);
        putPosition = (++i == items.length) ? 0 : i;
    }

    private synchronized E doExtract() {
        int i = takePosition;
        E x = items[i];
        items[i] = null;
        takePosition = (++i == items.length) ? 0 : i;
        return x;
    }

    public static void main(String[] args) {
        final BoundedBuffer<Integer> boundedBuffer = new BoundedBuffer<Integer>(10);
        Thread takser = new Thread() {
            @Override
            public void run() {
                try {
                    boundedBuffer.take();
                    System.err.println("get--------");
                } catch (InterruptedException e) {
                    System.err.println("interrputed");
                }
            }
        };

        try {
            takser.start();
            Thread.sleep(2000);

            takser.interrupt();
            takser.join(1000);
            System.err.println("error--------" + takser.isAlive());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
