package examples.socket.protocal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

import examples.socket.service.Global;
import examples.socket.service.Operator;
import net.sf.json.JSONObject;

public class WaterQueryProtocal {
    private String water_no;
    private String qfyf;
    private Operator operator;
    protected ByteArrayInputStream instream = null;
    protected ByteArrayOutputStream outstream = new ByteArrayOutputStream();

    public WaterQueryProtocal(JSONObject jobj) {
        this.water_no = water_no;
        this.qfyf = qfyf;
        this.operator = (Operator) Global.OPERATOR_NO.get(jobj.getString("agent_id"));
    }

    public void encode() {
        encodeHeader();
        encodeBody();
        encodeFoot();

    }

    public void encodeHeader() {
        try {
            ProtocalUtils.write((byte) Global.FUNCTION_WATER_QUERY, outstream);
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
            ProtocalUtils.writeVariableString(this.water_no, outstream);
            ProtocalUtils.writeBCDString(this.qfyf, 4, outstream);
        } catch (Exception ex) {

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
            dto.setWaterAddress(ProtocalUtils.readVariableString(instream));
            dto.setWaterZHYE(ProtocalUtils.readBigDecimal122(instream));
            dto.setWaterQFYF(ProtocalUtils.readBCDString(4, instream));
            dto.setWaterYSL(ProtocalUtils.readBCDString(6, instream));
            dto.setWaterCBRQ(ProtocalUtils.readBCDString(4, instream));
            dto.setWaterQFJE(ProtocalUtils.readBigDecimal122(instream));
            dto.setWaterZNJ(ProtocalUtils.readBigDecimal122(instream));
            dto.setWaterYJJE(ProtocalUtils.readBigDecimal122(instream));
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
