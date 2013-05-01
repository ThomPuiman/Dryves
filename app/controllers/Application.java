package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import model.*;
import org.json.simple.*;
import java.lang.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static User getCurrentUser(){
    	Http.Session sess = Http.Context.current().session();
    	if(sess.containsKey("currentUser")){
            Logger.info((String)sess.get("currentUser"));
    		Object obj=JSONValue.parse((String)sess.get("currentUser"));
    		JSONObject usr = (JSONObject)obj;
    		User currentUser = new User();
    		
    		try{
    			Integer l = Integer.parseInt(usr.get("id").toString());
    			currentUser.id = l;
    		}catch(NumberFormatException ex){}
    		currentUser.name = usr.get("name").toString();
    		currentUser.facebook_token = usr.get("facebook_token").toString();
    		currentUser.city = usr.get("city").toString();
    		currentUser.phone = usr.get("phone").toString();
    		currentUser.is_admin = Integer.parseInt(usr.get("is_admin").toString());
    		currentUser.is_active = Integer.parseInt(usr.get("is_active").toString());

        	return currentUser;  		
    	}else{
    		return null;
    	}
    }
  
}
