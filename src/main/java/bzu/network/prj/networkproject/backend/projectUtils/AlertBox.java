package bzu.network.prj.networkproject.backend.projectUtils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    private double height;
    private double width;


    public AlertBox() {
        this(100, 500);
    }

    public AlertBox(double height, double width) {

        this.height = height;
        this.width = width;

    }

    public void displayPopUp(String popMessage, String popTitle, int status) {

        Stage stg = new Stage();

        VBox vbb = new VBox();
        HBox hb = new HBox();
        Image img;
        Label alertLabel = new Label(popMessage);
        Button confirmButton = new Button("Confirm");

        if (status == 1)
            img = new Image("file:images/cross32p.png");
        else if (status == 2)
            img = new Image("file:images/warning.png");
        else if (status == 3)
            img = new Image("file:images/yes.png");
        else if (status == 4)
            img = new Image("file:images/delete.png");
        else if (status == 5)
            img = new Image("file:images/search.png");
        else
            img = new Image("file:images/warning.png");


        ImageView imgV = new ImageView(img);

        alertLabel.setAlignment(Pos.CENTER);
        alertLabel.setFont(new Font(15));
        alertLabel.setPrefHeight(100);

        confirmButton.setPrefWidth(150);
        confirmButton.setAlignment(Pos.CENTER);

        hb.getChildren().addAll(imgV, alertLabel);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(10);

        vbb.getChildren().addAll(hb, confirmButton);
        vbb.setAlignment(Pos.TOP_RIGHT);
        VBox.setMargin(hb, new Insets(10));
        VBox.setMargin(confirmButton, new Insets(5, 10, 10, 5));

        confirmButton.setOnAction(e -> {

            if (status == 1 )
                System.exit(0);
            else
                stg.hide();

        });

        stg.setScene(new Scene(vbb, getWidth(), getHeight()));
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.setResizable(false);
        stg.setTitle(popTitle);
        stg.getIcons().add(new Image("file:images/cross32p.png"));
        stg.showAndWait();
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

}
