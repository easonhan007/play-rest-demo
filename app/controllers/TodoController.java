package controllers;

import com.wordnik.swagger.annotations.*;

import models.Todo;
import models.Customer;
import play.Logger;
import play.data.Form;
import play.mvc.*;

import java.util.List;

import static play.libs.Json.toJson;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Api(value = "/api/todos", description = "Operations with Todos")
//@Security.Authenticated(Secured.class)
public class TodoController extends Controller {

    @ApiOperation(value = "get All customers",
            notes = "Returns List of all customers",
            response = Customer.class,
            httpMethod = "GET")
    public static Result getAllCustomers() {
        return ok(toJson(models.Customer.all()));
    }

    @ApiOperation(value = "get a customer",
            notes = "Returns customer by id",
            response = Customer.class,
            httpMethod = "GET")
    public static Result getCustomer(Long id) {
        return ok(toJson(models.Customer.byId(id)));
    }

    @ApiOperation(value = "delete a customer",
            notes = "Deletes customer by id",
            response = Customer.class,
            httpMethod = "DELETE")
    public static Result deleteCustomer(Long id) {
    	ObjectNode res = JsonNodeFactory.instance.objectNode();
        Customer c = models.Customer.deleteById(id);
        if(c != null) {
        	return ok(toJson(c));
        }
        else{
        	return notFound(toJson(res.put("message","Customer Not Found"))); 
        }

    }


    @ApiOperation(
            nickname = "createCustomer",
            value = "Create Customer",
            notes = "Create Customer record",
            httpMethod = "POST",
            response = Customer.class
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "body",
                            dataType = "Customer",
                            required = true,
                            paramType = "body",
                            value = "Customer"
                    )
            }
    )
    @ApiResponses(
            value = {
                    @com.wordnik.swagger.annotations.ApiResponse(code = 400, message = "Json Processing Exception")
            }
    )

    public static Result createCustomer() {
        Form<models.Customer> form = Form.form(models.Customer.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        else {
            models.Customer customer = form.get();
            customer.save();
            return ok(toJson(customer));
        }
    }
    
    @ApiOperation(
            nickname = "updateCustomer",
            value = "Update Customer",
            notes = "Update Customer record",
            httpMethod = "PUT",
            response = Customer.class
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "body",
                            dataType = "Customer",
                            required = true,
                            paramType = "body",
                            value = "Customer"
                    )
            }
    )
    @ApiResponses(
            value = {
                    @com.wordnik.swagger.annotations.ApiResponse(code = 400, message = "Json Processing Exception")
            }
    )

    public static Result updateCustomer(Long id) {
    	ObjectNode res = JsonNodeFactory.instance.objectNode();
        Form<models.Customer> form = Form.form(models.Customer.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        else {
            models.Customer customer = form.get();
            Customer c = Customer.byId(id);
            if(c != null) {
            	c.name = customer.name; 
            	c.address = customer.address; 
            	c.phoneNumber = customer.phoneNumber;
            	c.save();
            	return ok(toJson(c));
            } else {
            	return notFound(toJson(res.put("message", "Customer Not Found")));
            }
        }
    }
}
