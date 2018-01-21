package examples.socket.service;

public class Operator {
    private String agentID;
    private String operatorID;
    private String termialNo;
    private String password;
    private byte[] macCode = new byte[8];

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public String getTermialNo() {
        return termialNo;
    }

    public void setTermialNo(String termialNo) {
        this.termialNo = termialNo;
    }

    public byte[] getMacCode() {
        return macCode;
    }

    public void setMacCode(byte[] macCode) {
        this.macCode = macCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
