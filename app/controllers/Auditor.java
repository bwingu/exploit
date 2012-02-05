package controllers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import play.Logger;
import play.mvc.ActionInvoker;
import play.mvc.Finally;
import play.mvc.Controller;
import play.mvc.Http;
import play.utils.Java;
 
public class Auditor extends Controller { 

	@Finally
	static void trace() throws Throwable {
		//if (getActionAnnotation(Audit.class) != null) {
			audit();
		//}
	}
	
	private static void audit() throws Throwable {		
	        /* Get the method name */
	        Object[] action = ActionInvoker.getActionMethod(Http.Request.current().action);
	        Class c = (Class)action[0];
	        Method m = (Method)action[1];
	        /* Get the method parameters name... */
	        String[] names = Java.parameterNames(m);
	        /* ... and the values */
	        Object[] args = ActionInvoker.getActionMethodArgs(m, null);
	        List<String> params = new ArrayList<String>();
	            for (int i = 0; i < names.length; i++) {
	                params.add(names[i] + "=" + args[i]);
	        }
			
			//TODO: Ajouter l'id de l'utilisateur ??
			
	        /* Log the action */
	        Logger.info("Execution de <%s.%s> avec les parametres <%s> à <%s>",
	            c.getSimpleName(), m.getName(), params, new Date());
	}
	

}