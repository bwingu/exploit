package controllers;
 
import java.security.SecureRandom;
import java.util.*;

import models.Check;
import models.Role;
import models.SecurityIdent;
import models.User;

import org.apache.commons.lang.StringUtils;

import parser.Parser;
import play.Play;
import play.data.validation.Required;
import play.libs.Crypto;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Finally;
import play.mvc.Http.Cookie;
import play.utils.Java;
import controllers.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.google.gson.Gson;
 
public class OpenApi extends Controller {


	private static class ClassObject {
		String name;
		List<MethodObject> methods = new ArrayList<MethodObject>();
	}
	
	private static class MethodObject {
		String name;
		String returnType;
		List<ArgObject> args = new ArrayList<ArgObject>();		
	}
	
	private static class ArgObject {
		String name;
		String type;
	}
	
	public static void api(){
		ClassObject classObject = new ClassObject();
		Class currentController = getControllerClass();
		classObject.name = currentController.getName();
		List<Method> methods = findActionMethods(currentController);
	
		for (Method method : methods) {
		  	MethodObject methodObject = new MethodObject();
			methodObject.name = method.getName();
			methodObject.returnType = Java.rawJavaType(method.getReturnType());
			//Get args :
			for (Class clazz : method.getParameterTypes()) {
		        ArgObject args = new ArgObject();
				//args.name = variable.getName();
				args.type = Java.rawJavaType(clazz);
				methodObject.args.add(args);
		    }
			
			classObject.methods.add(methodObject);
		}
		renderJSON(classObject);
	}
	
	private static List<Method> findActionMethods(Class clazz) {
		List<Method> methods = new ArrayList<Method>();
        while (!clazz.getName().equals("java.lang.Object")) {
            for (Method m : clazz.getDeclaredMethods()) {
                if (Modifier.isPublic(m.getModifiers())) {
                    // Check that it is not an intercepter
                    if (!m.isAnnotationPresent(Before.class) && !m.isAnnotationPresent(After.class) && !m.isAnnotationPresent(Finally.class)) {
                        methods.add(m);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return methods;
    }	
}