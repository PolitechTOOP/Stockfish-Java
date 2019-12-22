package com.github.danildorogoy.template;

//Chess  application

//imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import xyz.niflheim.stockfish.engine.StockfishClient;
import xyz.niflheim.stockfish.engine.enums.Query;
import xyz.niflheim.stockfish.engine.enums.QueryType;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;


//class definition
public class ChessApplication extends Application {

	private static final Log log = LogFactory.getLog(ChessApplication.class);

	public static StockfishClient client = null;

	private StackPane sp_mainlayout; // layout which allows items to be positioned on top of each other
	private CustomControl cc_custom; // control which has a board and detects mouse and keyboard events
	private Controller controller;

	// entry point into our program to launch our JavaFX application
	public static void main(String[] args) { launch(args); }

	@Override
	public void init() throws Exception{
		// initialize the layout, create a CustomControl and it to the layout
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
			clone();

		}
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		// set the title and scene, and show the stage
		/*log.info("Hello World");
		primaryStage.setTitle("Chess game");
		primaryStage.setScene(new Scene(sp_mainlayout, 600, 700));
		primaryStage.setMinWidth(300);
		primaryStage.setMinHeight(300);
		primaryStage.show();*/
		Parent root = null;

		root = FXMLLoader.load(getClass().getResource("/test.fxml"));

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);

		primaryStage.setTitle("Hello!");
		primaryStage.setWidth(primaryStage.getMaxWidth());
		primaryStage.setHeight(primaryStage.getMaxHeight());

		primaryStage.show();
	}

	@Override
	public void stop() {
		log.info("Close");
		if (client != null) {
			client.close();
		}
	}
}