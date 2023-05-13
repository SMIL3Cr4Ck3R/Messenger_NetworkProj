package bzu.network.prj.networkproject.backend.UDPUtils;

import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;

public class UDPSever extends UDPHandler {

    HashMap<String, Client> clientList = new HashMap<>();

    public UDPSever (int portNumber) {

        super(portNumber);
        System.out.println("\n \t\t\t\t\t [!] CHATTING UDP SERVER [!]\n  " +
                                    "[~]> Server Initiated !\n  Waiting For Clients to Connect ...\n");

    }

    public void startServer () {

        try {
            while (true) {

                DatagramPacket dp = receivePacket();
                byte[] data = dp.getData();
                String clientHeader = new String(data).substring(0,dp.getLength());

                if (clientHeader.equals("GetUsers")) {

                    StringBuilder sb = new StringBuilder();

                    for (Map.Entry<String, Client> entry : clientList.entrySet() ) {
                        sb.append(entry.getValue()).append("|");
                    }
                    System.out.println(" >>> Data Sent to Client @ Port : " + dp.getPort());
                    sendPacket(dp.getPort(),sb.toString());

                }else if (clientHeader.matches("CloseMe:[a-zA-Z0-9]+")) {
                    String[] split = clientHeader.split(":");
                    clientList.remove(split[1]);
                    System.out.println("Client Removed :D !");

                }else {

                    String[] split = clientHeader.split(",");
                    Client c = new Client(Integer.parseInt(split[0]),Integer.parseInt(split[1]),split[2]);

                    if (!clientList.containsKey(c.getClientName())) {
                            clientList.put(c.getClientName(),c);
                            System.out.println(" [!]> Client Connected @ [ Port : " + split[1] + " | Name: "+ split[2] +
                                    "]\n-------------------------------------------------------------------");
                    }else {
                        System.out.println(" User already exists warning client");
                        sendPacket(c.getPortNumber(), "SryExist");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
