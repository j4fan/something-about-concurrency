package sceneone.countdownlatchdemo;

import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ExecutorService es = new ThreadPoolExecutor(1,1,10,TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),threadFactory,new ThreadPoolExecutor.CallerRunsPolicy());

        es.execute(()->{
            throw new NullPointerException();
        });
        es.execute(()->{
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"is running");
        });
        es.execute(()->{
            System.out.println(Thread.currentThread().getName()+"is running");
        });
        es.execute(()->{
            System.out.println(Thread.currentThread().getName()+"is running");
        });
        int i =3;
        while(i>0){
            Thread.sleep(3000L);
            i--;
            System.out.println(((ThreadPoolExecutor) es).getActiveCount());
        }

    }
}
