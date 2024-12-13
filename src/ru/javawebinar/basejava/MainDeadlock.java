package ru.javawebinar.basejava;

public class MainDeadlock {
    private static final Object LOCK = new Object();
    private static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> run(LOCK, LOCK_2)).start();

        new Thread(() -> run(LOCK_2, LOCK)).start();
    }

    private static void run(Object o1, Object o2) {
        synchronized (o1) {
            printThreadInfo(o1);
            synchronized (o2) {
                printThreadInfo(o2);
            }
        }
    }

    private static void printThreadInfo(Object object) {
        System.out.println(Thread.currentThread().getName() + " synchronized" + object);
    }
}