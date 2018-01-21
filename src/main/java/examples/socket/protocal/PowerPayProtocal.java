package examples.socket.protocal;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

import examples.socket.service.Global;
import examples.socket.service.Operator;
import net.sf.json.JSONObject;

public class PowerPayProtocal {
    private JSONObject jobj;
    private Operator operator;
    private PowerPayParam payParam;
    private String agent_id;
    protected ByteArrayInputStream instream = null;
    protected ByteArrayOutputStream outstream = new ByteArrayOutputStream();

    public PowerPayProtocal(JSONObject jobj) {
        this.jobj = jobj;
        this.agent_id = jobj.getString("agent_id");
        operator = (Operator) Global.OPERATOR_NO.get(this.agent_id);
    }
    public PowerPayProtocal(Operator operator, PowerPayParam payParam) {
        this.operator = operator;
        this.payParam = payParam;
    }

    public void encode() {
        encodeHeader();
        encodeBody();
        encodeFoot();
    }

    public void encodeHeader() {
        try {
            ProtocalUtils.write((byte) Global.FUNCTION_POWER_PAY, outstream);
            ProtocalUtils.writeVariableString(operator.getOperatorID(), outstream);
            ProtocalUtils.writeVariableString(operator.getTermialNo(), outstream);
            ProtocalUtils.write((byte) Global.TERMIAL_TYPE, outstream);
            ProtocalUtils.write((byte) Global.TERMIAL_MARK, outstream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void encodeBody() {
        try {
            ProtocalUtils.writeBCDString(payParam.getBusinessNo(), 5, outstream);
            ProtocalUtils.write((byte) Global.PAY_TYPE, outstream);
            ProtocalUtils.writeBCDString(payParam.getPaymentAmount(), 6, outstream);
            ProtocalUtils.writeBCDString(payParam.getYearMonth(), 3, outstream);
            ProtocalUtils.writeVariableString("", outstream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void encodeFoot() {
        try {
            byte[] data = this.outstream.toByteArray();
            byte[] mac = DESHelper.mac(operator.getMacCode(), data);
            ProtocalUtils.write(mac, outstream);

        } catch (Exception ex) {

        }
    }

    public PowerDTO decode(byte[] data) {
        PowerDTO dto = new PowerDTO();
        instream = new ByteArrayInputStream(data);
        try {
            instream.skip(1);
            dto.setResult_code(ProtocalUtils.readByte2Int(instream));
            instream.skip(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (dto.getResult_code() != 0) {
            dto.setMessage("发生错误，无法解析电力返回协议");
            return dto;
        }
        try {

            dto.setYwlsh(ProtocalUtils.readBCDString(5, instream));
            dto.setPowerNo(ProtocalUtils.readVariableString(instream));
            dto.setPowerName(ProtocalUtils.readVariableString(instream));
            dto.setPowerJFSJ(ProtocalUtils.readBCDString(7, instream));
            dto.setPowerMoth(ProtocalUtils.readBCDString(3, instream));
            instream.skip(6);  //结余暂不提供
            dto.setPowerSJJE(ProtocalUtils.readBCDString(6, instream));
            dto.setPowerNums(ProtocalUtils.readBCDString(6, instream));
            dto.setPowerDetail_QE(ProtocalUtils.readBigDecimal122(instream));
            dto.setPowerDetail_WYJ(ProtocalUtils.readBigDecimal122(instream));
            dto.setPowerDetail_YJ(ProtocalUtils.readBigDecimal122(instream));
            dto.setPowerJFRQ(dto.getPowerJFSJ().substring(0, 8));
            if (dto.getPowerMoth().equals("000000")) {
                dto.setPowerJFFS("2");
            } else {
                dto.setPowerJFFS("1");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dto;
    }

    public ByteArrayInputStream getInstream() {
        return instream;
    }

    public void setInstream(ByteArrayInputStream instream) {
        this.instream = instream;
    }

    public ByteArrayOutputStream getOutstream() {
        return outstream;
    }

    public void setOutstream(ByteArrayOutputStream outstream) {
        this.outstream = outstream;
    }


}
