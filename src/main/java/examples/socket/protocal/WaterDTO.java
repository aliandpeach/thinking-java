package examples.socket.protocal;

import java.math.BigDecimal;

public class WaterDTO {
    private String ywlsh;//业务流水号
    private String waterNo;//用户号
    private String waterName;//用户名称
    private String waterAddress;//用户地址
    private BigDecimal waterZHYE;// 账号余额
    private BigDecimal waterQFJE;//总欠费金额
    private BigDecimal waterZNJ;//总违约金
    private BigDecimal waterYJJE;//总违约金
    private String waterState;//付款控制
    private String waterQFYF;//欠费月份
    private String waterYSL;//用电量
    private String waterJFSJ;
    private String waterJFRQ;
    private String waterJFFS;
    private String waterCBRQ;
    private String waterHJSF;//合计水费
    private String waterHJWSF;//合计污水费
    private String waterBCJF;//本次缴费
    private String waterJYJE;//结余金额
    private String waterWYJ;//违约金
    private String waterFJFY;//附加费用
    private String waterYSJE;//应收金额
    private String waterSJJE;//实缴金额
    private int result_code;
    private String message;
    private String type;

    private String userPhone;//用户电话


    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getWaterSJJE() {
        return waterSJJE;
    }

    public void setWaterSJJE(String waterSJJE) {
        this.waterSJJE = waterSJJE;
    }

    public String getWaterHJSF() {
        return waterHJSF;
    }

    public void setWaterHJSF(String waterHJSF) {
        this.waterHJSF = waterHJSF;
    }

    public String getWaterHJWSF() {
        return waterHJWSF;
    }

    public void setWaterHJWSF(String waterHJWSF) {
        this.waterHJWSF = waterHJWSF;
    }

    public String getWaterBCJF() {
        return waterBCJF;
    }

    public void setWaterBCJF(String waterBCJF) {
        this.waterBCJF = waterBCJF;
    }

    public String getWaterJYJE() {
        return waterJYJE;
    }

    public void setWaterJYJE(String waterJYJE) {
        this.waterJYJE = waterJYJE;
    }

    public String getWaterWYJ() {
        return waterWYJ;
    }

    public void setWaterWYJ(String waterWYJ) {
        this.waterWYJ = waterWYJ;
    }

    public String getWaterFJFY() {
        return waterFJFY;
    }

    public void setWaterFJFY(String waterFJFY) {
        this.waterFJFY = waterFJFY;
    }

    public String getWaterYSJE() {
        return waterYSJE;
    }

    public void setWaterYSJE(String waterYSJE) {
        this.waterYSJE = waterYSJE;
    }

    public String getYwlsh() {
        return ywlsh;
    }

    public void setYwlsh(String ywlsh) {
        this.ywlsh = ywlsh;
    }

    public String getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(String waterNo) {
        this.waterNo = waterNo;
    }

    public String getWaterName() {
        return waterName;
    }

    public void setWaterName(String waterName) {
        this.waterName = waterName;
    }

    public String getWaterAddress() {
        return waterAddress;
    }

    public void setWaterAddress(String waterAddress) {
        this.waterAddress = waterAddress;
    }

    public BigDecimal getWaterZHYE() {
        return waterZHYE;
    }

    public void setWaterZHYE(BigDecimal waterZHYE) {
        this.waterZHYE = waterZHYE;
    }

    public BigDecimal getWaterQFJE() {
        return waterQFJE;
    }

    public void setWaterQFJE(BigDecimal waterQFJE) {
        this.waterQFJE = waterQFJE;
    }

    public BigDecimal getWaterZNJ() {
        return waterZNJ;
    }

    public void setWaterZNJ(BigDecimal waterZNJ) {
        this.waterZNJ = waterZNJ;
    }

    public BigDecimal getWaterYJJE() {
        return waterYJJE;
    }

    public void setWaterYJJE(BigDecimal waterYJJE) {
        this.waterYJJE = waterYJJE;
    }

    public String getWaterState() {
        return waterState;
    }

    public void setWaterState(String waterState) {
        this.waterState = waterState;
    }

    public String getWaterQFYF() {
        return waterQFYF;
    }

    public void setWaterQFYF(String waterQFYF) {
        this.waterQFYF = waterQFYF;
    }

    public String getWaterYSL() {
        return waterYSL;
    }

    public void setWaterYSL(String waterYSL) {
        this.waterYSL = waterYSL;
    }

    public String getWaterJFSJ() {
        return waterJFSJ;
    }

    public void setWaterJFSJ(String waterJFSJ) {
        this.waterJFSJ = waterJFSJ;
    }

    public String getWaterJFRQ() {
        return waterJFRQ;
    }

    public void setWaterJFRQ(String waterJFRQ) {
        this.waterJFRQ = waterJFRQ;
    }

    public String getWaterJFFS() {
        return waterJFFS;
    }

    public void setWaterJFFS(String waterJFFS) {
        this.waterJFFS = waterJFFS;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWaterCBRQ() {
        return waterCBRQ;
    }

    public void setWaterCBRQ(String waterCBRQ) {
        this.waterCBRQ = waterCBRQ;
    }

}
