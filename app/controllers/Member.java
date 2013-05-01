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

public class Member extends Controller {
  
    public static Result profile(Integer id) {
        User profileUser = User.find.where().eq("id", id).findUnique();
		ArrayList<ArrayList<String>> friends = Facebook.getFacebookFriends();
        return ok(views.html.member.profile.render(profileUser, friends));
    }
}
