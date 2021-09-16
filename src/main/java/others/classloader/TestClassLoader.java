package others.classloader;

import java.util.stream.IntStream;

/**
 * 关于ClassLoader的测试
 *
 * https://blog.csdn.net/yangcheng33/article/details/52631940
 * https://blog.csdn.net/zhoudaxia/article/details/35897057
 *
 * <p/>
 * @author Hancher
 * @date Created in 2018年06月11日 10:30
 * @version 1.0
 * @since 1.0
 */
public class TestClassLoader {
    public static void main(String[] args) {
        System.out.println("ContextClassLoader:"+Thread.currentThread().getContextClassLoader());
        System.out.println("ClassLoader:"+TestClassLoader.class.getClassLoader());
        System.out.println("SystemClassLoader:"+ClassLoader.getSystemClassLoader());

        Runnable r = ()-> System.out.println("ContextClassLoader:"+Thread.currentThread().getContextClassLoader());
        IntStream.range(1,10).forEach(i->new Thread(r).start());
        //todo 待定
    }
}
