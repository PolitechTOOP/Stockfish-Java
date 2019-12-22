package com.github.danildorogoy.controller;

import com.github.danildorogoy.ChessApplication;
import com.github.danildorogoy.template.board.ChessBoard;
import com.github.danildorogoy.template.board.StatusBar;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import xyz.niflheim.stockfish.engine.StockfishClient;
import xyz.niflheim.stockfish.engine.enums.Query;
import xyz.niflheim.stockfish.engine.enums.QueryType;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class Controller extends Control {

    private ChessBoard chessBoard;
    private StatusBar statusBar;
    private boolean isFirstClick = false;
    private String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private String move = "";
    private static final Log log = LogFactory.getLog(Controller .class);
    private boolean isWhite; // True if player plays White
    public static StockfishClient client = null;

    public Controller(boolean isWhite) {
        this.isWhite = isWhite;
        setSkin(new ControllerSkin(this));

        statusBar = new StatusBar();
        chessBoard = new ChessBoard(statusBar);
        getChildren().addAll(statusBar, chessBoard);

        try {
            client = new StockfishClient.Builder().build();
        } catch (StockfishInitException e) {
            log.error(e);
            if (client != null) {
                client.close();
            }
        }

        // TODO Auto-generated method stub
        //chessBoard.selectPiece(event.getX(), event.getY());
        setOnMouseClicked(this::mouseEntered);

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

    private void mouseEntered(MouseEvent event) {
        Node source = (Node) event.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        double x = event.getX();
        double y = event.getY();

        log.info(event);
        if (isFirstClick) {
            log.info(event.getX()+" "+event.getY());
            chessBoard.selectPiece(event.getX(), event.getY());;

            isFirstClick = false;
            CompletableFuture<String> resultFuture = new CompletableFuture<>();
            log.info(move);
            ChessApplication.client.submit(new Query.Builder(QueryType.Make_Move, fen)
                    .setMove(move).build(), resultFuture::complete);

            try {
                fen = resultFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error(e);
            }
            resultFuture = new CompletableFuture<>();
            log.info(fen);
            ChessApplication.client.submit(new Query.Builder(QueryType.Best_Move, fen)
                    .build(), resultFuture::complete);
            try {
                move = resultFuture.get();
                resultFuture.cancel(false);
            } catch (InterruptedException | ExecutionException e) {
                log.error(e);
            }
            resultFuture = new CompletableFuture<>();
            log.info(move);
            ChessApplication.client.submit(new Query.Builder(QueryType.Make_Move, fen)
                    .setMove(move).build(), resultFuture::complete);
            move = "";

            try {
                fen = resultFuture.get();
                resultFuture.cancel(false);
            } catch (InterruptedException | ExecutionException e) {
                log.error(e);
            }
            log.info(fen);
            //display(fen);
        } else {
            move = String.valueOf(event.getX()+event.getY());
            isFirstClick = true;
        }
        log.info(String.format("Mouse entered cell [%d, %d]%n", colIndex, rowIndex));
    }

    /*private void display(String fen) {
        String[][] board = parserFen(fen);
        for (String[] a : parserFen(fen)) {
            log.info(Arrays.toString(a));
        }
        gridPane.getChildren().forEach(item -> {
            Integer colIndex = GridPane.getColumnIndex(item);
            Integer rowIndex = GridPane.getRowIndex(item);
            colIndex = colIndex == null ? 0 : colIndex;
            rowIndex = rowIndex == null ? 0 : rowIndex;
            int finalColIndex = colIndex;
            int finalRowIndex = rowIndex;


            String url = ChessPiece.getChessPiece(board[finalRowIndex][finalColIndex]).getImg();
            if (!url.isEmpty()) {
                Platform.runLater(() -> gridPane.add(
                        new ImageView(url),
                        finalColIndex, finalRowIndex));
            } else {
                Platform.runLater(() -> {

                    if (item instanceof ImageView) {
                        gridPane.getChildren().remove(item);
                    }
                });
            }
        });
    }*/


    private String[][] parserFen(String fen) {
        fen = fen.substring(0, fen.indexOf(' '));
        String[] pos = fen.split("/");
        String[][] board = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0, t = 0; j < pos[i].length() && t < 8; j++, t++) {
                if (Character.isDigit(pos[i].toCharArray()[j])) {
                    int temp = pos[i].toCharArray()[j] - '0';
                    for (int k = 0; k < temp; k++) {
                        board[i][t + k] = "none";
                    }
                    t += temp - 1;
                    continue;
                }

                board[i][t] = String.valueOf(pos[i].toCharArray()[j]);

            }
        }
        return board;
    }

    public void stop() {
        if (client != null) {
            client.close();
        }
    }
}
