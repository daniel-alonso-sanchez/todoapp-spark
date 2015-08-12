package com.todoapp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.todoapp.controllers.TodoController;
import com.todoapp.modules.TodoModule;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class Bootstrap {
      
	/**
	public static void main(String[] args) throws Exception {
        setIpAddress(IP_ADDRESS);
        setPort(PORT);
        staticFileLocation("/public");
        new TodoResource(new TodoService(mongo()));
    }
**/
	public static void main(String[] args) throws Exception {
		Injector injector = Guice.createInjector(
		        new TodoModule()
		    );
		TodoController billingService = injector.getInstance(TodoController.class);
		injector.injectMembers(billingService);
	}
   
/**
	public Bootstrap() {		
		try {
			
			props.load(this.getClass().getClassLoader().getResourceAsStream("app.conf"));
			//configureDB();
		} catch (Exception e1) {
			logger.warn("DB Connection Error", e1);
		}
		
		setPort(Integer.parseInt(Util.getOrElse(props.getProperty("port"), "9000")));
		externalStaticFileLocation(Util.getOrElse(props.getProperty("public_dir"), "client"));
		JWT.SECRET = props.getProperty("app.secret").trim();
		
		logger.info("Loading routes");
		Set<Class<?>> classes = new Reflections("").getTypesAnnotatedWith(Controller.class);		
		logger.info(format("Found %d controllers", classes.size()));		
				
		for ( Class clazz : classes ) {
			logger.info("Loading Controller -> " + clazz.getName());
			
			try {
				clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				logger.error("Problem launchingcontroller {}", clazz.getName(), e);
				System.exit(1);
			}
		}		
	}
    **/
}
