package smalljavas;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Properties;

import org.apache.commons.dbcp2.*;
import org.junit.Test;

public class DBTest {

	@Test
	public void find_previously_stored_object() throws Exception {
		Properties props = new Properties();
		props.setProperty("driverClassName", "com.mysql.jdbc.Driver");
		props.setProperty("url", "jdbc:mysql://localhost/smalljavas");
		props.setProperty("user", "root");
		props.setProperty("password", "f1gaf1ga");
		
		BasicDataSource dataSource = BasicDataSourceFactory.createDataSource(props);
		DB db = new DB(dataSource);
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", "m");
		
		db.insert(c);
		assertEquals(c, db.fetch(Customer.class, c.getID()));
	}

	@Test
	public void newSQL_returns_expected_insert_statement() {
		// FIXME
		Customer c = new Customer("Mario", "Rossi", LocalDate.parse("1970-01-02"), "mr", "m");
		String sql = DB.newSQL("insert into ${table} (${columns}) values (${values})", c);
		assertEquals("insert into CUSTOMERS (ID, FIRST_NAME, LAST_NAME, BIRTH_DATE, TITLE, GENDER) values (:id, :firstName, :lastName, :birthDate, :title, :gender)", sql);
	}
	
}
