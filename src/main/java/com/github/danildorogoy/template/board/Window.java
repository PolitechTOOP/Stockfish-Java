package com.github.danildorogoy.template.board;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Window extends Group {

    private Rectangle r;
    private Translate pos;
    private boolean isHighlighted = false;
    private final Log log = LogFactory.getLog(Window.class);
    private final String coords;

    public Window(boolean isBlack, String id) {
        pos = new Translate();
        r = new Rectangle();
        coords = id;
        r.getTransforms().add(pos);
        if (isBlack) {
            r.setFill(Color.GREY);
        } else {
            r.setFill(Color.WHITE);
        }
        setOnMouseClicked(mouseEvent -> {
            log.info(mouseEvent);
            log.info("Clicked! " + coords);
        });
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
