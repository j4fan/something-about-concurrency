#### 三个线程循环打印ABC

>这个题目是一个比较老的题目，主要考察对多线程的了解和熟悉程度。研究了一段时间，对这个题目所涉及的场景也有些个人理解。

1.PrintInOrderWithLoopBySleep
---
在类PrintInOrderWithLoopBySleep中的方式，可以抽象为，在策略模式的代码中，我们用一个公用的状态state来代表策略线程的切换，
假如有N种策略，则N个线程同时开启，通过轮训+if判断的方式，切换执行的线程，不执行的线程在
while中永久循环。
```
    @Override
    public void run(){
        while(count<MAX_COUNT){
            int i = count%3;
            switch (i){
                case 0: if(this.getName().equals("A")) printByName(this.getName()); break;
                case 1: if(this.getName().equals("B")) printByName(this.getName()); break;
                case 2: if(this.getName().equals("C")) printByName(this.getName()); break;
                default:
            }

        }
    }
```

其中的代码细节是这一段，实际上申请了三个线程，在每次打印的时候，有两个线程都在while循环中不停running，直到
count的值发生了变化，才有可能使得if中的判断为true，从而打印对应的字母，否则一直在while中running。
实际上这里用while的循环代替了线程的挂起。这种方式CPU负载会比较高，但是线程上线文的切换开销比较小。
如果说打印这个操作相对于上下文切换是个比较耗时的操作，那这个方法可能不太科学。但是如果上下文切换相对于打印
是个比较昂贵的操作，那么这种方法是可行的。


