package com.github.danildorogoy.template;

import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;


public class CustomControl extends Control {

	private ChessBoard chessBoard;
	private StatusBar statusBar;
	private int statusBarSize = 100;

	public CustomControl(){
		setSkin(new CustomControlSkin(this));
		
		statusBar = new StatusBar();
		chessBoard = new ChessBoard(statusBar);
		getChildren().addAll(statusBar, chessBoard);
		
		setOnMouseClicked(event -> {
			// TODO Auto-generated method stub
			chessBoard.selectPiece(event.getX(), event.getY() - (statusBarSize / 2));
		});

		// Add a key listener that will reset the game
		setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.SPACE)
				chessBoard.resetGame();
		});
		
		statusBar.getResetButton().setOnAction(event -> {
			// TODO Auto-generated method stub
			chessBoard.resetGame();
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
