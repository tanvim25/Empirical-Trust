package se.analysis.models;

public class UserScoreObject {
	public UserScoreObject(int userId, int score) {
		super();
		this.userId = userId;
		this.score = score;
	}
	int userId;
	int score;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
