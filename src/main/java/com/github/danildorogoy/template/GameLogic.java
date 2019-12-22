package com.github.danildorogoy.template;

import com.github.danildorogoy.template.board.ChessBoard;
import com.github.danildorogoy.template.piece.Piece;

import java.util.Iterator;

public class GameLogic {

    //method to detect stalemate
    private boolean isOneKingStalemate(ChessBoard chessBoard, Piece king, int type) {
        int nbPiece = 0;
        boolean stalemate = true;

        // A Player has only 1 king left, which is not in check position and can't move
        for (int y = 0; y < chessBoard.getBoardHeight(); y++) {
            for (int x = 0; x < chessBoard.getBoardWidth(); x++) {
                if (chessBoard.getBoardPosition(x, y) == type)
                    nbPiece++;
            }
        }
        if (nbPiece == 1) {
            for (int y = king.getY() - 1; y <= king.getY() + 1; y++) {
                for (int x = king.getX() - 1; x <= king.getX() + 1; x++) {
                    if (y >= 0 && y < chessBoard.getBoardHeight() && x >= 0 && x < chessBoard.getBoardWidth() &&
                            chessBoard.getBoardPosition(x, y) != type) {
                        if (!isCheck(chessBoard, x, y, type, true)) {
                            stalemate = false;
                            break;
                        }
                    }
                }
                if (!stalemate)
                    break;
            }
        } else
            stalemate = false;
        return (stalemate);
    }

    public boolean isLimitPieceStalemate(ChessBoard chessBoard) {
        // Both Player have less then:
        // A king and a Queen
        // A king and a Rook
        // A king and two knight
        // A king, a bishop and a knight
        // A king and two bishop (one light square, one dark square)
        // A king and at least a pawn
        if (chessBoard.playerOneQueen != 0 || chessBoard.playerTwoQueen != 0)
            return false;
        else if (chessBoard.playerOneRook != 0 || chessBoard.playerTwoRook != 0)
            return false;
        else if (chessBoard.playerOneKnight > 1 || chessBoard.playerTwoKnight > 1)
            return false;
        else if (((chessBoard.playerOneBishopDarkSquare != 0 || chessBoard.playerOneBishopLightSquare != 0) &&
                chessBoard.playerOneKnight != 0) ||
                ((chessBoard.playerTwoBishopDarkSquare != 0 || chessBoard.playerTwoBishopLightSquare != 0) &&
                        chessBoard.playerTwoKnight != 0))
            return false;
        else if ((chessBoard.playerOneBishopDarkSquare != 0 && chessBoard.playerOneBishopLightSquare != 0) ||
                (chessBoard.playerTwoBishopDarkSquare != 0 && chessBoard.playerTwoBishopLightSquare != 0))
            return false;
        else return chessBoard.playerOnePawn <= 1 && chessBoard.playerTwoPawn <= 1;
    }

    public boolean isStalemate(ChessBoard chessBoard, Piece king, int type) {
        if (isOneKingStalemate(chessBoard, king, type) || isLimitPieceStalemate(chessBoard)) {
            chessBoard.stalemate = true;
            return true;
        }
        return false;
    }

    // Method to check if a piece is protecting the king from a check
    public boolean verticalProtection(ChessBoard chessBoard, int xPos, int yPos, int type) {
        int y = 0;
        int enemyType;
        if (type == 1)
            enemyType = 2;
        else
            enemyType = 1;

        // King on the Vertical Up
        for (y = yPos - 1; y >= 0; y--) {
            if (chessBoard.getBoardPosition(xPos, y) == type && chessBoard.getPiece(xPos, y).getName().equals("King")) {
                for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++) {
                    if (chessBoard.getBoardPosition(xPos, y) == type)
                        break;
                    else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
                        if (chessBoard.getPiece(xPos, y).getName().equals("Queen") ||
                                chessBoard.getPiece(xPos, y).getName().equals("Rook"))
                            return true;
                        else
                            break;
                    }
                }
                break;
            } else if (chessBoard.getBoardPosition(xPos, y) != 0)
                break;
        }
        // King on the Vertical Down
        for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++) {
            if (chessBoard.getBoardPosition(xPos, y) == type && chessBoard.getPiece(xPos, y).getName().equals("King")) {
                for (y = yPos - 1; y >= 0; y--) {
                    if (chessBoard.getBoardPosition(xPos, y) == type)
                        break;
                    else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
                        if (chessBoard.getPiece(xPos, y).getName().equals("Queen") ||
                                chessBoard.getPiece(xPos, y).getName().equals("Rook"))
                            return true;
                        else
                            break;
                    }
                }
                break;
            } else if (chessBoard.getBoardPosition(xPos, y) != 0)
                break;
        }
        return false;
    }

    public boolean horizontalProtection(ChessBoard chessBoard, int xPos, int yPos, int type) {
        int x = 0;
        int enemyType;
        if (type == 1)
            enemyType = 2;
        else
            enemyType = 1;

        // King on the Horizontal Left
        for (x = xPos - 1; x >= 0; x--) {
            if (chessBoard.getBoardPosition(x, yPos) == type && chessBoard.getPiece(x, yPos).getName().equals("King")) {
                for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++) {
                    if (chessBoard.getBoardPosition(x, yPos) == type)
                        break;
                    else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
                        if (chessBoard.getPiece(x, yPos).getName().equals("Queen") ||
                                chessBoard.getPiece(x, yPos).getName().equals("Rook"))
                            return true;
                        else
                            break;
                    }
                }
                break;
            } else if (chessBoard.getBoardPosition(x, yPos) != 0)
                break;
        }
        // King on the Horizontal Right
        for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++) {
            if (chessBoard.getBoardPosition(x, yPos) == type && chessBoard.getPiece(x, yPos).getName().equals("King")) {
                for (x = xPos - 1; x >= 0; x--) {
                    if (chessBoard.getBoardPosition(x, yPos) == type)
                        break;
                    else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
                        if (chessBoard.getPiece(x, yPos).getName().equals("Queen") ||
                                chessBoard.getPiece(x, yPos).getName().equals("Rook"))
                            return true;
                        else
                            break;
                    }
                }
                break;
            } else if (chessBoard.getBoardPosition(x, yPos) != 0)
                break;
        }
        return false;
    }

    public boolean slashDiagonalProtection(ChessBoard chessBoard, int xPos, int yPos, int type) {
        int enemyType;
        if (type == 1)
            enemyType = 2;
        else
            enemyType = 1;

        // King on the Diagonal / Up
        int y = yPos - 1;
        for (int x = xPos + 1; x < chessBoard.getBoardWidth() && y >= 0; x++, y--) {
            if (chessBoard.getBoardPosition(x, y) == type && chessBoard.getPiece(x, y).getName().equals("King")) {
                y = yPos + 1;
                for (x = xPos - 1; x >= 0 && y < chessBoard.getBoardHeight(); x--, y++) {
                    if (chessBoard.getBoardPosition(x, y) == type)
                        break;
                    else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                        if (chessBoard.getPiece(x, y).getName().equals("Queen") ||
                                chessBoard.getPiece(x, y).getName().equals("Bishop"))
                            return true;
                        else
                            break;
                    }
                }
                break;
            } else if (chessBoard.getBoardPosition(x, y) != 0)
                break;
        }
        // King on the Diagonal / Down
        y = yPos + 1;
        for (int x = xPos - 1; x >= 0 && y < chessBoard.getBoardHeight(); x--, y++) {
            if (chessBoard.getBoardPosition(x, y) == type && chessBoard.getPiece(x, y).getName().equals("King")) {
                y = yPos - 1;
                for (x = xPos + 1; x < chessBoard.getBoardWidth() && y >= 0; x++, y--) {
                    if (chessBoard.getBoardPosition(x, y) == type)
                        break;
                    else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                        if (chessBoard.getPiece(x, y).getName().equals("Queen") ||
                                chessBoard.getPiece(x, y).getName().equals("Bishop"))
                            return true;
                        else
                            break;
                    }
                }
                break;
            } else if (chessBoard.getBoardPosition(x, y) != 0)
                break;
        }
        return false;
    }

    public boolean backslashDiagonalProtection(ChessBoard chessBoard, int xPos, int yPos, int type) {
        int enemyType;
        if (type == 1)
            enemyType = 2;
        else
            enemyType = 1;

        // King on the Diagonal \ Up
        int y = yPos - 1;
        for (int x = xPos - 1; x >= 0 && y >= 0; x--, y--) {
            if (chessBoard.getBoardPosition(x, y) == type && chessBoard.getPiece(x, y).getName().equals("King")) {
                y = yPos + 1;
                for (x = xPos + 1; x < chessBoard.getBoardWidth() && y < chessBoard.getBoardHeight(); x++, y++) {
                    if (chessBoard.getBoardPosition(x, y) == type)
                        break;
                    else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                        if (chessBoard.getPiece(x, y).getName().equals("Queen") ||
                                chessBoard.getPiece(x, y).getName().equals("Bishop"))
                            return true;
                        else
                            break;
                    }
                }
                break;
            } else if (chessBoard.getBoardPosition(x, y) != 0)
                break;
        }
        // King on the Diagonal \ Down
        y = yPos + 1;
        for (int x = xPos + 1; x < chessBoard.getBoardWidth() && y < chessBoard.getBoardHeight(); x++, y++) {
            if (chessBoard.getBoardPosition(x, y) == type && chessBoard.getPiece(x, y).getName().equals("King")) {
                y = yPos - 1;
                for (x = xPos - 1; x >= 0 && y >= 0; x--, y--) {
                    if (chessBoard.getBoardPosition(x, y) == type)
                        break;
                    else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                        if (chessBoard.getPiece(x, y).getName().equals("Queen") ||
                                chessBoard.getPiece(x, y).getName().equals("Bishop"))
                            return true;
                        else
                            break;
                    }
                }
                break;
            } else if (chessBoard.getBoardPosition(x, y) != 0)
                break;
        }
        return false;
    }

    // Method to check check
    public boolean isCheck(ChessBoard chessBoard, int xPos, int yPos, int type, boolean kingCanCapture) {
        int y;
        int x;
        int enemyType;
        if (type == 1)
            enemyType = 2;
        else
            enemyType = 1;

        // Horizontal Left
        for (x = xPos - 1; x >= 0; x--) {
            if (chessBoard.getBoardPosition(x, yPos) == type && !chessBoard.getPiece(x, yPos).getName().equals("King"))
                break;
            else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
                if (x == xPos - 1 && chessBoard.getPiece(x, yPos) != null &&
                        kingCanCapture && chessBoard.getPiece(x, yPos).getName().equals("King"))
                    return true;
                else if (chessBoard.getPiece(x, yPos) != null && (chessBoard.getPiece(x, yPos)
                        .getName().equals("Queen") || chessBoard.getPiece(x, yPos).getName().equals("Rook")))
                    return true;
                else
                    break;
            }
        }
        // Horizontal Right
        for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++) {
            if (chessBoard.getBoardPosition(x, yPos) == type && !chessBoard.getPiece(x, yPos).getName().equals("King"))
                break;
            else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
                if (x == xPos + 1 && chessBoard.getPiece(x, yPos) != null && kingCanCapture && chessBoard.
                        getPiece(x, yPos).getName().equals("King"))
                    return true;
                else if (chessBoard.getPiece(x, yPos) != null && (chessBoard.getPiece(x, yPos).
                        getName().equals("Queen") || chessBoard.getPiece(x, yPos).getName().equals("Rook")))
                    return true;
                else
                    break;
            }
        }
        // Vertical Up
        for (y = yPos - 1; y >= 0; y--) {
            if (chessBoard.getBoardPosition(xPos, y) == type && !chessBoard.getPiece(xPos, y).getName().equals("King"))
                break;
            else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
                if (y == yPos - 1 && chessBoard.getPiece(xPos, y) != null && kingCanCapture &&
                        chessBoard.getPiece(xPos, y).getName().equals("King"))
                    return true;
                else if (chessBoard.getPiece(xPos, y) != null && (chessBoard.getPiece(xPos, y).
                        getName().equals("Queen") || chessBoard.getPiece(xPos, y).getName().equals("Rook")))
                    return true;
                else
                    break;
            }
        }
        // Vertical Down
        for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++) {
            if (chessBoard.getBoardPosition(xPos, y) == type && !chessBoard.getPiece(xPos, y).getName().equals("King"))
                break;
            else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
                if (y == yPos + 1 && chessBoard.getPiece(xPos, y) != null && kingCanCapture &&
                        chessBoard.getPiece(xPos, y).getName().equals("King"))
                    return true;
                else if (chessBoard.getPiece(xPos, y) != null && (chessBoard.getPiece(xPos, y).getName()
                        .equals("Queen") || chessBoard.getPiece(xPos, y).getName().equals("Rook")))
                    return true;
                else
                    break;
            }
        }
        // Diagonal 1 \ Up
        for (y = yPos - 1, x = xPos - 1; y >= 0 && x >= 0; y--, x--) {
            if (chessBoard.getBoardPosition(x, y) == type && !chessBoard.getPiece(x, y).getName().equals("King"))
                break;
            else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                if (y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        ((kingCanCapture && chessBoard.getPiece(x, y).getName().equals("King")) || (type == 1 &&
                                chessBoard.getPiece(x, y).getName().equals("Pawn"))))
                    return true;
                else if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (chessBoard.getPiece(x, y).getName().equals("Queen") ||
                                chessBoard.getPiece(x, y).getName().equals("Bishop")))
                    return true;
                else
                    break;
            }
        }
        // Diagonal 1 \ Down
        for (y = yPos + 1, x = xPos + 1; y < chessBoard.getBoardHeight() && x < chessBoard.getBoardWidth(); y++, x++) {
            if (chessBoard.getBoardPosition(x, y) == type && !chessBoard.getPiece(x, y).getName().equals("King"))
                break;
            else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                if (y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        ((kingCanCapture && chessBoard.getPiece(x, y).getName().equals("King")) ||
                                (type == 2 && chessBoard.getPiece(x, y).getName().equals("Pawn"))))
                    return true;
                else if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (chessBoard.getPiece(x, y).getName().equals("Queen") ||
                                chessBoard.getPiece(x, y).getName().equals("Bishop")))
                    return true;
                else
                    break;
            }
        }
        // Diagonal 2 / Up
        for (y = yPos - 1, x = xPos + 1; y >= 0 && x < chessBoard.getBoardWidth(); y--, x++) {
            if (chessBoard.getBoardPosition(x, y) == type && !chessBoard.getPiece(x, y).getName().equals("King"))
                break;
            else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                if (y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        ((kingCanCapture && chessBoard.getPiece(x, y).getName().equals("King")) ||
                                (type == 1 && chessBoard.getPiece(x, y).getName().equals("Pawn"))))
                    return true;
                else if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (chessBoard.getPiece(x, y).getName().equals("Queen") || chessBoard.getPiece(x, y).getName()
                                .equals("Bishop")))
                    return true;
                else
                    break;
            }
        }
        // Diagonal 2 / Down
        for (y = yPos + 1, x = xPos - 1; y < chessBoard.getBoardHeight() && x >= 0; y++, x--) {
            if (chessBoard.getBoardPosition(x, y) == type && !chessBoard.getPiece(x, y).getName().equals("King"))
                break;
            else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                if (y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        ((kingCanCapture && chessBoard.getPiece(x, y).getName().equals("King")) ||
                                (type == 2 && chessBoard.getPiece(x, y).getName().equals("Pawn"))))
                    return true;
                else if (chessBoard.getBoardPosition(x, y) != 0 &&
                        (chessBoard.getPiece(x, y).getName().equals("Queen") ||
                                chessBoard.getPiece(x, y).getName().equals("Bishop")))
                    return true;
                else
                    break;
            }
        }
        // Knight
        for (y = -2; y <= 2; y++) {
            if (y != 0) {
                x = y % 2 == 0 ? 1 : 2;
                if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight() && xPos - x >= 0 && xPos - x <
                        chessBoard.getBoardWidth() && chessBoard.getBoardPosition(xPos - x, yPos + y) != type &&
                        chessBoard.getBoardPosition(xPos - x, yPos + y) != 0) {
                    if (chessBoard.getPiece(xPos - x, yPos + y) != null &&
                            chessBoard.getPiece(xPos - x, yPos + y).getName().equals("Knight"))
                        return true;
                }
                if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight() && xPos + x >= 0 && xPos + x <
                        chessBoard.getBoardWidth() && chessBoard.getBoardPosition(xPos + x, yPos + y) != type &&
                        chessBoard.getBoardPosition(xPos + x, yPos + y) != 0) {
                    if (chessBoard.getPiece(xPos + x, yPos + y) != null &&
                            chessBoard.getPiece(xPos + x, yPos + y).getName().equals("Knight"))
                        return true;
                }
            }
        }
        return false;
    }

    // Method to find all the piece that create a check
    public void findAllCheckPieces(ChessBoard chessBoard, int xPos, int yPos, int type) {
        int y;
        int x;
        int enemyType;
        if (type == 1)
            enemyType = 2;
        else
            enemyType = 1;

        // Horizontal Left
        for (x = xPos - 1; x >= 0; x--) {
            if (chessBoard.getBoardPosition(x, yPos) == type)
                break;
            else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
                if (chessBoard.getPiece(x, yPos) != null && (chessBoard.getPiece(x, yPos).getName().equals("Queen") ||
                        chessBoard.getPiece(x, yPos).getName().equals("Rook")))
                    chessBoard.checkPieces.add(chessBoard.getPiece(x, yPos));
                else
                    break;
            }
        }
        // Horizontal Right
        for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++) {
            if (chessBoard.getBoardPosition(x, yPos) == type)
                break;
            else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
                if (chessBoard.getPiece(x, yPos) != null && (chessBoard.getPiece(x, yPos).getName().equals("Queen") ||
                        chessBoard.getPiece(x, yPos).getName().equals("Rook")))
                    chessBoard.checkPieces.add(chessBoard.getPiece(x, yPos));
                else
                    break;
            }
        }
        // Vertical Up
        for (y = yPos - 1; y >= 0; y--) {
            if (chessBoard.getBoardPosition(xPos, y) == type)
                break;
            else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
                if (chessBoard.getPiece(xPos, y) != null && (chessBoard.getPiece(xPos, y).getName().equals("Queen") ||
                        chessBoard.getPiece(xPos, y).getName().equals("Rook")))
                    chessBoard.checkPieces.add(chessBoard.getPiece(xPos, y));
                else
                    break;
            }
        }
        // Vertical Down
        for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++) {
            if (chessBoard.getBoardPosition(xPos, y) == type)
                break;
            else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
                if (chessBoard.getPiece(xPos, y) != null && (chessBoard.getPiece(xPos, y).getName().equals("Queen") ||
                        chessBoard.getPiece(xPos, y).getName().equals("Rook")))
                    chessBoard.checkPieces.add(chessBoard.getPiece(xPos, y));
                else
                    break;
            }
        }
        // Diagonal 1 \ Up
        for (y = yPos - 1, x = xPos - 1; y >= 0 && x >= 0; y--, x--) {
            if (chessBoard.getBoardPosition(x, y) == type)
                break;
            else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                if (y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (type == 1 && chessBoard.getPiece(x, y).getName().equals("Pawn")))
                    chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
                else if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (chessBoard.getPiece(x, y).getName().equals("Queen") ||
                                chessBoard.getPiece(x, y).getName().equals("Bishop")))
                    chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
                else
                    break;
            }
        }
        // Diagonal 1 \ Down
        for (y = yPos + 1, x = xPos + 1; y < chessBoard.getBoardHeight() && x < chessBoard.getBoardWidth(); y++, x++) {
            if (chessBoard.getBoardPosition(x, y) == type)
                break;
            else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                if (y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (type == 2 && chessBoard.getPiece(x, y).getName().equals("Pawn")))
                    chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
                else if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (chessBoard.getPiece(x, y).getName().equals("Queen") ||
                                chessBoard.getPiece(x, y).getName().equals("Bishop")))
                    chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
                else
                    break;
            }
        }
        // Diagonal 2 / Up
        for (y = yPos - 1, x = xPos + 1; y >= 0 && x < chessBoard.getBoardWidth(); y--, x++) {
            if (chessBoard.getBoardPosition(x, y) == type)
                break;
            else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                if (y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (type == 1 && chessBoard.getPiece(x, y).getName().equals("Pawn")))
                    chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
                else if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (chessBoard.getPiece(x, y).getName().equals("Queen") || chessBoard.getPiece(x, y).getName()
                                .equals("Bishop")))
                    chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
                else
                    break;
            }
        }
        // Diagonal 2 / Down
        for (y = yPos + 1, x = xPos - 1; y < chessBoard.getBoardHeight() && x >= 0; y++, x--) {
            if (chessBoard.getBoardPosition(x, y) == type)
                break;
            else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                if (y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (type == 2 && chessBoard.getPiece(x, y).getName().equals("Pawn")))
                    chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
                if (chessBoard.getBoardPosition(x, y) != 0 && (chessBoard.getPiece(x, y).getName().equals("Queen") ||
                        chessBoard.getPiece(x, y).getName().equals("Bishop")))
                    chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
                else
                    break;
            }
        }
        // Knight
        for (y = -2; y <= 2; y++) {
            if (y != 0) {
                x = y % 2 == 0 ? 1 : 2;
                if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight() && xPos - x >= 0 && xPos - x <
                        chessBoard.getBoardWidth() && chessBoard.getBoardPosition(xPos - x, yPos + y) != type &&
                        chessBoard.getBoardPosition(xPos - x, yPos + y) != 0) {
                    if (chessBoard.getPiece(xPos - x, yPos + y) != null &&
                            chessBoard.getPiece(xPos - x, yPos + y).getName().equals("Knight"))
                        chessBoard.checkPieces.add(chessBoard.getPiece(xPos - x, yPos + y));
                }
                if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight() && xPos + x >= 0 && xPos + x <
                        chessBoard.getBoardWidth() && chessBoard.getBoardPosition(xPos + x, yPos + y) != type &&
                        chessBoard.getBoardPosition(xPos + x, yPos + y) != 0) {
                    if (chessBoard.getPiece(xPos + x, yPos + y) != null &&
                            chessBoard.getPiece(xPos + x, yPos + y).getName().equals("Knight"))
                        chessBoard.checkPieces.add(chessBoard.getPiece(xPos + x, yPos + y));
                }
            }
        }
    }

    // Method to find all the piece that can save the king from a checkmate
    public void findAllSaviorPieces(ChessBoard chessBoard, int xPos, int yPos, int type, boolean protect) {
        int y;
        int x;
        int enemyType;
        if (type == 1)
            enemyType = 2;
        else
            enemyType = 1;

        // Horizontal Left
        for (x = xPos - 1; x >= 0; x--) {
            if (chessBoard.getBoardPosition(x, yPos) == type)
                break;
            else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
                if (chessBoard.getPiece(x, yPos) != null && (chessBoard.getPiece(x, yPos).getName().equals("Queen") ||
                        chessBoard.getPiece(x, yPos).getName().equals("Rook")))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(x, yPos));
                else
                    break;
            }
        }
        // Horizontal Right
        for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++) {
            if (chessBoard.getBoardPosition(x, yPos) == type)
                break;
            else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
                if (chessBoard.getPiece(x, yPos) != null && (chessBoard.getPiece(x, yPos).getName().equals("Queen") ||
                        chessBoard.getPiece(x, yPos).getName().equals("Rook")))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(x, yPos));
                else
                    break;
            }
        }
        // Vertical Up
        for (y = yPos - 1; y >= 0; y--) {
            if (chessBoard.getBoardPosition(xPos, y) == type)
                break;
            else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
                if (enemyType == 2 && protect && y == yPos - 1 && chessBoard.getPiece(xPos, y) != null &&
                        chessBoard.getPiece(xPos, y).getName().equals("Pawn"))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
                if (enemyType == 2 && protect && y == yPos - 2 && chessBoard.getPiece(xPos, y) != null &&
                        chessBoard.getPiece(xPos, y).getName().equals("Pawn") && chessBoard.getPiece(xPos, y).isFirstTime())
                    chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
                if (chessBoard.getPiece(xPos, y) != null && (chessBoard.getPiece(xPos, y).getName().equals("Queen") ||
                        chessBoard.getPiece(xPos, y).getName().equals("Rook")))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
                else
                    break;
            }
        }
        // Vertical Down
        for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++) {
            if (chessBoard.getBoardPosition(xPos, y) == type)
                break;
            else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
                if (enemyType == 1 && protect && y == yPos + 1 && chessBoard.getPiece(xPos, y) != null &&
                        chessBoard.getPiece(xPos, y).getName().equals("Pawn"))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
                if (enemyType == 1 && protect && y == yPos + 2 && chessBoard.getPiece(xPos, y) != null &&
                        chessBoard.getPiece(xPos, y).getName()
                                .equals("Pawn") && chessBoard.getPiece(xPos, y).isFirstTime())
                    chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
                if (chessBoard.getPiece(xPos, y) != null && (chessBoard.getPiece(xPos, y).getName().equals("Queen") ||
                        chessBoard.getPiece(xPos, y).getName().equals("Rook")))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
                else
                    break;
            }
        }
        // Diagonal 1 \ Up
        for (y = yPos - 1, x = xPos - 1; y >= 0 && x >= 0; y--, x--) {
            if (chessBoard.getBoardPosition(x, y) == type)
                break;
            else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                if (!protect && y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0 &&
                        chessBoard.getPiece(x, y) != null && (type == 1 && chessBoard.getPiece(x, y).getName()
                        .equals("Pawn")))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
                if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (chessBoard.getPiece(x, y).getName().equals("Queen") || chessBoard.getPiece(x, y).getName()
                                .equals("Bishop")))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
                else
                    break;
            }
        }
        // Diagonal 1 \ Down
        for (y = yPos + 1, x = xPos + 1; y < chessBoard.getBoardHeight() && x < chessBoard.getBoardWidth(); y++, x++) {
            if (chessBoard.getBoardPosition(x, y) == type)
                break;
            else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                if (!protect && y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0 &&
                        chessBoard.getPiece(x, y) != null && (type == 2 && chessBoard.getPiece(x, y).getName()
                        .equals("Pawn")))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
                if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (chessBoard.getPiece(x, y).getName().equals("Queen") || chessBoard.getPiece(x, y).getName()
                                .equals("Bishop")))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
                else
                    break;
            }
        }
        // Diagonal 2 / Up
        for (y = yPos - 1, x = xPos + 1; y >= 0 && x < chessBoard.getBoardWidth(); y--, x++) {
            if (chessBoard.getBoardPosition(x, y) == type)
                break;
            else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                if (!protect && y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0 &&
                        chessBoard.getPiece(x, y) != null && (type == 1 && chessBoard.getPiece(x, y).getName()
                        .equals("Pawn")))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
                if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null &&
                        (chessBoard.getPiece(x, y).getName().equals("Queen") || chessBoard.getPiece(x, y).getName()
                                .equals("Bishop")))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
                else
                    break;
            }
        }
        // Diagonal 2 / Down
        for (y = yPos + 1, x = xPos - 1; y < chessBoard.getBoardHeight() && x >= 0; y++, x--) {
            if (chessBoard.getBoardPosition(x, y) == type)
                break;
            else if (chessBoard.getBoardPosition(x, y) == enemyType) {
                if (!protect && y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0 &&
                        chessBoard.getPiece(x, y) != null && (type == 2 && chessBoard.getPiece(x, y).getName()
                        .equals("Pawn")))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
                if (chessBoard.getBoardPosition(x, y) != 0 && (chessBoard.getPiece(x, y).getName().equals("Queen") ||
                        chessBoard.getPiece(x, y).getName().equals("Bishop")))
                    chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
                else
                    break;
            }
        }
        // Knight
        for (y = -2; y <= 2; y++) {
            if (y != 0) {
                x = y % 2 == 0 ? 1 : 2;
                if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight() && xPos - x >= 0 && xPos - x <
                        chessBoard.getBoardWidth() && chessBoard.getBoardPosition(xPos - x, yPos + y) != type &&
                        chessBoard.getBoardPosition(xPos - x, yPos + y) != 0) {
                    if (chessBoard.getPiece(xPos - x, yPos + y) != null &&
                            chessBoard.getPiece(xPos - x, yPos + y).getName().equals("Knight"))
                        chessBoard.saviorPieces.add(chessBoard.getPiece(xPos - x, yPos + y));
                }
                if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight() && xPos + x >= 0 && xPos + x <
                        chessBoard.getBoardWidth() && chessBoard.getBoardPosition(xPos + x, yPos + y) != type &&
                        chessBoard.getBoardPosition(xPos + x, yPos + y) != 0) {
                    if (chessBoard.getPiece(xPos + x, yPos + y) != null &&
                            chessBoard.getPiece(xPos + x, yPos + y).getName().equals("Knight"))
                        chessBoard.saviorPieces.add(chessBoard.getPiece(xPos + x, yPos + y));
                }
            }
        }
    }


    // Method to check checkmate
    public boolean isCheckmate(ChessBoard chessboard, int xPos, int yPos, int type) {
        boolean checkmate = true;
        int x = xPos;
        int y = yPos;
        for (y = yPos - 1; y <= yPos + 1; y++) {
            for (x = xPos - 1; x <= xPos + 1; x++) {
                if (y >= 0 && y < chessboard.getBoardHeight() && x >= 0 && x < chessboard.getBoardWidth() &&
                        chessboard.getBoardPosition(x, y) != type) {
                    if (!isCheck(chessboard, x, y, type, true)) {
                        checkmate = false;
                        break;
                    }
                }
            }
            if (!checkmate)
                break;
        }

        if (chessboard.checkPieces.size() < 2) {
            Piece checkPiece = chessboard.checkPieces.get(0);
            canCapture(chessboard, checkPiece);
            canProtect(chessboard, xPos, yPos, type, checkPiece);
            if (!chessboard.saviorPieces.isEmpty()) {
                for (Iterator<Piece> piece = chessboard.saviorPieces.iterator(); piece.hasNext(); ) {
                    Piece item = piece.next();
                    item.setASavior(true);
                    if (verticalProtection(chessboard, item.getX(), item.getY(), item.getType()) ||
                            horizontalProtection(chessboard, item.getX(), item.getY(), item.getType()) ||
                            slashDiagonalProtection(chessboard, item.getX(), item.getY(), item.getType()) ||
                            backslashDiagonalProtection(chessboard, item.getX(), item.getY(), item.getType())) {
                        item.setASavior(false);
                        piece.remove();
                    }
                }
            }
            if (!chessboard.saviorPieces.isEmpty())
                checkmate = false;
        }
        return (checkmate);
    }

    // Method to check is someone can capture the piece that threat the king
    public void canCapture(ChessBoard chessboard, Piece checkPiece) {
        findAllSaviorPieces(chessboard, checkPiece.getX(), checkPiece.getY(), checkPiece.getType(), false);
    }

    // Method to check is someone can capture the threatening piece or protect the king from the piece that threat him
    public void canProtect(ChessBoard chessboard, int xPos, int yPos, int type, Piece checkPiece) {
        if (checkPiece.getName().equals("Knight") || checkPiece.getName().equals("Pawn"))
            return;
        // Vertical up threat
        if (xPos == checkPiece.getX() && yPos > checkPiece.getY())
            for (int y = checkPiece.getY() + 1; y < yPos; y++)
                findAllSaviorPieces(chessboard, checkPiece.getX(), y, checkPiece.getType(), true);
        // Vertical down threat
        if (xPos == checkPiece.getX() && yPos < checkPiece.getY())
            for (int y = checkPiece.getY() - 1; y > yPos; y--)
                findAllSaviorPieces(chessboard, checkPiece.getX(), y, checkPiece.getType(), true);
        // Horizontal left threat
        if (xPos > checkPiece.getX() && yPos == checkPiece.getY())
            for (int x = checkPiece.getX() + 1; x < xPos; x++)
                findAllSaviorPieces(chessboard, x, checkPiece.getY(), checkPiece.getType(), true);
        // Horizontal right threat
        if (xPos < checkPiece.getX() && yPos == checkPiece.getY())
            for (int x = checkPiece.getX() - 1; x > xPos; x--)
                findAllSaviorPieces(chessboard, x, checkPiece.getY(), checkPiece.getType(), true);
        // Diagonal 1 \ up threat
        int y = checkPiece.getY() + 1;
        if (xPos > checkPiece.getX() && yPos > checkPiece.getY())
            for (int x = checkPiece.getX() + 1; x < xPos && y < yPos; x++, y++)
                findAllSaviorPieces(chessboard, x, y, checkPiece.getType(), true);
        // Diagonal 1 \ down threat
        y = checkPiece.getY() - 1;
        if (xPos < checkPiece.getX() && yPos < checkPiece.getY())
            for (int x = checkPiece.getX() - 1; x > xPos && y > yPos; x--, y--)
                findAllSaviorPieces(chessboard, x, y, checkPiece.getType(), true);
        // Diagonal 2 / up threat
        y = checkPiece.getY() + 1;
        if (xPos < checkPiece.getX() && yPos > checkPiece.getY())
            for (int x = checkPiece.getX() - 1; x > xPos && y < yPos; x--, y++)
                findAllSaviorPieces(chessboard, x, y, checkPiece.getType(), true);
        // Diagonal 2 / down threat
        y = checkPiece.getY() - 1;
        if (xPos > checkPiece.getX() && yPos < checkPiece.getY())
            for (int x = checkPiece.getX() + 1; x < xPos && y > yPos; x++, y--)
                findAllSaviorPieces(chessboard, x, y, checkPiece.getType(), true);
    }

    public boolean isThisProtecting(ChessBoard chessboard, int xPos, int yPos, int type) {
        Piece checkPiece = chessboard.checkPieces.get(0);
        // Vertical up threat
        if (chessboard.getKing(type).getX() == checkPiece.getX() && chessboard.getKing(type).getY() > checkPiece.getY())
            if (xPos == chessboard.getKing(type).getX() && yPos < chessboard.getKing(type).getY() &&
                    yPos > checkPiece.getY())
                return true;
        // Vertical down threat
        if (chessboard.getKing(type).getX() == checkPiece.getX() && chessboard.getKing(type).getY() < checkPiece.getY())
            if (xPos == chessboard.getKing(type).getX() && yPos > chessboard.getKing(type).getY() &&
                    yPos < checkPiece.getY())
                return true;
        // Horizontal left threat
        if (chessboard.getKing(type).getX() > checkPiece.getX() && chessboard.getKing(type).getY() == checkPiece.getY())
            if (yPos == chessboard.getKing(type).getY() && xPos < chessboard.getKing(type).getX() &&
                    xPos > checkPiece.getX())
                return true;
        // Horizontal right threat
        if (chessboard.getKing(type).getX() < checkPiece.getX() && chessboard.getKing(type).getY() == checkPiece.getY())
            if (yPos == chessboard.getKing(type).getY() && xPos > chessboard.getKing(type).getX() &&
                    xPos < checkPiece.getX())
                return true;
        // Diagonal 1 \ up threat
        int y = checkPiece.getY();
        if (chessboard.getKing(type).getX() > checkPiece.getX() && chessboard.getKing(type).getY() > checkPiece.getY())
            for (int x = checkPiece.getX(); x < chessboard.getKing(type).getX() && y < chessboard.getKing(type).getY();
                 x++, y++)
                if (xPos == x && yPos == y)
                    return true;
        // Diagonal 1 \ down threat
        y = checkPiece.getY();
        if (chessboard.getKing(type).getX() < checkPiece.getX() && chessboard.getKing(type).getY() < checkPiece.getY())
            for (int x = checkPiece.getX(); x > chessboard.getKing(type).getX() && y > chessboard.getKing(type).getY();
                 x--, y--)
                if (xPos == x && yPos == y)
                    return true;
        // Diagonal 2 / up threat
        y = checkPiece.getY();
        if (chessboard.getKing(type).getX() < checkPiece.getX() && chessboard.getKing(type).getY() > checkPiece.getY())
            for (int x = checkPiece.getX(); x > chessboard.getKing(type).getX() && y < chessboard.getKing(type).getY();
                 x--, y++)
                if (xPos == x && yPos == y)
                    return true;
        // Diagonal 2 / down threat
        y = checkPiece.getY();
        if (chessboard.getKing(type).getX() > checkPiece.getX() && chessboard.getKing(type).getY() < checkPiece.getY())
            for (int x = checkPiece.getX(); x < chessboard.getKing(type).getX() && y > chessboard.getKing(type).getY();
                 x++, y--)
                if (xPos == x && yPos == y)
                    return true;
        return false;
    }
}
