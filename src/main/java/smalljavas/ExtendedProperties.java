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
   * Returns the value of the specified property evaluating placeholders (e.g. ${myKey}).
   *
   * <p>
   * Placeholders can be other props or system props.
   * </p>
   *
   * @param property a property
   * @return value of the specified property
   */
  @Override
  public String getProperty(String property)
  {
    String rawValue = getRawProperty(property);
    return StrSubstitutor.replace(rawValue, evaluatedValuesByKey());
  }

  public String getRawProperty(String property)
  {
    return super.getProperty(property);
  }

  /**
   * Returns a sub part of this properties.
   *
   * <p>
   * E.g. if you have a prop {@code greeting.morning} and you ask for {@code sub("greeting")} you obtain an
   * ExtendedProperties containing a prop {@code morning}.
   * </p>
   *
   * <p>
   * PAY ATTENTION: the returned object is a value object, so adding a new property to the original properties does not
   * affect the generated subs.
   * </p>
   *
   * @param prefix the prefix of the properties to returns in sub props
   * @return sub part of this properties
   */
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

  private Map<String, String> evaluatedValuesByKey()
  {
    Map<String, String> values = toRawMap();
    for (String prop: System.getProperties().stringPropertyNames()) {
      values.put(prop, System.getProperty(prop));
    }
    return values;
  }

  private Map<String, String> toRawMap()
  {
    Map<String, String> valuesByProps = new HashMap<>();
    for (String prop: stringPropertyNames())
      valuesByProps.put(prop, getRawProperty(prop));
    return valuesByProps;
  }

}
