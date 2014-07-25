package gr.twentyfourmedia.syndication.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import gr.twentyfourmedia.syndication.utilities.CustomException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception exception) {
 
		ModelAndView model = new ModelAndView("exception/exception");
		model.addObject("errorMessage", exception.getMessage());
		model.addObject("errorStackTrace", exceptionStackTraceToString(exception));
		
		return model;
	}
	
	@ExceptionHandler(CustomException.class)
	public ModelAndView handleCustomException(CustomException exception) {
 
		ModelAndView model = new ModelAndView("exception/exception");
		model.addObject("errorMessage", exception.getErrorMessage());
		
		return model;
	}
	
	/**
	 * Convert Exception's Stack Trace To String
	 * @param exception Raised Exception
	 * @return Stack Trace's String Representation
	 */
	private String exceptionStackTraceToString(Exception exception) {
		
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		exception.printStackTrace(printWriter);
		return writer.toString();
	}
}
