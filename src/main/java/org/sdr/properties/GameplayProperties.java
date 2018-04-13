package org.sdr.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


//how many times is a user allowed to err when guessing a picture
//until a new one is presented?

@Configuration
@ConfigurationProperties(prefix = "gameplay")
public class GameplayProperties {
	private int numberOfTries;

	public int getNumberOfTries() {
		return numberOfTries;
	}
	public void setNumberOfTries(int numberOfTries) {
		this.numberOfTries = numberOfTries;
	}
}

