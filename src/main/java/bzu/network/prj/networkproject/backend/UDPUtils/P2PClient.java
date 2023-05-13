package bzu.network.prj.networkproject.backend.UDPUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.HashMap;

public class P2PClient extends UDPHandler {

    private String clientName;
    private int clientSocketPort ;

    private int clientID ;

    private ArrayList<String> friendsList  = new ArrayList<>();

    public P2PClient(int clientID,String clientName) {
        super();
        this.clientName = clientName;
        this.clientID = clientID;

    }


    public P2PClient(int clientID,int portNumber, String clientName) {
        super(portNumber);
        this.clientName = clientName;
        this.clientSocketPort = portNumber;
        this.clientID = clientID;

    }

    public void startClientConnection () throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Client c = new Client(clientID,getClientSocketPort(),clientName);
        sendPacket(9995,c.toString());

        // to check if the user exist or not

        while(true) {

            DatagramPacket dp0  =  receivePacket();
            String check = new String(dp0.getData()).substring(0,dp0.getLength()).trim();

            if (check.equals("SryExist")) {
                System.out.println("\t<!> Sorry user already exist Try again <!>");
                System.out.print("\n\n > Please Enter your Name : ");
                String name = br.readLine().trim();
                c.setClientName(name);
                setClientName(name);
                sendPacket(9995,c.toString());

            }else
                break;
        }

        while (true) {
            System.out.println("\n       <|[~] Hello <"+getClientName()+"> Welcome to UDP Chat Room [~]|>\n" +
                    "          \n" +
                    "          Please Select One of the Following:\n" +
                    "          1- Show Clients List\n" +
                    "          2- Chat With A Friend\n" +
                    "          3- Exit");

            System.out.print(" > ");

            String input = br.readLine().trim();
            if (input.matches("[0-9]+")) {

                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1 -> {
                        sendPacket(9995, "GetUsers");
                        DatagramPacket dp = receivePacket();
                        String s = new String(dp.getData()).substring(0, dp.getLength());
                        buildClientTable(s);

                    }
                    case 2 -> {
                        System.out.println("\n[?] Please Choose a Client to chat :");
                        System.out.print("[Insert user port ] > ");

                        String input2 = br.readLine().trim();

                        if (input2.matches("[0-9]+")) {

                            int userID = Integer.parseInt(input2);
                            System.out.println("\n[!] Connected... !\n");

                            Thread cliReceiveThread = new Thread(() -> {

                                try {
                                    while (true) {
                                        DatagramPacket dps = receivePacket();
                                        String recvMsg = new String(dps.getData()).substring(0, dps.getLength());
                                        System.out.println("\n[from " + dps.getPort() + " ]> " + recvMsg);
                                        System.out.print("[ ME (" + getClientName() + ")]> ");

                                    }

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            cliReceiveThread.start();

                            while (true) {

                                System.out.print("[ ME (" + getClientName() + ")] > ");

                                String msg = br.readLine();
                                if (!msg.equals("exit"))
                                    sendPacket(userID, msg);
                                else {
                                    sendPacket(userID, getClientName() + " Left the Chat !");
                                        cliReceiveThread.interrupt();
                                        break;
                                }
                            }

                        } else
                            System.out.println("Please Insert Numbers only");

                    } case 3 -> {
                        sendPacket(9995,"CloseMe:"+clientName);
                        getUdpSocket().close();
                        System.exit(0);
                    }
                    default -> System.out.println("Please Choose 1,2,3 Only ");
                }
            }
            else
                System.out.println("Please insert Number");
        }

    }

    public int getClientSocketPort() {
        return clientSocketPort;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return
                    clientName +
                ','+ clientSocketPort
                            +"".trim();
    }

    public static void buildClientTable (String data) {

        String[] split = data.split("\\|");

        System.out.println("\n\t\t\t\t\t\t\t[#] Online Clients List [#]");
        for (String s : split) {
            String[] split2 = s.split(",");
            System.out.println("==================[ userID ]==========[ Port ]===========[ Username ]============\n" +
                    " > Client [#] :\t\t< " + split2[0] + " >\t\t\t  < " + split2[1] + " >\t\t\t   < " + split2[2] +" >"
                    +        "\n=================================================================================\n");
        }
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void setClientSocketPort(int clientSocketPort) {
        this.clientSocketPort = clientSocketPort;
    }

    public ArrayList<String> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(ArrayList<String> friendsList) {
        this.friendsList = friendsList;
    }
}

