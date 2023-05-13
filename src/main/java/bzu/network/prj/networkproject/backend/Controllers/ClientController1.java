package bzu.network.prj.networkproject.backend.Controllers;

import bzu.network.prj.networkproject.backend.UDPUtils.Client;
import bzu.network.prj.networkproject.backend.UDPUtils.UDPHandler;
import bzu.network.prj.networkproject.backend.projectUtils.AlertBox;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientController1 extends UDPHandler implements Initializable {


    @FXML
    private HBox chatHeader;
    @FXML
    private VBox clientListVB;

    @FXML
    private TextField msgTxtField;

    @FXML
    private Label toChatPeerName = new Label();

    @FXML
    private VBox msgVBox;

    @FXML
    private MFXScrollPane msg_SP;

    @FXML
    private Label peerUserName;

    private double x = 0;
    private double y = 0;

    private String clientName;

    private int clientSocketPort ;

    private int clientID ;

    private int sendToPeerPort ;

    private AlertBox altBox = new AlertBox()  ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            Client c = new Client(0, 2222, "Mohammed asd");
            this.clientID = c.getClientID();
            this.clientName = c.getClientName();
            this.clientSocketPort = getUdpSocket().getLocalPort();
            c.setPortNumber(this.clientSocketPort);

            System.out.println("in mainCont 1 port " + clientSocketPort);

            sendPacket(9995,c.toString());
            peerUserName.setText(c.getClientName());
            msgVBox.heightProperty().addListener((observableValue, number, t1) -> msg_SP.setVvalue((double) t1));
            refreshClientTable();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void refreshClientTable() throws IOException {
        sendPacket(9995,"GetUsers");

        DatagramPacket dp = receivePacket();
        String data = new String(dp.getData()).substring(0,dp.getLength());
        System.out.println(" getuser Data : " + data);
        createClientBlocks(data);


    }


    private void createClientBlocks (String data) {

        String[] split = data.split("\\|");
        for (String s : split) {
            String[] split2 = s.split(",");
            System.out.println(split2[0]+','+split2[1]+","+split2[2]);

            if (!(getClientSocketPort() == Integer.parseInt(split2[1]))) {
                updateClientTable(split2[2],split2[1]);
            }
        }
    }

    @FXML
    void draggedScene(MouseEvent event) {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);

    }

    @FXML
    void pressedOnScene(MouseEvent event) {

        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void closeAction(ActionEvent event) {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

    }


    private void updateClientTable (String name,String hiddenPort) {


        HBox clientBox = new HBox();

        clientBox.setAlignment(Pos.CENTER_LEFT);
        clientBox.setPadding(new Insets(0,10,0,20));
        clientBox.setSpacing(5);

        clientBox.setPrefWidth(253);
        clientBox.setMaxWidth(253);
        clientBox.setMinWidth(253);

        clientBox.setPrefHeight(55);
        clientBox.setMaxHeight(55);
        clientBox.setMinHeight(55);

        FontIcon userIcon = new FontIcon("fa-user");
        userIcon.setIconLiteral("fa-user");
        userIcon.setIconColor(Color.WHITE);
        userIcon.setIconSize(30);
        userIcon.setFill(Color.WHITE);
        HBox.setMargin(userIcon,new Insets(0,5,3,0));

        Label peerName = new Label(name);
        peerName.setFont(Font.font("Open Sans", FontWeight.BOLD,14));
        peerName.setTextFill(Color.WHITE);

        peerName.setPrefWidth(150);
        peerName.setMaxWidth(150);
        peerName.setMinWidth(150);

        peerName.setPrefHeight(21);
        peerName.setMinHeight(21);
        peerName.setMaxHeight(21);
        peerName.setPadding(new Insets(0,0,3,0));

        Label timeLabel = new Label("Now");
        timeLabel.setFont(Font.font(13));
        timeLabel.setTextFill(Color.web("#a4a4a4"));
        HBox.setMargin(timeLabel,new Insets(0,0,0,5));


        timeLabel.setPrefWidth(28);
        timeLabel.setMaxWidth(28);
        timeLabel.setMinWidth(28);

        timeLabel.setPrefHeight(18);
        timeLabel.setMinHeight(18);
        timeLabel.setMaxHeight(18);

        Label hiddenP = new Label("hiddenPort");
        hiddenP.setVisible(false);
        hiddenP.setText(hiddenPort);
        System.out.println(hiddenPort);

        clientBox.styleProperty().bind(Bindings.when(clientBox.hoverProperty())
                .then("""
                         -fx-border-color :  #fff;
                        -fx-background-radius :  9px;
                        -fx-background-color :  #19182B;
                        -fx-border-width :  2px;
                        -fx-border-radius :  10px;
                        -fx-font-size: 13px;
                            
                            """)

                .otherwise("""
                        -fx-border-color :  #6545DE;
                        -fx-background-radius :  9px;
                        -fx-background-color :  #19182B;
                        -fx-border-width :  2px;
                        -fx-border-radius :  10px;
                        -fx-font-size: 13px;
                    """));


        Label timeElapsed = new Label();
        timeElapsed.setAlignment(Pos.CENTER);
        timeElapsed.setTextFill(Color.web("#9a9a9a"));


        clientBox.setOnMouseClicked(mouseEvent -> {

            FontIcon onlineIco = new FontIcon("fa-circle");
            onlineIco.setIconLiteral("fa-circle");
            onlineIco.setIconSize(8);
            onlineIco.setIconColor(Color.web("#1eff00"));
            HBox.setMargin(onlineIco,new Insets(3,5,0,0));
            chatHeader.getChildren().add(2,onlineIco);
            toChatPeerName.setText(name);

            msgVBox.getChildren().removeAll();
            this.sendToPeerPort = Integer.parseInt(hiddenPort);
            System.out.println("getUdpSocket().getLocalPort() : " + getUdpSocket().getLocalPort());
            System.out.println("Hidden Port : " + hiddenPort);

            String pattern = "EEEEE MMMMM dd HH:mm";
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, new Locale("en", "US"));
            String dates = simpleDateFormat.format(new Date());
            timeElapsed.setText(dates);
            msgVBox.getChildren().add(timeElapsed);

            receiveMsg();

        });


        clientBox.getChildren().addAll(userIcon,peerName,timeLabel,hiddenP);
        clientListVB.getChildren().add(clientBox);


    }

    private void receiveMsg() {

        Thread receivePacketThread =  new Thread(() -> {

            try {
                while (true) {
                    DatagramPacket dps = receivePacket();
                    String recvMsg = new String(dps.getData()).substring(0, dps.getLength());

                    Platform.runLater( () -> msgVBox.getChildren().add(createMsgBlock(recvMsg,Pos.BOTTOM_LEFT,"#19182B")));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        receivePacketThread.start();
    }

    @FXML
    void sendMsgButtonAction() {

        try {

            if (!msgTxtField.getText().equals("")) {

                if (sendToPeerPort != 0 ) {

                    sendPacket(sendToPeerPort,msgTxtField.getText().trim());
                    System.out.println("sendToPeerPort : " + sendToPeerPort +  "\n my port :" + clientSocketPort + " OS udp port : " + getUdpSocket().getLocalPort()  );
                    msgVBox.getChildren().add(createMsgBlock(msgTxtField.getText().trim(),Pos.BOTTOM_RIGHT,"#4E38A4"));
                    msgTxtField.clear();

                }else
                    altBox.displayPopUp("Please Choose a user to chat with","No Peer Selected",2);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private HBox createMsgBlock (String msg, Pos pos , String bgc) {

        HBox msgHBox = new HBox();

        msgHBox.setAlignment(pos);
        msgHBox.setMaxWidth(470);
        msgHBox.setPrefWidth(470);
        msgHBox.setMinWidth(470);

        TextFlow tFlow = new TextFlow();
        tFlow.setTextAlignment(TextAlignment.LEFT);
        tFlow.setMaxWidth(355);

        tFlow.setPadding(new Insets(5,10,5,10));
        HBox.setMargin(tFlow,new Insets(10,10,10,15));

        Text text = new Text(msg);
        text.setFont(Font.font("Open Sans",FontWeight.NORMAL,13));
        text.setFill(Color.WHITE);

        tFlow.setStyle("                             -fx-background-color : "+bgc+";\n" +
                "                            -fx-text-fill: #9A97A7;\n" +
                "                            -fx-font-size: 14px;\n" +
                "                            -fx-background-radius: 15px;\n" +
                "                            -fx-border-radius: 15px ;\n" +
                "                            -fx-font-family: \"Open Sans\";"

        );

        tFlow.getChildren().add(text);
        msgHBox.getChildren().add(tFlow);

        return  msgHBox;
    }


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getClientSocketPort() {
        return clientSocketPort;
    }

    public void setClientSocketPort(int clientSocketPort) {
        this.clientSocketPort = clientSocketPort;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
