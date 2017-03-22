package smalljavas;

import java.lang.reflect.Field;
import java.util.*;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DB {

	private final DataSource dataSource;
	private final NamedParameterJdbcTemplate jdbc;

	public DB(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public void insert(Object o) {
		// FIXME
		// compose sql
		String sql = newSQL("insert into ${table} (${columns}) values (${values})", o);
		// execute
	}

	public <T> T fetch(Class<T> type, ID id) {
		// FIXME
		return null;
	}

	static String newSQL(String template, Object o) {
		Class<? extends Object> type = o.getClass();
		List<String> fieldNames = toNames(type.getDeclaredFields());

		Map<String, Object> valuesByPlaceholder = new HashMap<>();
		valuesByPlaceholder.put("table", (type.getSimpleName() + "s").toUpperCase());
		valuesByPlaceholder.put("columns", StringUtils.join(toSnakeCase(fieldNames), ", ").toUpperCase());
		valuesByPlaceholder.put("values", StringUtils.join(toPrefixed(fieldNames, ":"), ", "));
		return StrSubstitutor.replace(template, valuesByPlaceholder);
	}

	private static List<String> toPrefixed(List<String> names, String prefix) {
		List<String> ret = new ArrayList<>(names.size());
		for (String n: names)
			ret.add(prefix + n);
		return ret;
	}

	private static List<String> toSnakeCase(List<String> names) {
		List<String> ret = new ArrayList<>(names.size());
		for (String n: names) {
			String[] parts = StringUtils.splitByCharacterTypeCamelCase(n);
			String snakeCase = StringUtils.join(parts, "_");
			ret.add(snakeCase);
		}
		return ret;
	}

	private static List<String> toNames(Field[] fields) {
		List<String> ret = new ArrayList<>(fields.length);
		for (Field f: fields)
			ret.add(f.getName());
		return ret;
	}

}
