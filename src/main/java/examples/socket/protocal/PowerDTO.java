package examples.socket.protocal;

import java.math.BigDecimal;

public class PowerDTO {
    private String ywlsh;//业务流水号
    private String powerNo;//用户号
    private String powerName;//用户名称
    private String powerAddress;//用户地址
    private BigDecimal powerTotal_EE;// 账号余额
    private BigDecimal powerTotal_QF;//总欠费金额
    private BigDecimal powerTotal_WYJ;//总违约金
    private String powerSJJE;//实缴金额
    private String powerState;//付款控制
    private int powerMoths;//欠费月份数量
    private String powerMoth;//欠费月份
    private String powerNums;//用电量
    private BigDecimal powerDetail_QE;//本月欠费
    private BigDecimal powerDetail_WYJ;//本月违约金
    private BigDecimal powerDetail_YJ;//本月应缴
    private BigDecimal powerDetail_GZ;//规整金额
    private String powerJFSJ;
    private String powerJFRQ;
    private String powerJFFS;
    private int result_code;
    private String message;
    private String type;
    private String userPhone;


    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public BigDecimal getPowerDetail_GZ() {
        return powerDetail_GZ;
    }

    public void setPowerDetail_GZ(BigDecimal powerDetail_GZ) {
        this.powerDetail_GZ = powerDetail_GZ;
    }

    public String getPowerSJJE() {
        return powerSJJE;
    }

    public void setPowerSJJE(String powerSJJE) {
        this.powerSJJE = powerSJJE;
    }

    public String getPowerJFSJ() {
        return powerJFSJ;
    }

    public void setPowerJFSJ(String powerJFSJ) {
        this.powerJFSJ = powerJFSJ;
    }

    public String getPowerJFRQ() {
        return powerJFRQ;
    }

    public void setPowerJFRQ(String powerJFRQ) {
        this.powerJFRQ = powerJFRQ;
    }

    public String getPowerJFFS() {
        return powerJFFS;
    }

    public void setPowerJFFS(String powerJFFS) {
        this.powerJFFS = powerJFFS;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getYwlsh() {
        return ywlsh;
    }

    public void setYwlsh(String ywlsh) {
        this.ywlsh = ywlsh;
    }

    public String getPowerNo() {
        return powerNo;
    }

    public void setPowerNo(String powerNo) {
        this.powerNo = powerNo;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerAddress() {
        return powerAddress;
    }

    public void setPowerAddress(String powerAddress) {
        this.powerAddress = powerAddress;
    }

    public BigDecimal getPowerTotal_EE() {
        return powerTotal_EE;
    }

    public void setPowerTotal_EE(BigDecimal powerTotal_EE) {
        this.powerTotal_EE = powerTotal_EE;
    }

    public BigDecimal getPowerTotal_QF() {
        return powerTotal_QF;
    }

    public void setPowerTotal_QF(BigDecimal powerTotal_QF) {
        this.powerTotal_QF = powerTotal_QF;
    }

    public BigDecimal getPowerTotal_WYJ() {
        return powerTotal_WYJ;
    }

    public void setPowerTotal_WYJ(BigDecimal powerTotal_WYJ) {
        this.powerTotal_WYJ = powerTotal_WYJ;
    }

    public String getPowerState() {
        return powerState;
    }

    public void setPowerState(String powerState) {
        this.powerState = powerState;
    }

    public int getPowerMoths() {
        return powerMoths;
    }

    public void setPowerMoths(int powerMoths) {
        this.powerMoths = powerMoths;
    }

    public String getPowerMoth() {
        return powerMoth;
    }

    public void setPowerMoth(String powerMoth) {
        this.powerMoth = powerMoth;
    }

    public String getPowerNums() {
        return powerNums;
    }

    public void setPowerNums(String powerNums) {
        this.powerNums = powerNums;
    }

    public BigDecimal getPowerDetail_QE() {
        return powerDetail_QE;
    }

    public void setPowerDetail_QE(BigDecimal powerDetail_QE) {
        this.powerDetail_QE = powerDetail_QE;
    }

    public BigDecimal getPowerDetail_WYJ() {
        return powerDetail_WYJ;
    }

    public void setPowerDetail_WYJ(BigDecimal powerDetail_WYJ) {
        this.powerDetail_WYJ = powerDetail_WYJ;
    }

    public BigDecimal getPowerDetail_YJ() {
        return powerDetail_YJ;
    }

    public void setPowerDetail_YJ(BigDecimal powerDetail_YJ) {
        this.powerDetail_YJ = powerDetail_YJ;
    }


}
