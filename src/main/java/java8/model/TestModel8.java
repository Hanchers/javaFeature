package java8.model;

import java8.grammar.TestInterface;

import java.util.function.Supplier;

public class TestModel8 {
    private String name;
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public TestModel8() {
    }

    public TestModel8(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 验证Lambda
     * @param t 自定义的函数接口
     * @return
     */
    public boolean validate(TestInterface<TestModel8> t) {
        return t.validate(this);
    }


}
