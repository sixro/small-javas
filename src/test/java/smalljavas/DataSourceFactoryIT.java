package smalljavas;

import static org.junit.Assert.*;

import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Test;

public class DataSourceFactoryIT {

	private static final Properties NO_ADDITIONAL_PROPS = new Properties();

	@Test
	public void create() {
		DataSource ds = DataSourceFactory.newMySqlDataSource(
			"localhost", 
			"smalljavas", 
			"root", 
			"f1gaf1ga", 
			NO_ADDITIONAL_PROPS
		);
		assertNotNull(ds);
	}

}
