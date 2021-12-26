package telran.b7a.accounting.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = { "login" })
@Document(collection = "user")
@NoArgsConstructor


public class UserAccount {
	@Id
	String login;
	String password;
	String firstName;
	String lastName;
	LocalDate passwordExpDate = LocalDate.now().plusDays(-30);
	Set<String> roles = new HashSet<>();
	
	public UserAccount(String login, String password, String firstName, String lastName, LocalDate passwordExpDate) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.passwordExpDate = LocalDate.now().plusDays(30);
	}

	public boolean addRole(String role) {
		return roles.add(role);
	}

	public boolean removeRole(String role) {
		return roles.remove(role);
	}

}
