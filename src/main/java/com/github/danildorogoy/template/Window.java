package com.github.danildorogoy.template;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;

public class Window extends Group {

    private Rectangle r;
    private Translate pos;
    private boolean isHighlighted = false;

    public Window(int i) {
        pos = new Translate();
        r = new Rectangle();
        r.getTransforms().add(pos);
        if (i == 0)
            r.setFill(Color.GREY);
        else
            r.setFill(Color.WHITE);

        getChildren().add(r);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        r.setHeight(height);
        r.setWidth(width);
    }

    @Override
    public void relocate(double x, double y) {
        super.relocate(x, y);
        pos.setX(x);
        pos.setY(y);
    }

    public void highlightWindow(Color color) {
        r.setStrokeType(StrokeType.INSIDE);
        r.setStrokeWidth(4);
        r.setStroke(color);
        if (color == Color.GREEN)
            isHighlighted = true;
    }

    public void unhighlight() {
        r.setStroke(null);
        isHighlighted = false;
    }

    public boolean isHighlighted() {
        return (isHighlighted);
    }

    public Rectangle getRectangle() {
        return (r);
    }
}
