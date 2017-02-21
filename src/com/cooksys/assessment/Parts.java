package com.cooksys.assessment;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Parts {
	List<String> name;

	public List<String> getName() {
		if (name == null) {
			name = new ArrayList<String>();
		}
		return name;
	}

	public void newList() {
		name = new ArrayList<String>();
	}

	public void setName(List<String> name) {
		this.name = name;
	}
}