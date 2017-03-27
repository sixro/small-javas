package smalljavas;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.*;

import org.apache.commons.dbcp2.*;
import org.junit.Test;

import smalljavas.Customer.Gender;

public class DBTest {

	@Test
	public void find_previously_stored_object() throws Exception {
		Properties props = new Properties();
		props.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
		props.setProperty("url", "jdbc:mysql://localhost/smalljavas");
		props.setProperty("username", "root");
		props.setProperty("password", "f1gaf1ga");
		
		BasicDataSource dataSource = BasicDataSourceFactory.createDataSource(props);
		DB db = new DB(dataSource);
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", Gender.male);
		db.insert(c);
		Customer fetched = db.fetch(Customer.class, c.getID());
		
		System.out.println("orig ...: " + c);
		System.out.println("fetched : " + fetched);
		assertEquals(c, fetched);
	}

	@Test
	public void newSQL_returns_expected_insert_statement() {
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", Gender.male);
		String sql = DB.newSQL("insert into ${table} (${columns}) values (${values})", c.getClass());
		assertEquals("insert into CUSTOMERS (ID, FIRST_NAME, LAST_NAME, BIRTH_DATE, TITLE, GENDER) values (:id, :firstName, :lastName, :birthDate, :title, :gender)", sql);
	}
	
	@Test
	public void newParamMap_returns_expected_map() {
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", Gender.male);
		Map<String, Object> map = DB.newParamMap(c);
		assertThat(map, hasEntry("firstName", (Object) "Mario"));
	}

	@Test
	public void newParamMap_handle_ID() {
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", Gender.male);
		Map<String, Object> map = DB.newParamMap(c);
		assertThat(map, hasKey("id"));
		assertTrue(map.get("id") instanceof String);
	}

	@Test
	public void newParamMap_handle_enums() {
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", Gender.female);
		Map<String, Object> map = DB.newParamMap(c);
		assertThat(map, hasEntry("gender", (Object) "female"));
	}

}
