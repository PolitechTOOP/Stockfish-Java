package com.github.danildorogoy;

//Chess  application

//imports
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


//class definition
public class ChessApplication extends Application {

	private StackPane sp_mainlayout;
	private CustomControl cc_custom;

	private static final Log log = LogFactory.getLog(ChessApplication.class);

	public static StockfishClient client = null;

	// overridden init method
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
	
	// overridden start method
	@Override
	public void start(Stage primaryStage) throws IOException {

		Parent root;

		root = FXMLLoader.load(getClass().getResource("/test.fxml"));

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hello!");
		primaryStage.show();
	}

	// overridden stop method
	@Override
	public void stop() {
		log.info("Close");
	}
	
	// entry point into our program to launch our JavaFX application
	public static void main(String[] args) { launch(args); }
}