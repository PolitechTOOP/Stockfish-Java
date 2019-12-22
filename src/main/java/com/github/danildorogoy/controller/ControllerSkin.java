package com.github.danildorogoy.controller;

import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import org.springframework.stereotype.Component;

@Component
class ControllerSkin extends SkinBase<ComputerController> implements Skin<ComputerController> {
    public ControllerSkin(ComputerController cc) {
        super(cc);
    }
}
