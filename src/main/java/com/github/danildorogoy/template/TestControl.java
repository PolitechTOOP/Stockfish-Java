package com.github.danildorogoy.template;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class TestControl implements Initializable {

    private StackPane sp_mainlayout;
    private CustomControl cc_custom;

    @FXML
    Button userMode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userMode.setOnAction(event -> {
            sp_mainlayout = new StackPane();
            cc_custom = new CustomControl();
            sp_mainlayout.getChildren().add(cc_custom);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Chess game");
            primaryStage.setScene(new Scene(sp_mainlayout, 600, 700));
            primaryStage.setMinWidth(300);
            primaryStage.setMinHeight(300);
            primaryStage.show();
        });
    }
}
