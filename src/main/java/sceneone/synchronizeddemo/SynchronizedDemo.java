package sceneone.synchronizeddemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  此demo用于理解synchronized用法;
 *  分几种情况:
 *  0.默认不加synchronized
 *  1.方法上加synchronized
 *  2.方法内加synchronized(this)
 *  3.方法内加synchronized(SynchronizedDemo.class)
 *  4.方法上加static&synchronized
 *  5.多个实例的情况
 */

public class SynchronizedDemo {

    public SynchronizedDemo() {

    }

    public synchronized void method1() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " method1开始执行");
        Thread.sleep(5000L);
        System.out.println(Thread.currentThread().getName() + " method1执行完毕");

    }

    public void method2() {
        System.out.println(Thread.currentThread().getName() + " method2开始执行");
        System.out.println(Thread.currentThread().getName() + " method2执行完毕");
    }


    public static void main(String[] args) {
        final SynchronizedDemo demo = new SynchronizedDemo();
        ExecutorService es = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new ThreadPoolExecutor.DiscardPolicy());

        es.execute(() -> {
            try {
                demo.method1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        es.execute(() -> {
            demo.method2();
        });

        es.shutdown();

    }

}
