package controllers;

import play.*;
import play.mvc.*;
import component.*;

import views.html.*;
import org.json.simple.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import model.*;
import java.util.*;
import java.text.*;

public class Facebook extends Controller {
  
    public static Result index() {

        if(RequestTools.getParameter("access_token") != null){
            redirect("/facebook/show");
        }else{
            return ok(redirectfacebook.render());
        }
        
        String access_token = RequestTools.getParameter("access_token");
        access_token = access_token.replace("[","").replace("]", "");

        if(access_token != null){
        	Http.Session sess = Http.Context.current().session();
            sess.put("access_token", access_token);
        
            JSONObject userObj = getFacebookUser();
            //String[][] fbFriends = Application.Facebook.getFacebookFriends();

            Object userName = userObj.get("name");
            Object userId = userObj.get("id");

            createSession(userObj);

            return redirect("/member/profile/" + Application.getCurrentUser().id);
        }else{
			return ok(redirectfacebook.render());
        }
    }

    public static void createSession(JSONObject fbInfo){
        Object userName = fbInfo.get("name");
        Object userId = fbInfo.get("id");
        Date birthDay = new Date();
        

        User currentUsr = User.find.where().eq("facebook_token", userId).findUnique();

        //.find("facebook_token = :fb")
        //    .bind("fb", userId).fetch();
        if(currentUsr == null || currentUsr.id == null || currentUsr.id < 1){
            currentUsr = new User();
            currentUsr.name = (String)userName;
            currentUsr.facebook_token = (String)userId;
            currentUsr.city = "";
            currentUsr.phone = "";
            currentUsr.birthday = birthDay;
            currentUsr.is_admin = 0;
            currentUsr.is_active = 1;
            currentUsr.save();
        }
        Http.Session sess = Http.Context.current().session();

        JSONObject obj=new JSONObject();
        obj.put("name",currentUsr.name);
        obj.put("facebook_token",currentUsr.facebook_token);
        obj.put("city",currentUsr.city);
        obj.put("phone",currentUsr.phone);
        obj.put("birthday",currentUsr.birthday.toString());
        obj.put("is_admin",currentUsr.is_admin);
        obj.put("is_active",currentUsr.is_active);
        obj.put("id",currentUsr.id);
        StringWriter out = new StringWriter();
        try{
            obj.writeJSONString(out);
        }catch(java.io.IOException ex){}
        String jsonText = out.toString();
        sess.put("currentUser", jsonText);
    }

    public static Result uitloggen(){
        Http.Session sess = Http.Context.current().session();
        sess.remove("currentUser");
        return redirect("/");
    }

    public static JSONObject getFacebookUser(){
        JSONObject jsonObject = null;
        Http.Session sess = Http.Context.current().session();
        
        String access_token = (String)sess.get("access_token");
        BufferedReader reader;
        if(access_token != null){
            try {
                URL url = new URL("https://graph.facebook.com/me?access_token=" + access_token);
                
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;
                Object obj = JSONValue.parse(reader);
                jsonObject = (JSONObject) obj;  
                
                reader.close();
            } catch (MalformedURLException ex) {
                Logger.info(ex.getMessage());
            } catch (java.io.IOException ex) {
                Logger.info(ex.getMessage());
            } 
        }
        return jsonObject;
    }
    
    public static ArrayList<ArrayList<String>> getFacebookFriends(){
        JSONObject jsonObject = null;
        Http.Session sess = Http.Context.current().session();
        String access_token = (String)sess.get("access_token");
        Logger.info(access_token);
        ArrayList<ArrayList<String>> returnObject = new ArrayList<ArrayList<String>>();
        BufferedReader reader;
        if(access_token != null){
            try {
                
                URL url = new URL("https://graph.facebook.com/me/friends?access_token=" + access_token);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;
                StringBuilder jsonString = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    jsonString.append(line);
                }
                Logger.info(jsonString.toString());
                Object obj = JSONValue.parse(jsonString.toString());
                jsonObject = (JSONObject) obj;  
                JSONArray jArray = (JSONArray) jsonObject.get("data");
                for(int i = 0; i < jArray.size(); i++){
                   JSONObject jobj = (JSONObject) jArray.get(i);

                   ArrayList<String> temp = new ArrayList<String>();
                   temp.add(jobj.get("name").toString());
                   temp.add(jobj.get("id").toString());
                   returnObject.add(temp);
                }
                
                reader.close();
            } catch (MalformedURLException ex) {
                Logger.info(ex.getMessage());
            } catch (IOException ex) {
                Logger.info(ex.getMessage());
            } 
        }
        return returnObject;
    }
}
