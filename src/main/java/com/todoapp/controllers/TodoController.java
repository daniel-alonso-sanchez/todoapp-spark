package com.todoapp.controllers;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.SparkBase.staticFileLocation;
import static spark.SparkBase.ipAddress;
import static spark.SparkBase.port;


import java.util.Properties;

import com.google.inject.Inject;
import com.todoapp.TodoService;
import com.todoapp.utils.Util;
/**
 * Created by shekhargulati on 09/06/14.
 */
@Controller
public class TodoController {

    private static final String API_CONTEXT = "/api/v1";
    @Inject
    private TodoService todoService;

    public TodoController(Properties props) {
    	
    	setupSpark(props);		
    	setupEndpoints();
	}

	private void setupSpark(Properties props) {  
		ipAddress(Util.getOrElse(props.getProperty("app.ipaddress"), "0.0.0.0"));
		String portString=Util.getOrElse(props.getProperty("app.port"), "4567");
		port(Integer.parseInt(portString));
		
        staticFileLocation("/public");
	}

	private void setupEndpoints() {
		 get("/", (request, response) -> {
			    response.redirect("/index");
			    return null;
		 });
		 get("/index",  (request, response) -> {
			    response.redirect("/index.html");			 
			    return null;
		 });
		
        post(API_CONTEXT + "/todos", "application/json", (request, response) -> {
            todoService.createNewTodo(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());

        get(API_CONTEXT + "/todos/:id", "application/json", (request, response)

                -> todoService.find(request.params(":id")), new JsonTransformer());

        get(API_CONTEXT + "/todos", "application/json", (request, response)

                -> todoService.findAll(), new JsonTransformer());

        put(API_CONTEXT + "/todos/:id", "application/json", (request, response)

                -> todoService.update(request.params(":id"), request.body()), new JsonTransformer());
    }


}
