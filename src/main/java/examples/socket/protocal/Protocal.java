package examples.socket.protocal;

import java.io.ByteArrayOutputStream;

public class Protocal {
    private ByteArrayOutputStream outstream = new ByteArrayOutputStream();

    public byte[] build(byte[] data) {
        try {
            ProtocalUtils.write((byte) 136, outstream);// 起始字符
            ProtocalUtils.write((byte) 0, outstream);//   标识
            ProtocalUtils.write((byte) 1, outstream);//   序号
            ProtocalUtils.write((byte) 1, outstream);//   总帧数
            ProtocalUtils.write((byte) 1, outstream);//   当前帧
            ProtocalUtils.write((byte) data.length, outstream);//数据长度
            ProtocalUtils.write((byte) 0, outstream);//
            ProtocalUtils.write(data, outstream);//应用数据
            ProtocalUtils.write(ByteUtil.checkData(data), outstream);//异或校验
            ProtocalUtils.write((byte) 24, outstream);//结束字符

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return outstream.toByteArray();
    }

    public ByteArrayOutputStream getOutstream() {
        return outstream;
    }

    public void setOutstream(ByteArrayOutputStream outstream) {
        this.outstream = outstream;
    }


}
