package bzu.network.prj.networkproject.backend.CLI;
import bzu.network.prj.networkproject.backend.UDPUtils.UDPSever;
import java.io.IOException;

public class ServerCLI {

    public static void main(String[] args) throws IOException {

        UDPSever udpSever = new UDPSever(9995);
        udpSever.startServer();

    }
}
