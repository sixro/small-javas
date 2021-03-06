package smalljavas;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.*;

public class Customer {

	public static enum Gender { male, female };
	
	private final ID id;
	private final String firstName;
	private final String lastName;
	private final LocalDate birthDate;
	private final String title;
	private final Gender gender;
	private int accessCount = 0;

	@SuppressWarnings("unused")
	private Customer() {
		this.id = null;
		this.firstName = null;
		this.lastName = null;
		this.birthDate = null;
		this.title = null;
		this.gender = null;
	}
	
	public Customer(String firstName, String lastName, LocalDate birthDate, String title, Gender gender) {
		this.id = ID.newID("cus");
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.title = title;
		this.gender = gender;
	}

	public ID getID() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public String getTitle() {
		return title;
	}

	public Gender getGender() {
		return gender;
	}

	public int getAccessCount() {
		return accessCount;
	}

	public void incrementAccessCount() {
		this.accessCount++;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
