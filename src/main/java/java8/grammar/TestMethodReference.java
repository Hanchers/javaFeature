package java8.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * java8新特性，方法引用示例（特殊的Lambda） <br/>
 * 1、静态方法引用	Class::staticMethodName  <br/>
 * 2、实例的成员方法引用	instance::instanceMethodName  <br/>
 * 3、类的成员方法引用	Class::methodName  <br/>
 * 4、类的构造器引用	Class::new  <br/>
 *
 * <p/>
 * @author Hancher
 * @date Created in 2018年06月01日 10:06
 * @version 1.0
 * @since 1.0
 */
public class TestMethodReference {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(5,6,7,8,9,1,2,3,4,5,6,7);
        List<Integer> temp = new ArrayList<>(list);

        //静态方法引用
        //场景：Lambda入参就是静态方法的入参 （arg）-> ClassName.method(arg)
        //变为：ClassName::method
        System.out.println("\n测试：静态方法引用");
        list.stream().map(i->i+",").forEach(e->System.out.print(e));
        System.out.println();//换行
        list.stream().map(i->i+",").forEach(System.out::print);

        System.out.println("\n-------------------我是华丽的分割线------------------------\n");

        //类成员引用
        //场景：Lambda的第一个入参是实例，剩余的参数为方法入参 （instance,arg）-> instance.method(arg)
        //变为：ClassName::method
        //实例：典型的compareTo方法
        System.out.println("\n测试：类成员引用");
        list.sort(((o1, o2) -> o1.compareTo(o2)));
        list.stream().map(i->i+",").forEach(System.out::print);
        System.out.println();//换行
        list = temp;//复位
        list.sort(Integer::compareTo);
        list.stream().map(i->i+",").forEach(System.out::print);

        System.out.println("\n-------------------我是华丽的分割线------------------------\n");

        //实例成员引用
        //场景：Lambda的入参是实例的方法入参，此时实例是得到的，如成员实例。（arg）->instance.methon(arg)
        //变为：instance::method
        System.out.println("\n测试：实例成员引用");
        TestMethodReference methodReference = new TestMethodReference();//实例
        list.stream().map(i->methodReference.multiply10(i)).map(i->i+",").forEach(System.out::print);
        System.out.println();
        list.stream().map(methodReference::multiply10).map(i->i+",").forEach(System.out::print);

        System.out.println("\n-------------------我是华丽的分割线------------------------\n");

        //构造器：空,一般用于Supplier函数接口
        //场景：通过无参构造器生成一个实例，基本是用于Supplier, ()-> new ClassName();
        //变为：ClassName::new
        System.out.println("\n测试：构造器引用");
        Stream.generate(()->new Date()).limit(1).forEach(System.out::println);
        Stream.generate(Date::new).limit(1).forEach(System.out::println);
    }

    /**
     * origin扩大10倍
     * @param origin 原数
     * @return
     */
    public Integer multiply10(Integer origin) {
        return origin*10;
    }
}
