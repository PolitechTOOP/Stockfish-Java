package com.github.danildorogoy.controller;

import com.github.danildorogoy.template.board.ChessBoard;
import com.github.danildorogoy.template.board.StatusBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomControl extends Control {

	private ChessBoard chessBoard;
	private StatusBar statusBar;
	private int statusBarSize = 100;
	private static final Log log = LogFactory.getLog(CustomControl .class);
	//similar to previous custom controlls but must handle more
	//complex mouse interactions and key interactions
	public CustomControl(){
		setSkin(new CustomControlSkin(this));
		
		statusBar = new StatusBar();
		chessBoard = new ChessBoard(statusBar);
		getChildren().addAll(statusBar, chessBoard);
		
		setOnMouseClicked(event -> {
			// TODO Auto-generated method stub
			log.info(event.getX() +" "+ event.getY());
			chessBoard.selectPiece(event.getX(), event.getY() - (statusBarSize / 2));
		});

		// Add a key listener that will reset the game
		setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.SPACE)
				chessBoard.resetGame();
		});
		
		statusBar.getResetButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				chessBoard.resetGame();
			}
			
		});
	}

	public void resize(double width, double height){
		super.resize(width, height - statusBarSize);
		chessBoard.setTranslateY(statusBarSize / 2);
		chessBoard.resize(width, height - statusBarSize);
		statusBar.resize(width, statusBarSize);
		statusBar.setTranslateY(-(statusBarSize / 2));
	}
}
