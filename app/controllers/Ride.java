package controllers;

import play.*;
import play.mvc.*;
import component.*;

import views.html.*;
import org.json.simple.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;

public class Ride extends Controller {
  
    public static Result newride() {

        return ok(newride.render());
    }
}
