
package com.deeplake.idlframewok.item.misc;

import com.deeplake.idlframewok.item.ItemBase;


public class ItemBasicBinary extends ItemBase {

	public int getValue() {
		return value;
	}

	public ItemBasicBinary setValue(int value) {
		this.value = value;
		return this;
	}

	int value = 0;//0 = yin, 1 = yang

	public ItemBasicBinary(String name) {
		super(name);
	}



}
