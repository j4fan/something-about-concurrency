package sceneone.printInOrder;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition和Lock配合同样可以实现类似的功能
 */
public class PrintInOrderWithCondition {
    static final int LOOP_SIZE = 10;
    static Lock lock = new ReentrantLock();
    static Condition formerConditon = lock.newCondition();
    static Condition latterConditon = lock.newCondition();


    public static void main(String[] args) {


    }

    class ThreadA implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < LOOP_SIZE; i++) {

            }
        }
    }

}
