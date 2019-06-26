package sceneone.printInOrder;

import java.util.concurrent.locks.ReentrantLock;

public class ReetrantlockTest {

    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        Thread threadA = new Thread(()->{
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"acquire lock ");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        Thread threadB = new Thread(()->{
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"acquire lock ");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        threadA.start();
        threadB.start();
    }
}
