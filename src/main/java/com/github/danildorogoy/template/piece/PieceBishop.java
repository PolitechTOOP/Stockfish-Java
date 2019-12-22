package com.github.danildorogoy.template.piece;

import com.github.danildorogoy.controller.ComputerController;
import com.github.danildorogoy.template.CustomEvent;
import com.github.danildorogoy.template.board.ChessBoard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PieceBishop extends Piece {

    private Image image;

    public PieceBishop(int type, int xPos, int yPos, ComputerController controller) {
        super(type, xPos, yPos, controller);
        name = "Bishop";
        // TODO Auto-generated constructor stub
        if (type == 1) {
            image = new Image("file:src/main/resources/wBishop.png");
            imageView.setImage(image);
            imageView.fitHeightProperty();
            imageView.fitWidthProperty();
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
        } else {
            image = new Image("file:src/main/resources/bBishop.png");
            imageView.setImage(image);
            imageView.fitHeightProperty();
            imageView.fitWidthProperty();
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
        }
        setOnMouseClicked(mouseEvent -> {
            controller.mouseClick(new CustomEvent("", ChessPieceEnum.BLACK_BISHOP));
        });
    }

    @Override
    public ImageView getImage() {
        return (imageView);
    }

    @Override
    public void SelectPiece(ChessBoard chessBoard) {
        int y = this.yPos + 1;
        chessBoard.colorSquare(this.xPos, this.yPos, true);
        if (chessBoard.checkState && !this.isASavior)
            return;
        if (gameLogic.horizontalProtection(chessBoard, this.xPos, this.yPos, this.type) ||
                gameLogic.verticalProtection(chessBoard, this.xPos, this.yPos, this.type))
            return;
        if (!gameLogic.slashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type)) {
            for (int x = this.xPos + 1; x < chessBoard.getBoardWidth() &&
                    y < chessBoard.getBoardHeight(); x++, y++) {
                if (chessBoard.getBoardPosition(x, y) == 0) {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
                            chessBoard.colorSquare(x, y, false);
                    } else
                        chessBoard.colorSquare(x, y, false);
                } else if (chessBoard.getBoardPosition(x, y) == this.type)
                    break;
                else {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
                            chessBoard.colorSquare(x, y, false);
                    } else
                        chessBoard.colorSquare(x, y, false);
                    break;
                }
            }
            y = this.yPos - 1;
            for (int x = this.xPos - 1; x >= 0 && y >= 0; x--, y--) {
                if (chessBoard.getBoardPosition(x, y) == 0) {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
                            chessBoard.colorSquare(x, y, false);
                    } else
                        chessBoard.colorSquare(x, y, false);
                } else if (chessBoard.getBoardPosition(x, y) == this.type)
                    break;
                else {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
                            chessBoard.colorSquare(x, y, false);
                    } else
                        chessBoard.colorSquare(x, y, false);
                    break;
                }
            }
        }
        if (!gameLogic.backslashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type)) {
            y = this.yPos + 1;
            for (int x = this.xPos - 1; x >= 0 && y < chessBoard.getBoardHeight(); x--, y++) {
                if (chessBoard.getBoardPosition(x, y) == 0) {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
                            chessBoard.colorSquare(x, y, false);
                    } else
                        chessBoard.colorSquare(x, y, false);
                } else if (chessBoard.getBoardPosition(x, y) == this.type)
                    break;
                else {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
                            chessBoard.colorSquare(x, y, false);
                    } else
                        chessBoard.colorSquare(x, y, false);
                    break;
                }
            }
            y = this.yPos - 1;
            for (int x = this.xPos + 1; x < chessBoard.getBoardWidth() && y >= 0; x++, y--) {
                if (chessBoard.getBoardPosition(x, y) == 0) {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
                            chessBoard.colorSquare(x, y, false);
                    } else
                        chessBoard.colorSquare(x, y, false);
                } else if (chessBoard.getBoardPosition(x, y) == this.type)
                    break;
                else {
                    if (chessBoard.checkState) {
                        if (gameLogic.isThisProtecting(chessBoard, x, y, this.type))
                            chessBoard.colorSquare(x, y, false);
                    } else
                        chessBoard.colorSquare(x, y, false);
                    break;
                }
            }
        }
    }
}
