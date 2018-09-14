package CountDownLatchDemo;

import java.util.concurrent.*;

/**
 * @author fan4j
 * @date 2018.09.14
 */

public class CountDownLatchTask {
    private static final int COOL_POOL_SIZE = 5;
    private static final int MAX_POOLL_SIZE = 10;
    private static final int IDLE_TIME = 10;
    private static final int QUEUE_SIZE = 100;
    private static final int TASK_SIZE = 4;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(COOL_POOL_SIZE, MAX_POOLL_SIZE, IDLE_TIME,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(QUEUE_SIZE));

        CountDownLatch countDownLatch = new CountDownLatch(TASK_SIZE);

        for(int i=0;i<TASK_SIZE;i++){
            es.execute(()->{
                new SubTask().doJobWithSomeTime();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("all task finished ");
        // do something else after all subtasks finished
    }

}
