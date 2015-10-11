import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.TodoController;
import controllers.routes;
import models.Customer;

import org.junit.Test;

import play.api.test.FakeRequest;
import play.libs.Json;
import play.mvc.Result;
import utils.DemoData;
import views.html.defaultpages.notFound;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class SecurityControllerTest {

	@Test
	public void getAllCustomers() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				DemoData.loadDemoData();

				Result result = callAction(routes.ref.TodoController.getAllCustomers());

				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentAsString(result)).contains(DemoData.jack.name);
				assertThat(contentAsString(result)).contains(DemoData.tom.name);
				assertThat(contentAsString(result)).contains(DemoData.joe.name);
			}
		});
	}

	@Test
	public void getACustomer() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				DemoData.loadDemoData();
				Result result = callAction(routes.ref.TodoController.getCustomer(1));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentAsString(result)).contains(DemoData.tom.name);
			}
		});
	}

	@Test
	public void deleteACustomer() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				DemoData.loadDemoData();
				Result result = callAction(routes.ref.TodoController.getCustomer(1));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentAsString(result)).contains(DemoData.tom.name);
				
				Result delResult = callAction(routes.ref.TodoController.deleteCustomer(1));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentAsString(delResult)).contains(DemoData.tom.name);

				Result newResult = callAction(routes.ref.TodoController.getAllCustomers());
				assertThat(contentAsString(newResult)).doesNotContain(DemoData.tom.name);
			}
		});
	}
	
	@Test
	public void createACustomer() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				DemoData.loadDemoData();

                ObjectNode customerJson = Json.newObject();
                String name = "billy";
                customerJson.put("name", name);
				customerJson.put("address", "AKL");
                customerJson.put("phoneNumber", "23423323");
                
                play.test.FakeRequest fakeRequest = fakeRequest().withJsonBody(customerJson);

				Result result = callAction(routes.ref.TodoController.createCustomer(), fakeRequest);
                
                assertThat(status(result)).isEqualTo(OK);
                
                Customer c = Json.fromJson(Json.parse(contentAsString(result)), Customer.class);
                assertThat(c.id).isNotNull();
                assertThat(c.name).isEqualTo(name);
			}
		});
	}

	@Test
	public void editCustomer() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				DemoData.loadDemoData();
				Result result = callAction(routes.ref.TodoController.getCustomer(1));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentAsString(result)).contains(DemoData.tom.name);
				
                ObjectNode customerJson = Json.newObject();
                String name = "billy";
                customerJson.put("name", name);
				customerJson.put("address", "AKL");
                customerJson.put("phoneNumber", "23423323");
                
                play.test.FakeRequest fakeRequest = fakeRequest().withJsonBody(customerJson);

				Result result1 = callAction(routes.ref.TodoController.updateCustomer(1), fakeRequest);
                
                assertThat(status(result)).isEqualTo(OK);
				Result result2 = callAction(routes.ref.TodoController.getCustomer(1));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentAsString(result2)).contains(name);
                
			}
		});
	}

}
