package com.learn.selfeditor;

import java.beans.PropertyEditorSupport;

/**
 * 自定义编辑器
 */
public class AddressPropertyEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String[] s = text.split("_");
		Address address = new Address();
		address.setProvince(s[0]);
		address.setCity(s[1]);
		address.setTown(s[2]);
		this.setValue(address);
	}
}
