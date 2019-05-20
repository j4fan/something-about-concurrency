package common;


/**
 * 用这个demo研究了一下String赋值的一些问题，顺便回顾了一些知识点。
 * 首先装一个jclasslib的插件，可以方便的再编译后看反编译的字节码指令。
 * 先编译一下代码，然后
     0 ldc #2 <test>
     2 astore_1
     3 new #3 <java/lang/StringBuilder>
     6 dup
     7 invokespecial #4 <java/lang/StringBuilder.<init>>
     10 aload_1
     11 invokevirtual #5 <java/lang/StringBuilder.append>
     14 ldc #6 <demo>
     16 invokevirtual #5 <java/lang/StringBuilder.append>
     19 invokevirtual #7 <java/lang/StringBuilder.toString>
     22 astore_2
     23 ldc #8 <hello>
     25 astore_3
     26 ldc #9 <helloworld>
     28 astore 4
     30 return



 */
public class ConstDemo {

    public static void main(String[] args) {
        String a = "test";
        String b = a +"demo";
        final String c = "hello";
        String d= c+"world";
    }
}
