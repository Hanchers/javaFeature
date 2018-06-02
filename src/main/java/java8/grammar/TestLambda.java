package java8.grammar;

import java8.model.TestModel8;

import java.util.Arrays;
import java.util.List;

/**
 * 函数式编程：lambda
 * 以Comparator接口为例
 * <p/>
 * @author Hancher
 * @date Created in 2018年05月23日 17:11
 * @version 1.0
 * @since 1.0
 */
public class TestLambda {
    public static void main(String[] args) {
        //Lambda 演化  testInterface
        //before java8
        TestInterface<String> t1 = new TestInterface<String>() {
            @Override
            public boolean validate(String o) {
                return o.isEmpty();
            }
        };

        //in java8
        TestInterface<String> t2 =(String o) -> {
          return  o.isEmpty();
        };
        TestInterface<String> t3 = (String o) -> o.isEmpty();
        TestInterface<String> t4 = o -> o.isEmpty();
        TestInterface<String> t5 = String::isEmpty;
        TestInterface<String> t6 = TestInterface::validateNullStr;

//        normalLambda();
        lambdaDemo();
    }

    public static void normalLambda(){
        //常用的Lambda
        //开启线程
        System.out.println(Thread.currentThread().getName());
        new Thread(()->System.out.println(Thread.currentThread().getName())).start();

        System.out.println("=============================");

        //排序比较
        List<Integer> list = Arrays.asList(12,4,65,34,232,54,6,7,54,2,78);
        list.forEach(e->System.out.print(e+","));
        list.sort(((o1, o2) -> o1.compareTo(o2)));
//        list.sort((Integer::compareTo));
        System.out.println();
        list.forEach(e->System.out.print(e+","));

        //生产者
        //操作者
        //操作者BiFunction
        //消费者Consumer函数接口 t->void accept(t)
        //Predicate

    }

    public static void lambdaDemo(){
        TestModel8 testModel8 = new TestModel8("a",1);

        System.out.println("before java8, name is a ? :"+testModel8.validate(new TestInterface<TestModel8>() {
            @Override
            public boolean validate(TestModel8 o) {
                return o.getName().equals("a");
            }
        }));

        //判断是否是a
        System.out.println("name is a?:"+testModel8.validate( o ->o.getName().equals("a") ));
        System.out.println("name is a?:"+testModel8.validate( o ->o.getName().equals("b") ));
        //判断值
        System.out.println("value > 0?:"+testModel8.validate( o ->o.getValue()>0 ));
        System.out.println("value > 1?:"+testModel8.validate( o ->o.getValue()>1 ));
        //判断空
        TestInterface<String> t6 = TestInterface::validateNullStr;
        System.out.println("name is null ? :"+t6.validate(testModel8.getName()));
    }

}
