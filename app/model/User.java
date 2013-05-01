package model;
 
import java.util.*;
import javax.persistence.*;
 
import play.api.libs.Crypto;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
 
import play.Logger;
 
import com.avaje.ebean.*;

@Entity
public class User extends Model {
 
    public String name;
    public String facebook_token;
    public String city;
    public String phone;
    public Date birthday;
    public Integer is_admin;
    public Integer is_active;
    @Id
    public Integer id;

    public static Model.Finder<Integer,User> find = new Model.Finder(Integer.class, User.class);
 
	public static List<User> findAll() {
	    return find.all();
	}

	public static User find(){
		return find.findUnique();
	}
 
}