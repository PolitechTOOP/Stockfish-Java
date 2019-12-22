package com.github.danildorogoy.controller;

import com.github.danildorogoy.controller.Controller;
import com.github.danildorogoy.controller.CustomControl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

;import java.net.URL;
import java.util.ResourceBundle;

public class TestControl implements Initializable {

    private StackPane sp_mainlayout;
    private CustomControl cc_custom;
    private Controller cc_control;

    @FXML
    Button userMode;
    @FXML
    Button whiteMode;
    @FXML
    Button blackMode;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sp_mainlayout = new StackPane();
                cc_custom = new CustomControl();
                sp_mainlayout.getChildren().add(cc_custom);
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Chess game");
                primaryStage.setScene(new Scene(sp_mainlayout, 600, 700));
                primaryStage.setMinWidth(300);
                primaryStage.setMinHeight(300);
                primaryStage.show();
            }
        });
        whiteMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sp_mainlayout = new StackPane();
                cc_control = new Controller(true);
                sp_mainlayout.getChildren().add(cc_control);
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Chess game");
                primaryStage.setScene(new Scene(sp_mainlayout, 600, 700));
                primaryStage.setMinWidth(300);
                primaryStage.setMinHeight(300);
                primaryStage.show();
            }
        });
        blackMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sp_mainlayout = new StackPane();
                cc_control = new Controller(false);
                sp_mainlayout.getChildren().add(cc_control);
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Chess game");
                primaryStage.setScene(new Scene(sp_mainlayout, 600, 700));
                primaryStage.setMinWidth(300);
                primaryStage.setMinHeight(300);
                primaryStage.show();
            }
        });
    }
}
