package smalljavas;

import java.time.LocalDate;

public class Customer {

	private final ID id;
	private final String firstName;
	private final String lastName;
	private final LocalDate birthDate;
	private final String title;
	private final String gender;

	public Customer(String firstName, String lastName, LocalDate birthDate, String title, String gender) {
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

	public String getGender() {
		return gender;
	}

}
