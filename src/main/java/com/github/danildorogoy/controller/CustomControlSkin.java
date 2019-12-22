package com.github.danildorogoy.controller;


//basic implementation of a Skin

//imports 
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import org.springframework.stereotype.Component;

//class definition
@Component
class CustomControlSkin extends SkinBase<CustomControl> implements Skin<CustomControl> {
	public CustomControlSkin(CustomControl cc) {
		//call the super class constructor
		super(cc);
	}
}