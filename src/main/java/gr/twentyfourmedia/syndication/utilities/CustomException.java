package gr.twentyfourmedia.syndication.utilities;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public CustomException(String errorMessage) {
		
		this.errorMessage = errorMessage;
	}
 
	public String getErrorMessage() {
		
		return errorMessage;
	}
 
	public void setErrorMessage(String errorMessage) {
		
		this.errorMessage = errorMessage;
	}	
}