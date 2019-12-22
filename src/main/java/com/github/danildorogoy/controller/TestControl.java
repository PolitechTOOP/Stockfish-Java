package com.github.danildorogoy.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

;import java.net.URL;
import java.util.ResourceBundle;

@Component
public class TestControl implements Initializable {


    private StackPane sp_mainlayout;
    private final CustomControl cc_custom;
    private final ComputerController cc_control;

    @FXML
    Button userMode;
    @FXML
    Button whiteMode;
    @FXML
    Button blackMode;

    public TestControl(CustomControl cc_custom, ComputerController cc_control) {
        this.cc_custom = cc_custom;
        this.cc_control = cc_control;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userMode.setOnAction(event -> {
            sp_mainlayout = new StackPane();
            sp_mainlayout.getChildren().add(cc_custom);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Chess game");
            primaryStage.setScene(new Scene(sp_mainlayout, 600, 700));
            primaryStage.setMinWidth(300);
            primaryStage.setMinHeight(300);
            primaryStage.show();
        });
        whiteMode.setOnAction(event -> {
            sp_mainlayout = new StackPane();
            sp_mainlayout.getChildren().add(cc_control);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Chess game");
            primaryStage.setScene(new Scene(sp_mainlayout, 600, 700));
            primaryStage.setMinWidth(300);
            primaryStage.setMinHeight(300);
            primaryStage.show();
        });

    }
}
