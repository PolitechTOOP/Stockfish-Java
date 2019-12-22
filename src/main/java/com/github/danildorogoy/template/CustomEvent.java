package com.github.danildorogoy.template;

import com.github.danildorogoy.template.piece.ChessPieceEnum;

public class CustomEvent {
    private String coord;
    private ChessPieceEnum chessPieceEnum;
    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

    public ChessPieceEnum getChessPieceEnum() {
        return chessPieceEnum;
    }

    public void setChessPieceEnum(ChessPieceEnum chessPieceEnum) {
        this.chessPieceEnum = chessPieceEnum;
    }

    public CustomEvent(String coord, ChessPieceEnum chessPieceEnum) {
        this.coord = coord;
        this.chessPieceEnum = chessPieceEnum;
    }
}
