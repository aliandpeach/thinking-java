package examples.socket.protocal;

/**
 * 电费缴费参数类
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2017/12/15
 */
public class PowerPayParam {
    /**
     * 查询返回业务流水号，长度为5
     */
    private String businessNo;
    /**
     * 付款方式：1：现金  2：刷卡
     */
    private byte paymentType;
    /**
     * 缴费金额
     */
    private String paymentAmount;
    /**
     * 电费年月YYYYMM
     */
    private String yearMonth;
    /**
     * 第三方流水号
     */
    private String thirdBusinessNo;
    /**
     * 刷卡途径
     */
    private byte cardWay;
    /**
     * 交易卡号
     */
    private String cardNo;
    /**
     * 系统检索号
     */
    private String searchNo;
    /**
     * 收银机号
     */
    private String cashierNo;
    /**
     * 操作员号
     */
    private String operatorNo;
    /**
     * 交易终端号
     */
    private String terminalNo;
    /**
     * 商户号
     */
    private String merchantNo;
    /**
     * 交易日期YYYYMMDD
     */
    private String tradeDate;
    /**
     * 交易时间HHMMSS
     */
    private String tradeTime;
    /**
     * 银联商户号
     */
    private String unionMerchantNo;
    /**
     * 终端流水号
     */
    private String termBusinessNo;
    /**
     * 终端批次号
     */
    private String termBatchNo;
    /**
     * 发卡行
     */
    private String bank;

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public byte getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(byte paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getThirdBusinessNo() {
        return thirdBusinessNo;
    }

    public void setThirdBusinessNo(String thirdBusinessNo) {
        this.thirdBusinessNo = thirdBusinessNo;
    }

    public byte getCardWay() {
        return cardWay;
    }

    public void setCardWay(byte cardWay) {
        this.cardWay = cardWay;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSearchNo() {
        return searchNo;
    }

    public void setSearchNo(String searchNo) {
        this.searchNo = searchNo;
    }

    public String getCashierNo() {
        return cashierNo;
    }

    public void setCashierNo(String cashierNo) {
        this.cashierNo = cashierNo;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getUnionMerchantNo() {
        return unionMerchantNo;
    }

    public void setUnionMerchantNo(String unionMerchantNo) {
        this.unionMerchantNo = unionMerchantNo;
    }

    public String getTermBusinessNo() {
        return termBusinessNo;
    }

    public void setTermBusinessNo(String termBusinessNo) {
        this.termBusinessNo = termBusinessNo;
    }

    public String getTermBatchNo() {
        return termBatchNo;
    }

    public void setTermBatchNo(String termBatchNo) {
        this.termBatchNo = termBatchNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
