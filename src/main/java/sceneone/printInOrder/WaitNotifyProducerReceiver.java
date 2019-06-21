package sceneone.printInOrder;

/**
 * wait/notify实现生产者消费者模型
 */

public class WaitNotifyProducerReceiver {

    static Object monitor = new Object();
    static volatile int count = 1;

    static class Sender {

        private void send() throws InterruptedException {
            synchronized (monitor) {
                while (true) {
                    if (count > 0) {
                        monitor.wait();
                    } else {
                        System.out.println("Sender send msg...");
                        Thread.sleep(1000L);
                        count++;
                        monitor.notify();
                    }
                }
            }
        }

        static class Receiver {

            private void receive() throws InterruptedException {
                synchronized (monitor) {
                    while (true) {
                        if (count == 0) {
                            monitor.wait();
                        } else {
                            System.out.println("Receiver receive msg");
                            Thread.sleep(1000L);
                            count--;
                            monitor.notify();
                        }
                    }
                }
            }
        }

        public static void main(String[] args) {
            Thread threadSender = new Thread(() -> {
                try {
                    new Sender().send();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread threadReceiver = new Thread(() -> {
                try {
                    new Receiver().receive();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            threadSender.start();
            threadReceiver.start();
        }
    }
}
