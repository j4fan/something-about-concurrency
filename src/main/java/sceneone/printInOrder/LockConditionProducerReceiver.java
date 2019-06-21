package sceneone.printInOrder;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用lock/condition 配合condition的await()/notify()方法
 */
public class LockConditionProducerReceiver {

    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    static volatile int count = 0;

    static class Sender {

        private void send() {
            lock.lock();
            try {
                while (true) {

                    if (count > 0) {
                        condition.await();
                    } else {
                        System.out.println("Sender send msg...");
                        Thread.sleep(1000L);
                        count++;
                        condition.signal();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class Receiver {

        private void receive() {
            lock.lock();
            try {
                while (true) {

                    if (count == 0) {
                        condition.await();
                    } else {
                        System.out.println("Receiver receive msg");
                        Thread.sleep(1000L);
                        count--;
                        condition.signal();
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
        Thread threadSender = new Thread(() -> {
            new LockConditionProducerReceiver.Sender().send();
        });

        Thread threadReceiver = new Thread(() -> {
            new LockConditionProducerReceiver.Receiver().receive();
        });

        threadSender.start();
        threadReceiver.start();
    }
}
