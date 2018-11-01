package sceneone.countdownlatchdemo;

import java.util.Random;

public class SubTask {
    private Random random = new Random();

    public void doJobWithSomeTime() {
        System.out.println(Thread.currentThread().getName() + " start to execute");
        try {
            Thread.sleep(random.nextInt(10) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " ended ");
    }
}
