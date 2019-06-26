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

2.PrintInOrderWithCondition
后面又想了很多种方法，实际上只处理三个线程的先后顺序的时候，可能不会想到用一个int类型的标志flag
来确定执行哪个线程。于是出现了PrintInOrderWithNotifySafe这些类，这里面我尝试用多个condition的await和
signal方法来切换到底执行哪个线程。同时，为了防止出现第一个线程执行完毕，即第一个condition执行到signal方
法的时候，第二个方法还没有执行到await方法，特意用了一个标志位来标识，相当麻烦。<br>
实际上当我看了Object类中wait方法的api中的解释后，有了正确思路，api中这样写到
```aidl
A thread can also wake up without being notified, interrupted, or timing out, 
a so-called spurious wakeup. While this will rarely occur in practice, 
applications must guard against it by testing for the condition that 
should have caused the thread to be awakened, and continuing to wait if the
 condition is not satisfied. In other words, waits should always occur in loops, 
 like this one:
     synchronized (obj) {
         while (<condition does not hold>)
             obj.wait(timeout);
         ... // Perform action appropriate to condition
     }
```
所以这段代码的思路也是相同的，首先对需要确定顺序的代码块加锁(可能前面有些代码是需要多个线程并行执行的)
在while中，如果判断条件为true，那么执行操作，结束后await,唤醒锁上的其他线程。false就进行await<br>
这里还有另外一个问题，为什么将可重入锁ReentrantLock换成netty中的不可重入锁NonReentrantLock,程序不会报错
(这个问题已经解决，重入锁是指对一个线程可以重入，不同的线程依然会将其他线程阻塞，通过notify来通知)
