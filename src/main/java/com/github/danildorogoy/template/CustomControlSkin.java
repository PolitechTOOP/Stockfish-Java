package com.github.danildorogoy.template;

import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;

class CustomControlSkin extends SkinBase<CustomControl> implements Skin<CustomControl> {
	public CustomControlSkin(CustomControl cc) {
		super(cc);
	}
}