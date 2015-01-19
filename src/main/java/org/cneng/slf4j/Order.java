package org.cneng.slf4j;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-6-29
 * Time: 上午11:28
 * Order
 */
public class Order {
    private long id;
    private String describe;
    private String personName;
    private BigDecimal totalPrice;

    public Order() {
    }

    public Order(long id, String describe, String personName, BigDecimal totalPrice) {
        this.id = id;
        this.describe = describe;
        this.personName = personName;
        this.totalPrice = totalPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String toddString() {
        return "Order{" +
                "id=" + id +
                ", describe=" + describe +
                ", personName=" + personName +
                ", totalPrice=" + totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP) +
                "}";
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", describe='" + describe + '\'' +
                ", personName='" + personName + '\'' +
                ", totalPrice=" + totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP) +
                '}';
    }
}
