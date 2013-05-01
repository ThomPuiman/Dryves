package component;

import play.*;
import play.mvc.*;

import views.html.*;
import java.util.*;

public class RequestTools {
  
    public static String getParameter(String paramKey){
    	final Set<Map.Entry<String,String[]>> entries = Http.Context.current().request().queryString().entrySet();
        for (Map.Entry<String,String[]> entry : entries) {
            final String key = entry.getKey();
            if(paramKey.equals(key)){
            	return Arrays.toString(entry.getValue());
            }
        }
        return null;
    }
  
}
