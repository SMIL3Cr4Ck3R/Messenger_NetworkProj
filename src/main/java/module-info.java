module bzu.network.prj.networkproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;
    requires MaterialFX;

    opens bzu.network.prj.networkproject to javafx.fxml;
    exports bzu.network.prj.networkproject;
    exports bzu.network.prj.networkproject.backend.Controllers;
    opens bzu.network.prj.networkproject.backend.Controllers to javafx.fxml;


}