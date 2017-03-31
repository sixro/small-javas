package smalljavas;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.*;

import smalljavas.Customer.Gender;

public class DBIT
{
	private DB db;

	@Before public void setup() throws Exception {
		db = new DB(
			DataSourceFactory.newMySqlDataSource(
				"localhost", 
				"smalljavas", 
				"root", 
				"f1gaf1ga", 
				new Properties()
			)
		);
	}
	
	@Test
	public void find_previously_stored_object() {
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", Gender.male);
		c.incrementAccessCount();
		
		db.insert(c);
		Customer fetched = db.select(Customer.class, c.getID());
		
		System.out.println("orig ...: " + c);
		System.out.println("fetched : " + fetched);
		assertEquals(c, fetched);
	}

	@Test
	public void returns_null_when_not_found() {
		assertNull(db.select(Customer.class, ID.valueOf("not-exists")));
	}

	@Test
	public void delete_previously_stored_object() {
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", Gender.male);
		
		db.insert(c);
		db.delete(Customer.class, c.getID());
		
		assertNull(db.select(Customer.class, c.getID()));
	}

	@Test
	public void delete_an_object() {
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", Gender.male);
		
		db.insert(c);
		db.delete(c);
		
		assertNull(db.select(Customer.class, c.getID()));
	}

	@Test
	public void update_previously_stored_object() {
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", Gender.male);
		
		db.insert(c);
		
		c.incrementAccessCount();
		db.update(c);
		
		assertEquals(1, db.select(Customer.class, c.getID()).getAccessCount());
	}

	@Test
	public void newSQL_returns_expected_insert_statement() {
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", Gender.male);
		String sql = DB.newSQL("insert into ${table} (${columns}) values (${values})", c.getClass());
		assertEquals("insert into CUSTOMERS (ID, FIRST_NAME, LAST_NAME, BIRTH_DATE, TITLE, GENDER, ACCESS_COUNT) values (:id, :firstName, :lastName, :birthDate, :title, :gender, :accessCount)", sql);
	}
	
	@Test
	public void newSQL_returns_expected_update_statement() {
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", Gender.male);
		String sql = DB.newSQL("update ${table} set ${columnsAndValues} where ID = :id", c.getClass());
		assertEquals("update CUSTOMERS set ID = :id, FIRST_NAME = :firstName, LAST_NAME = :lastName, BIRTH_DATE = :birthDate, TITLE = :title, GENDER = :gender, ACCESS_COUNT = :accessCount where ID = :id", sql);
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

	@Test
	public void newParamMap_handle_integers() {
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", Gender.female);
		c.incrementAccessCount();
		Map<String, Object> map = DB.newParamMap(c);
		assertThat(map, hasEntry("accessCount", (Object) 1));
	}
}
