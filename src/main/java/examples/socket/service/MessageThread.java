package examples.socket.service;

import examples.socket.protocal.PowerDTO;
import examples.socket.protocal.WaterDTO;

public class MessageThread extends Thread {

    private PowerDTO powerdto;
    private WaterDTO waterdto;

    public MessageThread(Object obj) {
        if (obj instanceof PowerDTO) {
            this.powerdto = (PowerDTO) obj;
        } else if (obj instanceof WaterDTO) {
            this.waterdto = (WaterDTO) obj;
        }
    }

    public void run() {
        if (powerdto != null) {
            if (powerdto.getType().equals("1")) {
                MessageService.sendMessage(powerdto);
            } else {
                MessageService.sendMessage1(powerdto);
            }

        } else {
            MessageService.sendMessage(waterdto);
        }

    }
}
