package bzu.network.prj.networkproject.backend.UDPUtils;

import java.io.IOException;
import java.net.*;

public class UDPHandler {

        private DatagramSocket udpSocket;
        private  int portNumber ;

    public UDPHandler () {

        try {
            udpSocket = new DatagramSocket();
            this.portNumber = udpSocket.getPort();

        } catch (SocketException e) {
            udpSocket.close();
            throw new RuntimeException(e);

        }
    }

        public UDPHandler (int portNumber) {

            try {
                udpSocket = new DatagramSocket(portNumber);
                this.portNumber = portNumber;

            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }

        private DatagramPacket initializeSendPacket (int toPort, String msg) throws UnknownHostException {
            DatagramPacket sendPacket ;
            byte[] buffData = msg.getBytes();
            InetAddress ip = InetAddress.getLocalHost();
            sendPacket = new DatagramPacket(buffData,0,buffData.length,ip,toPort);
            return sendPacket;
        }

    public void sendPacket (int toPort, String msg) throws IOException {

            DatagramPacket dataPacket = initializeSendPacket(toPort,msg);
            udpSocket.send(dataPacket);

    }

        private DatagramPacket initializeRecvPacket () {

            DatagramPacket receivePacket ;
            byte[] buffData = new byte[512];
            receivePacket = new DatagramPacket(buffData, 0,buffData.length);
            return receivePacket;
        }

        public DatagramPacket receivePacket () throws IOException {
            DatagramPacket receivePacket = initializeRecvPacket();
            udpSocket.receive(receivePacket);
            return receivePacket;
        }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public DatagramSocket getUdpSocket() {
        return udpSocket;
    }
}
