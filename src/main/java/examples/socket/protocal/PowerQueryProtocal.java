package examples.socket.protocal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

import examples.socket.service.Global;
import examples.socket.service.Operator;
import net.sf.json.JSONObject;

public class PowerQueryProtocal {
    private String power_no;
    private String agent_id;
    private Operator operator;
    protected ByteArrayInputStream instream = null;
    protected ByteArrayOutputStream outstream = new ByteArrayOutputStream();

    public PowerQueryProtocal(JSONObject jobj) {
        this.power_no = jobj.getString("power_no");
        this.agent_id = jobj.getString("agent_id");
        this.operator = (Operator) Global.OPERATOR_NO.get(this.agent_id);
    }

    public PowerQueryProtocal(String power_no, Operator operator) {
        this.power_no = power_no;
        this.agent_id = operator.getAgentID();
        this.operator = operator;
    }

    public void encode() {
        encodeHeader();
        encodeBody();
        encodeFoot();
    }

    public void encodeHeader() {
        try {
            ProtocalUtils.write((byte) Global.FUNCTION_POWER_QUERY, outstream);
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
            ProtocalUtils.writeVariableString(this.power_no, outstream);
        } catch (Exception ex) {

        }
    }

    public void encodeFoot() {
        try {
            byte[] data = this.outstream.toByteArray();
            System.out.println(ByteUtil.toHexString0x(operator.getMacCode()));
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
            dto.setPowerAddress(ProtocalUtils.readVariableString(instream));
            dto.setPowerTotal_EE(ProtocalUtils.readBigDecimal122(instream));
            dto.setPowerTotal_QF(ProtocalUtils.readBigDecimal122(instream));
            dto.setPowerTotal_WYJ(ProtocalUtils.readBigDecimal122(instream));
            instream.skip(1);
            dto.setPowerMoths(ProtocalUtils.readByte2Int(instream));
            if (dto.getPowerMoths() == 1) {
                dto.setPowerMoth(ProtocalUtils.readBCDString(3, instream));
                dto.setPowerNums(ProtocalUtils.readBCDString(6, instream));
                dto.setPowerDetail_QE(ProtocalUtils.readBigDecimal122(instream));
                dto.setPowerDetail_WYJ(ProtocalUtils.readBigDecimal122(instream));
                dto.setPowerDetail_YJ(ProtocalUtils.readBigDecimal122(instream));
                dto.setPowerDetail_GZ(new BigDecimal(Math.ceil(dto.getPowerDetail_YJ().doubleValue())));

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

    public static void main(String[] args) {
        System.out.println(new BigDecimal(Math.ceil(0.00000D)));
    }


}
