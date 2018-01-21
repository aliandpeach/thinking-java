package examples.socket.protocal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import examples.socket.service.Global;
import examples.socket.service.Operator;

public class LoginProtocal {
    private ByteArrayInputStream instream = null;
    private ByteArrayOutputStream outstream = new ByteArrayOutputStream();
    private Operator operator;

    public LoginProtocal(Operator operator) {
        this.operator = operator;
    }

    public void encode() {
        encodeHeader();
        encodeBody();
        encodeFoot();
    }

    public void encodeHeader() {
        try {
            ProtocalUtils.write((byte) Global.FUNCTION_LOGIN, outstream);
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
            // 登陆密码
            byte[] password = DESHelper.encrypt(ProtocalUtils.getBytes(8), operator.getPassword().getBytes());
            ProtocalUtils.write(password, outstream);
            // SIM卡号
            ProtocalUtils.writeVariableString("0", outstream);
            // 硬件版本号
            ProtocalUtils.writeVariableString("1", outstream);
            // 软件版本号
            ProtocalUtils.writeVariableString("1", outstream);
        } catch (Exception ex) {
        }
    }

    public void encodeFoot() {
        try {
//            byte[] data = this.outstream.toByteArray();
            byte[] data = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
            byte[] mac = DESHelper.mac(ProtocalUtils.getBytes(8), data);
            ProtocalUtils.write(mac, outstream);
        } catch (Exception ex) {

        }
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
