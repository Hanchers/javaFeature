package java8.grammar;

import java.util.Objects;

/**
 * 体验java8中的函数接口功能<br/>
 * 以及默认方法、静态方法功能
 *
 * <p/>
 * @author Hancher
 * @date Created in 2018年05月24日 9:35
 * @version 1.0
 * @since 1.0
 */
@FunctionalInterface
public interface TestInterface {
    /**
     * 验证一个对象是否合法
     * @param
     * @author Hancher
     * @date  2018年05月24日  10:23
     * @since 1.0
     * @return
     */
    boolean validate(Object o);

    /**
     * 判断对象是否为空
     * @param
     * @author Hancher
     * @date  2018年05月24日  10:26
     * @since 1.0
     * @return
     */
    default boolean validateNull(Object o) {
        return Objects.isNull(o);
    }

    /**
     * 判断对象是否是空字符对象
     * @param
     * @author Hancher
     * @date  2018年05月24日  10:33
     * @since 1.0
     * @return
     */
    static boolean validateNullStr(Object o) {
        if (Objects.isNull(o)) {
            return true;
        }else {
            return ((String)o).isEmpty();
        }
    }
}
