package com.github.danildorogoy.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.IOException;

public class ChessApplication extends Application {

	private static final Log log = LogFactory.getLog(ChessApplication.class);

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root;
		root = FXMLLoader.load(getClass().getResource("/test.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hello!");
		primaryStage.show();
	}

	@Override
	public void stop() {
		log.info("Close");
	}

	public static void main(String[] args) { launch(args); }
}