package sceneone.synchronizeddemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SycDemo {

    public SycDemo() {

    }

    private void method1() throws InterruptedException {
        synchronized (SycDemo.class){
            System.out.println("method1>>>>>>>>start");
            Thread.sleep(3000L);
            System.out.println("method1>>>>>>>>end");
        }
    }

    private void method2() {
        System.out.println("method2>>>>>>>>>start");
        System.out.println("method2>>>>>>>>>end");
    }

    public static void main(String[] args) throws InterruptedException {
        SycDemo sycDemo = new SycDemo();
        SycDemo sycDemo1 = new SycDemo();
        ExecutorService es = new ThreadPoolExecutor(2, 2, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        es.execute(() -> {
            try {
                sycDemo.method1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        es.execute(() -> {
            try {
                sycDemo1.method1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        es.shutdown();
    }
}
