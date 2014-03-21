package ch20annotation.simple;

/**
 * 一个枚举，为注解做数据准备.
 * <p/>
 * 正则表达式规则枚举
 *
 * @author XiongNeng
 * @version 1.0
 * @since 14-3-21
 */
public enum RegexRule {
    /**
     * email正則表達式規則
     */
    EMAIL("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"),
    /**
     * 數字正則表達式規則
     */
    NUMBER("^[0-9]*$");

    public String value;

    RegexRule(String value) {
        this.value = value;
    }
}
