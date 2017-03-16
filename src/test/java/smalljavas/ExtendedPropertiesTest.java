package smalljavas;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExtendedPropertiesTest
{

  private ExtendedProperties eprops = new ExtendedProperties();

  @Before public void setup() {
    System.clearProperty("key");
  }

  @Test public void evaluate_placeholders() {
    eprops.setProperty("key", "Hello");
    eprops.setProperty("greeting", "${key} world");

    assertEquals("Hello world", eprops.getProperty("greeting"));
  }

  @Test public void system_props_have_an_higher_priority() {
    System.setProperty("key", "Ciao");

    eprops.setProperty("key", "Hello");
    eprops.setProperty("greeting", "${key} world");

    assertEquals("Ciao world", eprops.getProperty("greeting"));
  }

  @Test public void sub() {
    eprops.setProperty("greeting.friends", "Hello");
    eprops.setProperty("greeting.polite", "Good morning");

    ExtendedProperties subprops = eprops.sub("greeting");
    assertEquals("Hello", subprops.getProperty("friends"));
    assertEquals("Good morning", subprops.getProperty("polite"));
  }

}
