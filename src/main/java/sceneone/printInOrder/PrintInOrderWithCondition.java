package sceneone.printInOrder;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition和Lock配合同样可以实现类似的功能
 */
public class PrintInOrderWithCondition {

    static Lock lock = new ReentrantLock();
    static Condition formerConditon = lock.newCondition();
    static Condition latterConditon = lock.newCondition();

    static boolean formerFlag = true;
    static boolean latterFlag = true;

    public static void main(String[] args) {

        Thread threadOne = new Thread(() -> {
            lock.lock();
            System.out.println("A");
            formerConditon.signal();
            formerFlag=false;
            lock.unlock();
        });
        Thread threadTwo = new Thread(() -> {
            lock.lock();
            try {
                if(formerFlag){
                    formerConditon.await();
                }
                System.out.println("B");
                latterConditon.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
                latterFlag=false;
            }
        });

        Thread threadThree = new Thread(() -> {
            lock.lock();
            try {
                if(latterFlag){
                    latterConditon.await();
                }
                System.out.println("C");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        threadOne.start();
        threadThree.start();
        threadTwo.start();

    }

}
