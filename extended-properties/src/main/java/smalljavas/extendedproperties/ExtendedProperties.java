package smalljavas.extendedproperties;

import org.apache.commons.lang3.text.StrSubstitutor;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Represents an extension of {@link Properties} adding some missing behaviour.
 */
public class ExtendedProperties extends Properties
{
  /**
   * Returns the value of the specified properties evaluating placeholders (e.g. ${myKey}).
   *
   * @param property
   * @return
   */
  @Override
  public String getProperty(String property)
  {
    String rawValue = getRawProperty(property);
    return StrSubstitutor.replace(rawValue, toRawMap());
  }

  public String getRawProperty(String property)
  {
    return super.getProperty(property);
  }

  
  public ExtendedProperties sub(String prefix)
  {
    String completePrefix = prefix + ".";

    ExtendedProperties sub = new ExtendedProperties();
    for (String prop: stringPropertyNames()) {
      if (!prop.startsWith(completePrefix)) continue;

      sub.setProperty(prop.substring(completePrefix.length()), getRawProperty(prop));
    }
    return sub;
  }

  private Map<String, String> toRawMap()
  {
    Map<String, String> valuesByProps = new HashMap<>();
    for (String prop: stringPropertyNames())
      valuesByProps.put(prop, getRawProperty(prop));
    return valuesByProps;
  }

}
