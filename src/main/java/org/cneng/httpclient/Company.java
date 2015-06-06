package org.cneng.httpclient;

import java.util.Date;

/**
 * 企业类
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/6/6
 */
public class Company {
    /** 企业名称 */
    private String companyName;
    /** 注册号 */
    private String taxno;
    /** 法定代表人 */
    private String lawPerson;
    /** 成立日期 */
    private Date regDate;
    /** 住所 */
    private String location;
    /** 经营范围 */
    private String business;
    /** 股东/发起人 */
    private String stockholder;
    /** 具体经营项目 */
    private String detail;
    /** 是否有违法 */
    private String illegal;
    /** 是否有行政处罚 */
    private String penalty;
    /** 是否经常异常 */
    private String exception;

    /**
     * Gets illegal.
     *
     * @return Value of illegal.
     */
    public String getIllegal() {
        return illegal;
    }

    /**
     * Sets new location.
     *
     * @param location New value of location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets new detail.
     *
     * @param detail New value of detail.
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Sets new companyName.
     *
     * @param companyName New value of companyName.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets lawPerson.
     *
     * @return Value of lawPerson.
     */
    public String getLawPerson() {
        return lawPerson;
    }

    /**
     * Gets location.
     *
     * @return Value of location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets business.
     *
     * @return Value of business.
     */
    public String getBusiness() {
        return business;
    }

    /**
     * Sets new illegal.
     *
     * @param illegal New value of illegal.
     */
    public void setIllegal(String illegal) {
        this.illegal = illegal;
    }

    /**
     * Gets stockholder.
     *
     * @return Value of stockholder.
     */
    public String getStockholder() {
        return stockholder;
    }

    /**
     * Sets new regDate.
     *
     * @param regDate New value of regDate.
     */
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    /**
     * Gets companyName.
     *
     * @return Value of companyName.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Gets exception.
     *
     * @return Value of exception.
     */
    public String getException() {
        return exception;
    }

    /**
     * Sets new lawPerson.
     *
     * @param lawPerson New value of lawPerson.
     */
    public void setLawPerson(String lawPerson) {
        this.lawPerson = lawPerson;
    }

    /**
     * Sets new exception.
     *
     * @param exception New value of exception.
     */
    public void setException(String exception) {
        this.exception = exception;
    }

    /**
     * Gets detail.
     *
     * @return Value of detail.
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Gets regDate.
     *
     * @return Value of regDate.
     */
    public Date getRegDate() {
        return regDate;
    }

    /**
     * Sets new business.
     *
     * @param business New value of business.
     */
    public void setBusiness(String business) {
        this.business = business;
    }

    /**
     * Gets penalty.
     *
     * @return Value of penalty.
     */
    public String getPenalty() {
        return penalty;
    }

    /**
     * Sets new penalty.
     *
     * @param penalty New value of penalty.
     */
    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    /**
     * Gets taxno.
     *
     * @return Value of taxno.
     */
    public String getTaxno() {
        return taxno;
    }

    /**
     * Sets new taxno.
     *
     * @param taxno New value of taxno.
     */
    public void setTaxno(String taxno) {
        this.taxno = taxno;
    }

    /**
     * Sets new stockholder.
     *
     * @param stockholder New value of stockholder.
     */
    public void setStockholder(String stockholder) {
        this.stockholder = stockholder;
    }
}
