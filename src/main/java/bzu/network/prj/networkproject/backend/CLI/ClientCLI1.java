package bzu.network.prj.networkproject.backend.CLI;

import bzu.network.prj.networkproject.backend.UDPUtils.P2PClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientCLI1 {


    public static void main(String[] args) {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("\n\n\t\t\t\t[*] Welcome To UDP Peer-To-Peer Chatting [*]\n\n > Please Enter your Name : ");
            String name = br.readLine().trim();

            //MODIFY PORT & ID
            P2PClient cli = new P2PClient(5,4444,name);
            cli.startClientConnection();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
