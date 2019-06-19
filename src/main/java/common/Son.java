package common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Son extends Father {

    @Override
    public void speak() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //使用MethodType构造出要调用方法的返回类型
//        try {
//        MethodType methodType = MethodType.methodType(void.class);
//            //找到祖父类的构造函数
//            MethodHandle inithandle = MethodHandles.lookup().findConstructor(GrandFather.class, methodType);
//            //获取祖父类实例对象
//            Object o =  inithandle.invoke();
//            //找到祖父类里被覆写的方法并把该方法绑定到祖父类实例上
//            MethodHandle handle = MethodHandles.lookup()
//                    .findVirtual(GrandFather.class, "speak", methodType).bindTo(o);
//            //调用祖父类里被父类覆写的方法
//            handle.invoke();
//        } catch (Throwable e){
//            e.printStackTrace();
//        }

        Method method = GrandFather.class.getDeclaredMethod("speak");
        GrandFather a = new GrandFather();
        method.invoke(a);
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Son son = new Son();
        son.speak();
    }
}
