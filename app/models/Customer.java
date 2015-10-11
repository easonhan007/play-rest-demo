package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Customer extends Model {

    @Id
    public Long id;

    @Column(length = 1024, nullable = false)
    @Constraints.MaxLength(1024)
    @Constraints.Required
    public String name;

    @Column(length = 1024, nullable = false)
    @Constraints.MaxLength(1024)
    @Constraints.Required
    public String address;

    @Column(length = 1024, nullable = false)
    @Constraints.MaxLength(1024)
    @Constraints.Required
    public String phoneNumber;

    public static Finder<Long, Customer> find = new Finder<Long, Customer>(Long.class, Customer.class);
    public Customer(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static List<Customer> all() {
        return find.all();
    }
    
    public static Customer byId(Long id) {
    	return find.where().idEq(id).findUnique();
    }
    
    public static Customer deleteById(Long id) {
    	Customer c = byId(id);
    	if(c != null) {
    		c.delete();
    		return c;
    	} else {
    		return null;
    	}
    }
}
