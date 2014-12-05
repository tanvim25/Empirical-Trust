package se.analysis.models;

public class UserAnswer {
	int userId;
	int accptedAnswers;
	public UserAnswer(int userId, int accptedAnswers) {
			super();
			this.userId = userId;
			this.accptedAnswers = accptedAnswers;
		}
	
	public int getUserId() {
		return userId;
	}
	
	public int getAccptedAnswers() {
		return accptedAnswers;
	}

}
