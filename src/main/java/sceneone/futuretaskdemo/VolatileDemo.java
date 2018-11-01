package sceneone.futuretaskdemo;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class VolatileDemo implements Runnable {

    private static volatile int count = 0;
    private static final int EXECUTE_TIME = 1000;
    private Random random = new Random();
    private static CountDownLatch countDownLatch = new CountDownLatch(EXECUTE_TIME);

    public static void main(String[] args) throws InterruptedException {
        VolatileDemo demo = new VolatileDemo();

        for (int i = 0; i < EXECUTE_TIME; i++) {
            new Thread(demo).start();
        }
        countDownLatch.await();
        System.out.println(count);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " " + count++);
        countDownLatch.countDown();
    }
}
