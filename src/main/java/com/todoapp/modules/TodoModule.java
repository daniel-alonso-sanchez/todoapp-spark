package com.todoapp.modules;

import java.net.UnknownHostException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.todoapp.TodoService;
import com.todoapp.controllers.TodoController;
import com.todoapp.utils.Util;

public class TodoModule extends AbstractModule {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Properties props = new Properties();

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		try {
			props.load(this.getClass().getClassLoader()
					.getResourceAsStream("app.conf"));
			// configureDB();
		} catch (Exception e1) {
			logger.warn("DB Connection Error", e1);
		}
		bind(TodoService.class);
		bind(DB.class).toInstance( provideDatabase());
	}

	@Provides
	TodoController provideTodoController() {
		return new TodoController(props);
	}
	

	
	private DB provideDatabase() {
		String host = Util.getOrElse(props.getProperty("db.host"), "localhost");
		String portString = Util.getOrElse(props.getProperty("db.port"), "27017");
		String name = Util.getOrElse(props.getProperty("db.name"), "todoapp");
		
		int port = Integer.parseInt(portString);
		DB db=null;
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
				.connectionsPerHost(20).build();
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient(
					new ServerAddress(host, port), mongoClientOptions);
			mongoClient.setWriteConcern(WriteConcern.SAFE);
			db = mongoClient.getDB(name);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return db;
	}
}
