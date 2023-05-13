package bzu.network.prj.networkproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Client1 extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(Client1.class.getResource("fxmls/client1.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(),1042,650, Color.TRANSPARENT));
        stage.setTitle("UDP Chatting App");
        stage.getIcons().add(new Image("file:src/main/resources/bzu/network/prj/networkproject/img/facebook_messenger_48px.png"));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
