package com.github.danildorogoy.template.piece;

import com.github.danildorogoy.controller.ComputerController;
import com.github.danildorogoy.template.board.ChessBoard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PieceKing extends Piece {


    private Image image;

    public PieceKing(int type, int xPos, int yPos, ComputerController controller) {
        super(type, xPos, yPos, controller);
        name = "King";
        // TODO Auto-generated constructor stub

        if (type == 1) {
            image = new Image("file:src/main/resources/wKing.png");
            imageView.setImage(image);
            imageView.fitHeightProperty();
            imageView.fitWidthProperty();
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
        } else {
            image = new Image("file:src/main/resources/bKing.png");
            imageView.setImage(image);
            imageView.fitHeightProperty();
            imageView.fitWidthProperty();
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
        }
    }

    @Override
    public ImageView getImage() {
        return (imageView);
    }

    @Override
    public void SelectPiece(ChessBoard chessBoard) {
        int x = this.xPos;
        int y = this.yPos;
        chessBoard.colorSquare(this.xPos, this.yPos, true);
        for (y = this.yPos - 1; y <= this.yPos + 1; y++) {
            for (x = this.xPos - 1; x <= this.xPos + 1; x++) {
                if (y >= 0 && y < chessBoard.getBoardHeight() && x >= 0 && x < chessBoard.getBoardWidth() &&
                        chessBoard.getBoardPosition(x, y) != this.type) {
                    if (!chessBoard.checkState)
                        this.canCastle(chessBoard);
                    if (!gameLogic.isCheck(chessBoard, x, y, this.type, true))
                        chessBoard.colorSquare(x, y, false);
                }
            }
        }
    }


    public int canCastle(ChessBoard chessBoard) {
        int canCastle = 0;
        if (type == 2 && this.isFirstTime && chessBoard.getBoardPosition(5, 0) == 0 &&
                chessBoard.getBoardPosition(6, 0) == 0 && chessBoard.getPiece(7, 0) != null &&
                chessBoard.getPiece(7, 0).isFirstTime) {
            canCastle = 1;
            chessBoard.colorSquare(7, 0, false);
        }
        //longCastle (1 2 3 empty)
        if (type == 2 && this.isFirstTime && chessBoard.getBoardPosition(1, 0) == 0 &&
                chessBoard.getBoardPosition(2, 0) == 0 && chessBoard.getBoardPosition(3, 0) == 0 &&
                chessBoard.getPiece(0, 0) != null && chessBoard.getPiece(0, 0).isFirstTime) {
            canCastle = 2;
            chessBoard.colorSquare(0, 0, false);
        }
        // White
        //shortCastle (5 and 6 empty)
        if (type == 1 && this.isFirstTime && chessBoard.getBoardPosition(5, 7) == 0 &&
                chessBoard.getBoardPosition(6, 7) == 0 && chessBoard.getPiece(7, 7) != null &&
                chessBoard.getPiece(7, 7).isFirstTime) {
            canCastle = 3;
            chessBoard.colorSquare(7, 7, false);
        }
        //longCastle (1 2 3 empty)
        if (type == 1 && this.isFirstTime && chessBoard.getBoardPosition(1, 7) == 0 &&
                chessBoard.getBoardPosition(2, 7) == 0 && chessBoard.getBoardPosition(3, 7) == 0 &&
                chessBoard.getPiece(0, 7) != null && chessBoard.getPiece(0, 7).isFirstTime) {
            canCastle = 4;
            chessBoard.colorSquare(0, 7, false);
        }
        return canCastle;
    }
}
