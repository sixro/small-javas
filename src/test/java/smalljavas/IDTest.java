package smalljavas;

import org.junit.Test;

import static org.junit.Assert.*;

public class IDTest
{

  @Test public void seed() {
    ID id = ID.newID("xxx");
    System.out.println(id);
    assertTrue(id.toString().startsWith("xxx"));
  }

  @Test public void unique() {
    assertNotEquals(ID.newID("xxx"), ID.newID("xxx"));
  }

  @Test public void length() {
    assertEquals(35, ID.newID("xxx").toString().length());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalid_seed_toolong() {
    ID.newID("too_long");
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalid_seed_tooshort() {
    ID.newID("oo");
  }

}
