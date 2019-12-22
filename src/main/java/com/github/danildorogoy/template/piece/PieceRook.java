package com.github.danildorogoy.template.piece;

import com.github.danildorogoy.controller.ComputerController;
import com.github.danildorogoy.template.board.ChessBoard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PieceRook extends Piece {

    private Image image;

    public PieceRook(int type, int xPos, int yPos, ComputerController controller) {
        super(type, xPos, yPos, controller);
        name = "Rook";
        // TODO Auto-generated constructor stub
        if (type == 1) {
            image = new Image("file:src/main/resources/wRook.png");
            imageView.setImage(image);
            imageView.fitHeightProperty();
            imageView.fitWidthProperty();
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
        } else {
            image = new Image("file:src/main/resources/bRook.png");
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
        chessBoard.colorSquare(this.xPos, this.yPos, true);
        if (chessBoard.checkState && !this.isASavior)
            return;
        if (gameLogic.slashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type) ||
                gameLogic.backslashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type))
            return;
        if (!gameLogic.horizontalProtection(chessBoard, this.xPos, this.yPos, this.type)) {
            for (int y = this.yPos - 1; y >= 0; y--) {
                if (chessBoard.getBoardPosition(this.xPos, y) == 0) {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, this.xPos, y, this.type))
                            chessBoard.colorSquare(this.xPos, y, false);
                    } else
                        chessBoard.colorSquare(this.xPos, y, false);
                } else if (chessBoard.getBoardPosition(this.xPos, y) == this.type)
                    break;
                else {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, this.xPos, y, this.type))
                            chessBoard.colorSquare(this.xPos, y, false);
                    } else
                        chessBoard.colorSquare(this.xPos, y, false);
                    break;
                }
            }
            for (int y = this.yPos + 1; y < chessBoard.getBoardHeight(); y++) {
                if (chessBoard.getBoardPosition(this.xPos, y) == 0) {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, this.xPos, y, this.type))
                            chessBoard.colorSquare(this.xPos, y, false);
                    } else
                        chessBoard.colorSquare(this.xPos, y, false);
                } else if (chessBoard.getBoardPosition(this.xPos, y) == this.type)
                    break;
                else {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, this.xPos, y, this.type))
                            chessBoard.colorSquare(this.xPos, y, false);
                    } else
                        chessBoard.colorSquare(this.xPos, y, false);
                    break;
                }
            }
        }
        if (!gameLogic.verticalProtection(chessBoard, this.xPos, this.yPos, this.type)) {
            for (int x = this.xPos - 1; x >= 0; x--) {
                if (chessBoard.getBoardPosition(x, this.yPos) == 0) {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, x, this.yPos, this.type))
                            chessBoard.colorSquare(x, this.yPos, false);
                    } else
                        chessBoard.colorSquare(x, this.yPos, false);
                } else if (chessBoard.getBoardPosition(x, this.yPos) == this.type)
                    break;
                else {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, x, this.yPos, this.type))
                            chessBoard.colorSquare(x, this.yPos, false);
                    } else
                        chessBoard.colorSquare(x, this.yPos, false);
                    break;
                }
            }
            for (int x = this.xPos + 1; x < chessBoard.getBoardWidth(); x++) {
                if (chessBoard.getBoardPosition(x, this.yPos) == 0) {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, x, this.yPos, this.type))
                            chessBoard.colorSquare(x, this.yPos, false);
                    } else
                        chessBoard.colorSquare(x, this.yPos, false);
                } else if (chessBoard.getBoardPosition(x, this.yPos) == this.type)
                    break;
                else {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, x, this.yPos, this.type))
                            chessBoard.colorSquare(x, this.yPos, false);
                    } else
                        chessBoard.colorSquare(x, this.yPos, false);
                    break;
                }
            }
        }
    }
}