package org.sdr.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;



//determine the scores given for uploading a picture
//and guessing a picture
//It seems this class isn't actually needed, Boot is smart
//enough to read directly from application.properties

@Configuration
@ConfigurationProperties(prefix = "score")
public class ScoreProperties {
	private int increaseScoreAmountIfUploading;
	private int increaseScoreAmountIfGuessing;
	
	public int getIncreaseAmountIfUploading() {
		return increaseScoreAmountIfUploading;
	}
	public void setIncreaseAmountIfUploading(int increaseAmountIfUploading) {
		this.increaseScoreAmountIfUploading = increaseAmountIfUploading;
	}
	public int getIncreaseAmountIfGuessing() {
		return increaseScoreAmountIfGuessing;
	}
	public void setIncreaseAmountIfGuessing(int increaseAmountIfGuessing) {
		this.increaseScoreAmountIfGuessing = increaseAmountIfGuessing;
	}
	


}
