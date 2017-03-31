package smalljavas;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.*;

public class DataSourceFactory {

	private DataSourceFactory() { }
	
	/**
	 * Returns a MySql DataSource setting all needed properties as expected (e.g. driver classname, jdbc url, etc...).
	 * 
	 * <p>
	 * If you have an issue related to the timezone you have 2 options:
	 * </p>
	 * <ul>
	 * 	<li>run command: {@code SET GLOBAL time_zone = <timezone>}</li>
	 * 	<li>start {@code mysqld_safe} with option {@code --timezone=<timezone>}</li>
	 * </ul>
	 * <p>
	 * where {@code <timezone>} can be {@code Europe/Rome}.
	 * </p>
	 * 
	 * @param server MySql server
	 * @param db database
	 * @param user a user
	 * @param password a password
	 * @param additionalProps additional props found in commons-dbcp
	 * @return a MySql DataSource
	 */
	public static DataSource newMySqlDataSource(String server, String db, String user, String password, Properties additionalProps) {
		Properties props = new Properties();
		props.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
		props.setProperty("url", "jdbc:mysql://" + server + "/" + db);
		props.setProperty("username", user);
		props.setProperty("password", password);
		props.putAll(additionalProps);
		
		try {
			return BasicDataSourceFactory.createDataSource(props);
		} catch (Exception e) {
			throw new RuntimeException("Unable to create a MySql datasource", e);

		}
	}

}
