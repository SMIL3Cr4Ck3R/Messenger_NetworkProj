package bzu.network.prj.networkproject.backend.UDPUtils;

public class Client {


    private int portNumber;
    private String clientName ;

    private int clientID;

    public Client(int clientID ,int portNumber, String  clientName) {

        this.clientID = clientID;
        this.portNumber = portNumber;
        this.clientName = clientName;

    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public String toString() {
        return clientID + "," + portNumber  + "," + clientName;
    }
}
