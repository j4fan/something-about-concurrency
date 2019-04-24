package sceneone.futuretaskdemo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletebleFutureDemo {

    private final String HELLO = "Hello";
    private final String WORLD = "World";

    /**
     * simple future user
     */
    public Future calculateAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            System.out.println("start...");
            Thread.sleep(5000L);
            completableFuture.complete(HELLO);
            System.out.println("end....");
            return null;
        });
        return completableFuture;
    }

    /**
     * cancel a task
     */
    public void cancleAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(5000L);
            completableFuture.cancel(false);
            return 1;
        });
        System.out.println(completableFuture.get());
    }

    /**
     * process a result of a async task
     */
    public void processResultAsyncTask() throws ExecutionException, InterruptedException {
        CompletableFuture<String> formalFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return HELLO;
        });
        CompletableFuture<String> latterFuture = formalFuture.thenApply(s -> s + "," + WORLD);
        System.out.println(latterFuture.get());
    }


    /**
     * process mutiple future in parallel
     */
    public void RunningMultipleFutureInParallel() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "who";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "you");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "are");
        CompletableFuture<Void> combilefuture = CompletableFuture.allOf(future1, future2, future3);
        combilefuture.get();
        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());

    }

    public CompletableFuture thenComposeUsage() {
        CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> "hello").
                thenCombine(CompletableFuture.supplyAsync(() -> "world"),
                        (s1, s2) -> s1 + s2);
        return completableFuture;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
        Future future = new CompletebleFutureDemo().calculateAsync();
        System.out.println("gaga");
        Thread.sleep(3000L);
        System.out.println(future.get());
        System.out.println("end");
         */

        /**
        CompletableFuture completableFuture = new CompletebleFutureDemo().thenComposeUsage();
        System.out.println(completableFuture.get());
         */



    }

}
