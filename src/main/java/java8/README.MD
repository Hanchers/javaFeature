# java8新特性简介

### Java8简介
java8由oracle公司于2014年3月18日发布，是java5以来最重要的升级，这次升级加入了很多其他语言很早就有的特性，如函数式编程等。这个版本包含语言、编译器、库、工具和JVM等方面的十多个新特性。一举挽回了近年来java逐渐没落的趋势。

### java8的重要特性
#### 1、语法
##### 1.1 更好的类型推断
java8在编译器层级做了很大的优化，可以很好的在方法调用，声明过程中，推断出实际的参数类型，做到代码的精简与语法的优化。  
例如：

```java
//demo1: 实例创建过程中使用(最常用)
Map<String, List<String>> myMap = new HashMap<String, List<String>>();//older
Map<String, List<String>> myMap = new HashMap<>(); // java8

//demo2: 方法调用过程中，通过泛型推导
public static <U> void addBox(U u, java.util.List<Box<U>> boxes) ; //方法声明，所在类BoxDemo
//方法调用
BoxDemo.<Integer>addBox(Integer.valueOf(10), listOfIntegerBoxes); //older
BoxDemo.addBox(Integer.valueOf(20), listOfIntegerBoxes); //java8
```
总之：java8中可以更好的通过泛型等方式，推断出方法的入参、声明中的实际参数，从而省略一些繁琐的代码。

##### 1.2 方法引用
顾名思义，就是通过方法名的方式来实现对方法的调用。  
目前，共有四种类型的方法引用:
类型 | 例子 
---|---
静态方法引用 | Class::staticMethodName
实例的成员方法引用 | instance::instanceMethodName
类的成员方法引用 | Class::methodName
类的构造器引用 | Class::new


##### 1.3 接口默认方法+静态方法 
java8之前，在接口中只能定义方法的声明，不能有方法的实现。但是，这样的规定也限制了java功能的扩展与升级，因为每增加一个方法就要对很多实现类改造。  
java8中，可以在接口中增加默认的方法实现，和抽象类类似。这样，就为java8接口的升级改造铺平了道路，同时避免了已有系统的大规模改造。 

```java
@FunctionalInterface
public interface TestInterface {
   
    boolean validate(Object o);

    default boolean validateNull(Object o) {
        return Objects.isNull(o);
    }

    static boolean validateNullStr(Object o) {
        if (Objects.isNull(o)) {
            return true;
        }else {
            return ((String)o).isEmpty();
        }
    }
}
```


##### 1.4 Lambda 表达式(敲黑板) 
Lambda表达式（也称为闭包）是Java 8中最大和最令人期待的语言改变。它允许我们将函数当成参数传递给某个方法，或者把代码本身当作数据处理：函数式开发者非常熟悉这些概念。很多JVM平台上的语言（Groovy、Scala等）从诞生之日就支持Lambda表达式，但是Java开发者没有选择，只能使用匿名内部类代替Lambda表达式。所以Lambda表达式可以用来代替繁琐难看的匿名内部类。可以说，java8中上面几点的改进，就是为Lambda表达式做铺垫。  
使用方式：()->{}  
先决条件：需要有函数接口或类函数接口的实现  
函数接口：只有一个抽象方法的接口就是函数接口，为了防止接口被误添方法，可以通过@FunctionalInterface来加强。默认方法对此不影响。  
优势：  
1. 代码传递！java除值传递外又多了一种传递方式。实现了代码的定制化。
2. 取代匿名内部类  
3. 简化代码风格，实现良好的可读性 ，毕竟代码最终还是给人读的。
4. 未完待续  
以Comparator接口示例：

```java
//Comparator接口  
public interface Comparator<T> {
    int compare(T o1, T o2);
}

//lambda使用
(Integer o1,Integer o2) -> {o1.compareTo(o2)};
(o1, o2) -> {o1.compareTo(o2)};
(o1, o2) -> o1.compareTo(o2);
```

##### 1.5 重复注解
在开发过程中，难免会遇到要对一个类型重复注解的情况，比如我想有一个过滤器注解，既可以过滤一个请求，有可以组合实现过滤不同的请求。以前可能会通过注解传多值的方式来实现，现在也可以通过重复注解来实现了。

```java
@Alert(role="Manager")
@Alert(role="Administrator")
public class UnauthorizedAccessException extends SecurityException { ... }
```


##### 1.6 注解几乎可以用在地方
在java8之前，注解仅能用于**声明**;  
但是随着java8的发布，注解几乎可以用于任何类型。这意味着注解可以在任何地方的类型上使用。如：
- 对象创建的时候
```
new @Interned MyObject();
```
- 类型强转的时候
```
myString = (@NonNull String) str;
```
- 接口实现时（implements）
```
class UnmodifiableList<T> implements  @Readonly List<@Readonly T> { ... }
```
- 声明抛出异常时
```
void monitorTemperature() throws @Critical TemperatureException { ... }
```


##### 1.7 方法参数反射
java8对反射功能做了增强，可以通过反射的方式，获取方法的入参名称，无论是构造方案还是普通方法。  
但是，为了不占用过多的内存，这个功能必须启动时添加 -parameters 来开启，同时会生成一个特殊的class文件来保存这些入参名称。
##### 1.8 Opthional引入
为了解决恶名昭著的java空指针问题，在Java8中引入了Optional类，其作用就是“包装”，在值之外包上一个盒子，这样空指针就不存在了。从而在编码时，可以避免空判断，达到更优雅的代码。  
何时使用：个人认为在写代码的时候，对于那些可能会存在空值得地址，可以使用Optional来包装，而不会有空值的地方，则不用，从而可以一眼看出值得特性，以及是否需要空判断。  
Optional提供了三种方式来创建Optional对象：

```java
Optional<T> t = Optional.empty();//创建一个空对象
Optional<T> t = Optional.of(value);//创建一个非空对象
Optional<T> t = Optional.ofNullable(value);//创建一个可能是空的对象

//使用-取值
t.get();//不安全，值不存在会抛 NoSuchElementException
t.isPresent(); //判断t是否有值
t.orElse(T other); //值存在，返回值；否则，返回other
t.orElseGet(Supplier<? extends T> other); //orElse升级版，接受一个Lambda表达式，自定义and延迟加载 默认返回值。
```


#### 2 stream流(敲黑板)
java8的另一大升级，就是流的加入，其与Lambda表达式是相辅相成的。  
流可以理解为对集合中数据的流水线处理，是一种高级的迭代方式。为了让集合支持流的功能，java8对集合接口做了增强，也就是通过增加大量的默认方法来支持。  
流本身不产生数据，只是对集合中的数据做流水线性的处理。所以，流大致可以分为三个阶段：
- 生成阶段  
  即将集合中的数据转换到流中。流的数据常来自三种：集合、数组、自生成。

```
Collection.stream();
Arrays.stream(T[]);
Stream.of("a", "b", "c");
```
- 中间操作  
  流的中间操作是可以重复使用的、可叠加的。  
  常用的中间操作有：
    - filter(过滤)
    - map(类型转换)
    - distinct（去重）
    - sorted（排序）
    - parallel(转为并行流)
    - limit(限制数量)
    - skip（跳过数量）
    - flatMap(并行流类型转换合一)
    - peek(偷窥)：操作流中元素并返回原流，常用于debug.
- 终端操作  
  终端操作是互斥的，唯一的。  
  常用的终端操作：
    - forEach（遍历）：代替for/foreach
    - collect(收集)：有现成的collector工具类可用，很方便
    - count（总数）
    - reduce（归约）
    - findAny(查找到元素)
    - findFirst(查找到第一个元素)：并行时与findAny有区别
    - allMatch(所有数据满足条件)
    - anyMatch(任意数据满足条件)
    - noneMatch(没有数据满足条件)
    - min(找到最小的元素)
    - max(找到最大的元素)  

此外，在简单说一下，流是分为单向流与并发流的。  
单向流：没有特殊说明，我们使用的流都是单向流，也就是单线程流。  
并发流：凡是parallel开头的流或操作，都是使用了并发流，也就是使用了多线程，极大的简化了多线程的入口。  
比较：并发流并不是任何时候都好，1是性能开销，2是并发流底层是使用了一个线程池，如果并发很多的时候，可能会造成等待的情况（自己考虑的，尚未在网上找到任何说明），3就是一些操作对并发很不友好,如limit,findFirst等。所以，除非是数据量很大或性能消耗很大的时候，不建议用并行流。
    
java8中的流是一个很大的话题，这里只是简单介绍一下基本操作，具体还要深入了解。  
流参考文档：[Java 8 中的 Streams API 详解](https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/)，
[Java8 新特性之流式数据处理](https://www.cnblogs.com/shenlanzhizun/p/6027042.html)，
[Java Stream API入门篇](http://www.cnblogs.com/CarpenterLee/p/6545321.html)，
[java8 并行流使用](https://blog.csdn.net/gdouchufu/article/details/29242429)



#### 3、新的时间API
时间，本身就是世界上最难让人懂得事物，在物理界，难倒了一群世界上最聪明的人；生活中，时间标准也各不相同，如阳历、阴历、农历，又如UTC(协调世界时)与GMT(格林威治时间)   

同样，在Java世界，时间的管理也同样混乱，从开始的Date,到后来被Calendar取代，到如今，看来Calendar也晚节不保，被更年轻的Time所取代，但愿这是最后的改朝换代吧。  

在最新的time Api中，提供了对日期、时间、原子时、时区提供了一套简单易用的安全，确实比以前的时间类更加易用了。  
相关类：  
- LocalDate : 本地日期
- LocalTime ：本地时间
- LocalDateTime ：本地日期时间
- ZonedDateTime : 时区时间
- Instant ：UTC，当前的世界标准时间。可以理解为0时区时间，但是本质上不一样。
- Clock : 时间钟，基准时间
- Duration ：时间段，基于time
- Period : 时间段，基于date
- ZoneId : 时区

通过上面这些类，基本就可以解决现有的日常时间问题了。接下来会有例子展示使用。  

说一下与Date的转换，这是所有java时间类避免不了的。

```
//唯一中介 Instant
Date date = Date.from(instant);
Instant instant = date.toInstant();
```

time类的通用方法：

    of - static factory method
    parse - static factory method focussed on parsing
    get - gets the value of something
    is - checks if something is true
    with - the immutable equivalent of a setter
    plus - adds an amount to an object
    minus - subtracts an amount from an object
    to - converts this object to another type
    at - combines this object with another, such as date.atTime(time)

最后提个问题：如果要国际化，各地的时间该怎么处理？

[官方API](https://docs.oracle.com/javase/8/docs/technotes/guides/datetime/index.html)

#### 4、杂
##### 4.1 Javascript引擎由Rhino 换为Nashorn
Java 8提供了新的Nashorn JavaScript引擎，使得我们可以在JVM上开发和运行JS应用。Nashorn JavaScript引擎是javax.script.ScriptEngine的另一个实现版本，这类Script引擎遵循相同的规则，允许Java和JavaScript交互使用，例子代码如下：

```
ScriptEngineManager manager = new ScriptEngineManager();
ScriptEngine engine = manager.getEngineByName( "JavaScript" );

System.out.println( engine.getClass().getName() );
System.out.println( "Result:" + engine.eval( "function f() { return 1; }; f() + 1;" ) );
```
 这个代码的输出结果如下：

```
jdk.nashorn.api.scripting.NashornScriptEngine
Result: 2
```
[官方文档](https://docs.oracle.com/javase/8/docs/technotes/guides/scripting/nashorn/)
##### 4.2 Base64引入java库
如题，不用依赖第三方库了。

##### 4.3 HotSpot虚拟机去掉了PermGen
再也不会遇到这个异常了：java.lang.OutOfMemoryError： PermGen space  
但是并不意味着内存溢出异常没有了。
##### 4.4 接口的默认方法是由字节码层级支持的
##### 4.5 hashmap性能优化


#### X.总结
1. java8的这次重大升级可以说完全是围绕着Lambda表达式和Stream流这两个核心来实现的。
2. 总的来说java8的这次升级，并没有提供很突出的特性，只能说拉平了与其他语言的差距。
3. 使用java8的新特性，确实能够使代码变得简介美观。
4. 

### 参考文档
官方文档：[What's New in JDK8](http://www.oracle.com/technetwork/java/javase/8-whats-new-2157071.html)  
[JAVA8 十大新特性详解](https://blog.csdn.net/u014470581/article/details/54944384)
