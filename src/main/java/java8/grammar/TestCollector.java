package java8.grammar;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 流收集器的终极武器：自己定制
 * 参考 Collectors#joining()
 * <p/>
 * @author Hancher
 * @date Created in 2018年06月03日 0:58
 * @version 1.0
 * @since 1.0
 */
public class TestCollector implements Collector<String, StringBuilder, String> {
    @Override
    public Supplier<StringBuilder> supplier() {
        return StringBuilder::new;
    }

    @Override
    public BiConsumer<StringBuilder, String> accumulator() {
        return (a,t)->{
            if (a.length() == 0) {
                a.append(t);
            } else {
                a.append(",").append(t);
            }
        };
    }

    @Override
    public BinaryOperator<StringBuilder> combiner() {
        return StringBuilder::append;
    }

    @Override
    public Function<StringBuilder, String> finisher() {
        return StringBuilder::toString;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }

}
