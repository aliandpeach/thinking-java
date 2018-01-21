package examples.socket.protocal;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

import examples.socket.service.Global;
import examples.socket.service.Operator;
import net.sf.json.JSONObject;

public class WaterPayProtocal {
    private JSONObject jobj;
    private Operator operator;
    private String agent_id;
    protected ByteArrayInputStream instream = null;
    protected ByteArrayOutputStream outstream = new ByteArrayOutputStream();

    public WaterPayProtocal(JSONObject jobj) {
        this.jobj = jobj;
        this.agent_id = jobj.getString("agent_id");
        operator = (Operator) Global.OPERATOR_NO.get(this.agent_id);
    }

    public void encode() {
        encodeHeader();
        encodeBody();
        encodeFoot();

    }

    public void encodeHeader() {
        try {
            ProtocalUtils.write((byte) Global.FUNCTION_WATER_PAY, outstream);
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
            ProtocalUtils.writeBCDString(jobj.getString("ywlsh"), 5, outstream);
            ProtocalUtils.write((byte) Global.PAY_TYPE, outstream);
            ProtocalUtils.writeBCDString(jobj.getString("je") + "00", 6, outstream);
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

    public WaterDTO decode(byte[] data) {
        WaterDTO dto = new WaterDTO();
        instream = new ByteArrayInputStream(data);
        try {
            instream.skip(1);
            dto.setResult_code(ProtocalUtils.readByte2Int(instream));
            instream.skip(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (dto.getResult_code() != 0) {
            dto.setMessage("发生错误，无法解析水务返回协议");
            return dto;
        }
        try {

            dto.setYwlsh(ProtocalUtils.readBCDString(5, instream));
            dto.setWaterNo(ProtocalUtils.readVariableString(instream));
            dto.setWaterName(ProtocalUtils.readVariableString(instream));
            dto.setWaterCBRQ(ProtocalUtils.readVariableString(instream));
            dto.setWaterYSL(ProtocalUtils.readBCDString(6, instream));
            dto.setWaterHJSF(ProtocalUtils.readBCDString(6, instream));
            dto.setWaterHJWSF(ProtocalUtils.readBCDString(6, instream));
            dto.setWaterBCJF(ProtocalUtils.readBCDString(6, instream));
            dto.setWaterJYJE(ProtocalUtils.readBCDString(6, instream));
            dto.setWaterWYJ(ProtocalUtils.readBCDString(6, instream));
            dto.setWaterFJFY(ProtocalUtils.readBCDString(6, instream));
            dto.setWaterYSJE(ProtocalUtils.readBCDString(6, instream));
            dto.setWaterJFSJ(ProtocalUtils.readBCDString(7, instream));
            dto.setWaterJFRQ(dto.getWaterJFSJ().substring(0, 8));
            dto.setWaterJFFS("1");//水务只能缴纳欠费，无法缴纳预存

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
