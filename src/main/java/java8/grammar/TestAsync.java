package java8.grammar;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 探索java8中的CompletableFuture
 * <br/> 可以实现并行流的效果
 *
 * <p/>
 * @author Hancher
 * @date Created in 2018年06月03日 16:56
 * @version 1.0
 * @since 1.0
 */
public class TestAsync {
    private static final ExecutorService executor = Executors.newCachedThreadPool();
    public static void main(String[] args) {
        Integer[] integers = {11,11,111,4,56,57,2,7,6,45,344,5,655,3};

        //默认和并发流使用一个线程池,效果也一样
        //模拟findAny情况，当find后，其他线程仍旧继续跑
        Long start  = System.currentTimeMillis();
        CompletableFuture[] commonFuture = Arrays.stream(integers).map(TestAsync::doAsyncCommon).toArray(CompletableFuture[]::new);
        CompletableFuture.anyOf(commonFuture).join();
        System.out.println("耗时："+(System.currentTimeMillis()-start));
        Arrays.stream(commonFuture).filter(e->e.getNow(null) != null).forEach(e->System.out.println(e.getNow(0)));

        //findAny不会这样
        Integer i = Arrays.stream(integers).parallel().peek(e->System.out.println(Thread.currentThread().getName()+":"+e)).findAny().get();
        System.out.println("stream版findAny:"+i);

        //指定线程池 ,更灵活实用
        List<CompletableFuture<Integer>> executorFuture = Arrays.stream(integers).map(TestAsync::doAsyncExecutor).collect(Collectors.toList());
        executorFuture.forEach(CompletableFuture::join);


    }

    private static CompletableFuture<Integer> doAsyncCommon(Integer i){
        return CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+":"+i);
            try {
                Thread.sleep(i*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return i;
        });
    }

    private static CompletableFuture<Integer> doAsyncExecutor(Integer i){
        return CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+":"+i);
            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return i;
        },executor);
    }

}
