package smalljavas;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents an ID useful to identify an Entity (as intended in DDD).
 */
public class ID
{
  private final String value;

  private ID(String value) {
    this.value = value;
  }

  public static ID newID(String seed)
  {
    if (seed.length() != 3) throw new IllegalArgumentException("seed has to be of 3 chars");
    return new ID(seed + UUID.randomUUID().toString().replaceAll("-", ""));
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (!(o instanceof ID)) return false;
    ID id = (ID) o;
    return Objects.equals(value, id.value);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(value);
  }

  @Override
  public String toString()
  {
    return value;
  }

}
