package org.sdr.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String name;
	private String password;
	private long score = 0;
	
	private List<Picture> picturesList = null;
	
	public User() {}
	
	public User (String name, String password, long score) {
		this.name = name;
		this.password = password;
		this.score = score;
	}

	public void addPicture(Picture pic) {
		if (picturesList == null) {
			picturesList = new ArrayList<Picture>();
		}
		picturesList.add(pic);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public List<Picture> getPicturesList() {
		return picturesList;
	}

	public void setPicturesList(List<Picture> picturesList) {
		this.picturesList = picturesList;
	}
	
	
}
