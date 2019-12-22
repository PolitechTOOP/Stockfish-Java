package com.github.danildorogoy;

import com.github.danildorogoy.controller.CustomControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import xyz.niflheim.stockfish.engine.StockfishClient;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;
import java.io.IOException;

public class ChessApplication extends Application {

	private StackPane sp_mainlayout;
	private CustomControl cc_custom;

	private static final Log log = LogFactory.getLog(ChessApplication.class);

	public static StockfishClient client = null;

	@Override
	public void init() throws Exception {

		sp_mainlayout = new StackPane();
		cc_custom = new CustomControl();
		sp_mainlayout.getChildren().add(cc_custom);
		super.init();
		try {
			client = new StockfishClient.Builder().build();
		} catch (StockfishInitException e) {
			log.error(e);
			if (client != null) {
				client.close();
			}
		}
	}

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