package java8.grammar;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * 流使用例子
 *
 * <p/>
 * @author Hancher
 * @date Created in 2018年06月01日 0:31
 * @version 1.0
 * @since 1.0
 */
public class TestStream {
    public static void main(String[] args) {
//        testCreat();
        testProcess();
        testCollect();
    }

    /**
     * 测试流的产生
     */
    public static void testCreat() {
        //源自集合
        String[] stringArray = { "Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda" };
        List<String> list = Arrays.asList(stringArray);
        System.out.println("源自集合");
        list.stream().forEach(e->System.out.print(e+","));
        list.forEach(e->System.out.print(e+","));

        System.out.println("\n-----------------华丽的分割线------------------\n");

        //源自数组
        System.out.println("源自数组");
        Arrays.stream(stringArray).forEach(e->System.out.print(e+","));

        System.out.println("\n-----------------华丽的分割线------------------\n");

        //源自Map，先转为集合在使用流
        System.out.println("源自Map");
        Map<String,String> map = new HashMap<>();
        map.put("kkk","vvv");
        map.put("k","v");
        map.entrySet().forEach(entity->System.out.println(entity.getValue()));
        map.forEach((key, value) -> System.out.println(value));

        System.out.println("\n-----------------华丽的分割线------------------\n");

        //直接构造流
        System.out.println("直接构造流");
        Stream s = Stream.empty();//空流
        Stream s2 = Stream.builder().add("a").add("b").build();//build 方式
        Stream s3 = Stream.of("c","d");
        Stream s4 = Stream.concat(s2,s3);
        s4.forEach(e->System.out.print(e+","));

        //无限流
        System.out.println("无限流");
        //累加式无限，即由一个初始值，按照一定的规则不断的扩展而成。如：1,2,4,8....
        Stream.iterate(1,i->i*2).limit(11).forEach(i->System.out.print(i+","));
        //原生式无限，即由一个原生的母版不断的产生值。如：1,1,1,1.....
        Stream.generate(Date::new).limit(10).forEach(System.out::println);

        System.out.println("\n-----------------华丽的分割线------------------\n");

        //由文件生成(NIO)
        System.out.println("由文件生成：");
        try (Stream<String> lines = Files.lines(Paths.get("plain.txt"))){
            lines.forEach(System.out::println);//打印每一行
        } catch (IOException e) {
            e.printStackTrace();
        }

        //流的产生基本也就这些了，如有新发现，请周知
    }

    /**
     * 测试：流过程处理
     */
    public static void testProcess() {
        String[] stringArray = { "Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda" };
        List<String> list = Arrays.asList(stringArray);

        System.out.println("开始流过程的处理测试");
        //目标：从列表中筛选出带有‘a’,'r'字母的名字，并转为大写，去重，排序，最后输出第2、3个字母。
        list.stream()
                .filter(s->s.contains("a")) //过滤
                .filter(s -> s.contains("r")) //中间操作可重复使用
                .map(s -> s.split("")) //此处返回的是 [B,a,r,b,a,r,a],[M,a,r,y],[P,a,t,r,i,c,i,a] 数组流，而不是字母流
                .peek(System.out::println)  //打印日志，关于方法引用，参考TestMethodReference
                .flatMap(Arrays::stream) //扁平化流
                .map(String::toUpperCase) //字母大写
                .sorted()  //排序
                .distinct() //去重
                .skip(1)  //跳过n
                .limit(2) //限制n
                .forEach(System.out::println);

        //流的中间操作也就这些，实际使用中可以按需去组合
    }


    /**
     * 测试：流的收集
     */
    public static void  testCollect(){
        String[] stringArray = { "Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda" };
        Integer[] integers = {1,11,111,4,56,57,5,7,6,45,344,5,7655,3};
        List<String> list = Arrays.asList(stringArray);

        //最简单也是最常用的遍历,基本可以取代for/foreach
        // 但是如果涉及到外部变量的改变，流就力不从心了。因为流要求流内的数据必须是局部变量或者final变量
        // 此处forEach方法，接受一个Consumer函数接口，具体参考TestLambda
        list.forEach(System.out::println);
        //流力不从心之例
        String temp = "";
        final String[] tempArray = {""};
        list.forEach(e->{
//            temp = e;//此处会报错，流内的外部变量必须是不可变的，是final类型的
            String t = e; //局部变量没问题。流的这个限制是为多线程考虑的，防止公共资源的竞争修改问题
            tempArray[0] = e;//可以通过数据、集合的方式来避过这个限制，不过除非万不得已，不建议这么用。
        });

        System.out.println("\n-----------------华丽的分割线------------------\n");

        //计算总数
        System.out.println("计算集合中数据的总数:");
        System.out.println(list.stream().count());

        System.out.println("\n-----------------华丽的分割线------------------\n");

        System.out.println("查找集合中的包含‘o’的第一个元素");
        System.out.println(list.stream().filter(e->e.contains("o")).findAny().orElse("没有找到"));

        System.out.println("\n-----------------华丽的分割线------------------\n");

        //findAny 与 findFirst 的区别在并行中，串行中无区别。findFirst含有顺序的概念
        System.out.println("查找集合中的包含‘z’的第一个元素");
        System.out.println(list.stream().filter(e->e.contains("z")).findFirst().orElse("没有找到"));

        System.out.println("\n-----------------华丽的分割线------------------\n");
        System.out.println("找到集合是否有包含‘b’的元素");
        //接受一个Predicate函数接口
        System.out.println(list.stream().anyMatch(e->e.contains("b")));

        System.out.println("\n-----------------华丽的分割线------------------\n");
        System.out.println("找到集合元素是否都有包含‘b’");
        System.out.println(list.stream().allMatch(e->e.contains("b")));


        System.out.println("\n-----------------华丽的分割线------------------\n");
        System.out.println("找到集合元素是否都不包含‘b’");
        System.out.println(list.stream().noneMatch(e->e.contains("b")));
        System.out.println("找到集合元素是否都不包含‘w’");
        System.out.println(list.stream().noneMatch(e->e.contains("w")));

        System.out.println("\n-----------------华丽的分割线------------------\n");
        System.out.println("找到数组中最大的元素");
        //注意对数值操作的时候，记得使用mapToInt拆箱，极大的提高效率。
        System.out.println(Arrays.stream(integers).mapToInt(Integer::intValue).max().orElse(0));

        System.out.println("\n-----------------华丽的分割线------------------\n");
        System.out.println("找到数组中最小的元素");
        //注意对数值操作的时候，记得使用mapToInt拆箱，极大的提高效率。
        System.out.println(Arrays.stream(integers).mapToInt(Integer::intValue).min().orElseGet(()->0));

        System.out.println("\n-----------------华丽的分割线------------------\n");
        System.out.println("计算数组中平均数");
        //注意对数值操作的时候，记得使用mapToInt拆箱，极大的提高效率。
        System.out.println(Arrays.stream(integers).mapToInt(Integer::intValue).average().orElse(0));

        System.out.println("\n-----------------华丽的分割线------------------\n");
        System.out.println("计算数组中所以元素的和");
        //注意对数值操作的时候，记得使用mapToInt拆箱，极大的提高效率。
        System.out.println(Arrays.stream(integers).mapToInt(Integer::intValue).sum());

        System.out.println("\n-----------------华丽的分割线------------------\n");
        System.out.println("获得数据的所有统计数据");
        IntSummaryStatistics statistics = Arrays.stream(integers).mapToInt(Integer::intValue).summaryStatistics();
        System.out.println("总数："+statistics.getCount()+
                            ",最大值："+statistics.getMax()+
                            ",最小值："+statistics.getMin()+
                            ",平均值："+statistics.getAverage()+
                            ",总和："+statistics.getSum());

        System.out.println("\n-----------------华丽的分割线------------------\n");
        System.out.println("将list中的名称连接在一起");
        //reduce归约：顾名思义，就是将集合中的元素通过某种方式合并成一个元素。
        //参数1：identity(本体) ， 与集合中的类型一致，是归约的初始值。
        //参数2：accumulator(累加) ，函数接口，将集合中的两个元素合并成一个，直至剩下最后一个元素
        //参数3：combiner(连接器)，当使用多线程处理时，这里是用来执行多线程中子任务的连接方式，是对参数2的再加工。单线程时无效
        System.out.println(list.stream().reduce("这是奇点",(o1,o2)->o1+"-"+o2,BinaryOperator.minBy(String::compareTo)));
        //单线程版
        System.out.println(list.stream().reduce("这是奇点",(o1,o2)->o1+"-"+o2));
        //因为无初始值，会返回Optional
        System.out.println(list.stream().reduce((o1,o2)->o1+"-"+o2).orElse("空"));

        System.out.println("\n-----------------华丽的分割线------------------\n");
        System.out.println("最重要也是最常用的收集:collect");
        System.out.println("将集合中所有的名字用','连接在一起");
        System.out.println(list.stream().collect(Collectors.joining(",")));

        System.out.println("找出集合中所有的不含a的名字");
        List<String> l1 = list.stream().filter(e->!e.contains("a")).collect(Collectors.toList());
        System.out.println(l1.stream().collect(joining(",","我是开始：","我是结尾！")));

        System.out.println("计算集合中每个名字的长度");
        List<Integer> l2 = list.stream().collect(mapping(String::length,toList()));
        System.out.println(l2.stream().map(Object::toString).collect(Collectors.joining(",")));

        System.out.println("计算集合中名字的个数");
        System.out.println(list.stream().collect(counting()));

        System.out.println("计算数组的的最小值");
        System.out.println(Arrays.stream(integers).collect(minBy(Integer::compareTo)).orElse(0));

        System.out.println("再次统计数组");
        System.out.println(Arrays.stream(integers).collect(summarizingInt(i->i*10)).getMax());

        System.out.println("用reduce的方式给数组求和");
        System.out.println(Arrays.stream(integers).collect(reducing(Integer::sum)).orElse(0));

        System.out.println("集合中的名字,名字-长度分组");
        Map map = list.stream().collect(groupingBy(e->e));
        System.out.println(map);
//        System.out.println(list.stream().collect(groupingBy(e->e.charAt(0),HashMap::new,joining("-"))));//此处类型推断失败，因为没有返回值

        System.out.println("按");


    }

    /**
     * 测试：并行流
     */
    public static void testParallel(){

    }

    /**
     * 测试：探索Lambda的级联操作
     */
    public static void testCombine(){

    }

    public static void testOthers() {

    }
}
