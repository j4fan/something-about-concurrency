## something-about-concurrency
> 本项目旨在记录本人在开发过程遇到的并发场景和常用的解决方案，项目将以场景+方案的形式呈现。

![TeamCity CodeBetter](https://img.shields.io/teamcity/codebetter/bt428.svg)

## 1 汇总多任务返回结果

场景描述：一个任务需要向多个系统请求数据，最后进行汇总输出，或者进行统一操作，如果串行执行，会比较耗时，故考虑采用多线程做法。

方案一：CountDownLatch

在CountDownLatchTask这个例子中，可以看到CountDownLatch的基本用法，使用起来是十分方便的，但是缺点也是比较明显，CountDownLatch会嵌入代码中造成一定的耦合。

对于线程池的使用这里也有一点心得，当我们创建一个线程池，例如
```
private ExecutorService executorService = new ThreadPoolExecutor(5, 10,
            10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new DefaultThreadFactory("withdrawTask"));

```
调用时可以看到ExecutorService有execute方法,即可以创建一个实现了Runnable接口的子类
```
es.execute(() -> {
                new SubTask() //其中SubTask实现了Runnable接口
            });
```
可以使用匿名内部类，这样会更加方便，例如我们想在执行之后进行计数，进行CoundDownLatch的countdown()
```
es.execute(() -> {
                new SubTask().doJobWithSomeTime();
                countDownLatch.countDown();
            });
```

未完待续...