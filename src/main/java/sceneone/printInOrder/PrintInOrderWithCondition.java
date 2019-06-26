package sceneone.printInOrder;

import org.jboss.netty.util.internal.NonReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition和Lock配合同样可以实现类似的功能
 */
public class PrintInOrderWithCondition {
    static int LOOP_MARK = 30;
    static Lock lock = new ReentrantLock();
//    static Lock lock = new NonReentrantLock();
    static Condition condition = lock.newCondition();


    static class ThreadA implements Runnable {

        @Override
        public void run() {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"acquired the lock");
            try {
                while (LOOP_MARK > 0) {
                    if (LOOP_MARK % 3 == 0) {
                        System.out.println("A");
                        LOOP_MARK--;
                        Thread.sleep(1000);
                        condition.signal();
                    } else {
                        condition.await();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class ThreadB implements Runnable {

        @Override
        public void run() {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"acquired the lock");
            try {
                while (LOOP_MARK > 0) {
                    if (LOOP_MARK % 3 == 2) {
                        System.out.println("B");
                        LOOP_MARK--;
                        Thread.sleep(1000);
                        condition.signal();
                    } else {
                        condition.await();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class ThreadC implements Runnable {

        @Override
        public void run() {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"acquired the lock");
            try {
                while (LOOP_MARK > 0) {
                    if (LOOP_MARK % 3 == 1) {
                        System.out.println("C");
                        LOOP_MARK--;
                        Thread.sleep(1000);
                        condition.signal();
                    } else {
                        condition.await();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ThreadC()).start();
        new Thread(new ThreadA()).start();
        new Thread(new ThreadB()).start();
    }

}
