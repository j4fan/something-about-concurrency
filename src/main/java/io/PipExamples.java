package io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipExamples {

    public static void main(String[] args) throws IOException {
        final PipedOutputStream pipedOutputStream = new PipedOutputStream();
        final PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        Thread thread1 = new Thread(() -> {
            try {
                pipedOutputStream.write("hello world".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    pipedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                int data = pipedInputStream.read();
                while (data != -1) {
                    System.out.println((char) data);
                    data = pipedInputStream.read();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    pipedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();
    }
}
