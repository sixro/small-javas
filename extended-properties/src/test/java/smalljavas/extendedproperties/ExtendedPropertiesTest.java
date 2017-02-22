package smalljavas.extendedproperties;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExtendedPropertiesTest
{

  @Test public void evaluate_placeholders() {
    ExtendedProperties props = new ExtendedProperties();
    props.setProperty("key", "Hello");
    props.setProperty("greeting", "${key} world");

    assertEquals("Hello world", props.getProperty("greeting"));
  }

  @Test public void sub() {
    ExtendedProperties props = new ExtendedProperties();
    props.setProperty("greeting.friends", "Hello");
    props.setProperty("greeting.polite", "Good morning");

    ExtendedProperties subprops = props.sub("greeting");
    assertEquals("Hello", subprops.getProperty("friends"));
    assertEquals("Good morning", subprops.getProperty("polite"));
  }

}
