##something-about-concurrency
> 本项目旨在记录本人在开发过程遇到的并发场景和常用的解决方案，项目将以场景+方案的形式呈现。

![TeamCity CodeBetter](https://img.shields.io/teamcity/codebetter/bt428.svg)

## 1 汇总多任务返回结果

场景描述：一个任务需要向多个系统请求数据，最后进行汇总输出，或者进行统一操作，如果串行执行，会比较耗时，故考虑采用多线程做法。

方案一：CountDownLatch

to be continued...