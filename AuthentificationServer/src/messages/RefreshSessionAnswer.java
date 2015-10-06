package messages;

import java.io.Serializable;

/**
* @author alexandre
* RefreshSessionAnswer.java
*/
public class RefreshSessionAnswer implements Message, Serializable {
	private static final long serialVersionUID = 4264015399583852116L;
	
	private Answer answer;
	
	public RefreshSessionAnswer(Answer answer) {
		this.answer = answer;
	}
	
	public Answer getAnswer() {
		return answer;
	}
	
	public enum Answer {
		SUCCESS, TIME_OUT, BAD_ADDRESS
	}
}

