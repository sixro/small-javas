package smalljavas;

import java.lang.reflect.*;
import java.sql.*;
import java.time.*;
import java.util.*;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;

public class DB {

	private static final Logger LOG = LoggerFactory.getLogger(DB.class);
	
	private final NamedParameterJdbcTemplate jdbc;

	public DB(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public void insert(Object o) {
		long start = System.currentTimeMillis();
		LOG.info("inserting {}...", o);
		
		String sql = newSQL("insert into ${table} (${columns}) values (${values})", o.getClass());
		Map<String, Object> paramMap = newParamMap(o);
		LOG.debug("... paramMap: {}...", paramMap);
		jdbc.update(sql, paramMap);
		
		LOG.info("... {} inserted in {} ms", o, System.currentTimeMillis() -start);
	}

	public <T> T fetch(Class<T> type, ID id) {
		long start = System.currentTimeMillis();
		LOG.info("fetching {} with identifier {} ...", type, id);
		
		String sql = newSQL("select ${columns} from ${table} where id = :id", type);
		T object = jdbc.queryForObject(sql, Collections.singletonMap("id", (Object) id.toString()), new DB.AutoMapper<T>(type));
		
		LOG.info("... returning {} fetched in {} ms", object, System.currentTimeMillis() -start);
		return object;
	}

	static String newSQL(String template, Class<?> type) {
		List<String> fieldNames = toNames(type.getDeclaredFields());

		Map<String, Object> valuesByPlaceholder = new HashMap<>();
		valuesByPlaceholder.put("table", (type.getSimpleName() + "s").toUpperCase());
		valuesByPlaceholder.put("columns", StringUtils.join(toSnakeCase(fieldNames), ", ").toUpperCase());
		valuesByPlaceholder.put("values", StringUtils.join(toPrefixed(fieldNames, ":"), ", "));
		return StrSubstitutor.replace(template, valuesByPlaceholder);
	}
	
	static Map<String, Object> newParamMap(Object o) {
		Map<String, Object> map = new LinkedHashMap<>();
		Field[] fields = o.getClass().getDeclaredFields();
		for (Field f: fields) {
			try {
				if (!f.isAccessible()) f.setAccessible(true);
				Object value = f.get(o);
				if (value instanceof ID)
					value = ((ID) value).toString();
				if (Enum.class.isAssignableFrom(value.getClass()))
					value = ((Enum<?>) value).name();
				map.put(f.getName(), value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return map;
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

	
	public static class AutoMapper<T> implements RowMapper<T> {

		private final Class<T> type;

		public AutoMapper(Class<T> type) {
			this.type = type;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T mapRow(ResultSet rs, int row) throws SQLException {
			
			try {
				Constructor<T> constructor = type.getDeclaredConstructor();
				if (!constructor.isAccessible()) constructor.setAccessible(true);
				T obj = constructor.newInstance();
				Field[] fields = type.getDeclaredFields();
				for (Field f: fields) {
					System.out.println(f.getName());
					if (! f.isAccessible()) f.setAccessible(true);
					// FIXME performance
					String column = toSnakeCase(Collections.singletonList(f.getName())).get(0);
					Object value = rs.getObject(column);
					
					// FIXME handle all datetime types
					// FIXME int
					if (ID.class.isAssignableFrom(f.getType()))
						value = ID.valueOf((String) value);
					if (LocalDate.class.isAssignableFrom(f.getType()))
						value = ((Timestamp) value).toLocalDateTime().toLocalDate();
					if (Enum.class.isAssignableFrom(f.getType()))
						value = Enum.valueOf((Class) f.getType(), (String) value);
					f.set(obj, value);
				}
				return obj;
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}

	}

}
