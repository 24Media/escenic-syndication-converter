package gr.twentyfourmedia.syndication.utilities;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Attributes Initialization At Application's Startup
 */
public class ApplicationContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
	    /**
	     * Read Values From miscellaneous.properties File
	     */
		final String props = "/miscellaneous.properties";
		final Properties propsFromFile = new Properties();
		
		try {
			
			propsFromFile.load(getClass().getResourceAsStream(props));
		} 
		catch (IOException exception) {
			
			exception.printStackTrace();
		}
		
		for(String prop : propsFromFile.stringPropertyNames()) {
			
			if(System.getProperty(prop) == null) {
				
				System.setProperty(prop, propsFromFile.getProperty(prop));
			}
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) { /*Do Nothing*/

	}
}