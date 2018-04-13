package org.sdr.model;

public class Picture {
	
	private String name;
	private String description;
	
	public Picture() {}
	
	public Picture(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String path) {
		this.name = path;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
