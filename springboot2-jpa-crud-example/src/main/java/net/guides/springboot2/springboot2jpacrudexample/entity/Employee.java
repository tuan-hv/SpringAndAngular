package net.guides.springboot2.springboot2jpacrudexample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@NotNull(message = "Email not blank")
	@Column(name = "email_address", nullable = false)
	private String email;
	@Column(name = "address")
	private String address;
	@Column(name = "phone_number")
	private String PhoneNumber;

	@Column(name = "status")
	private int status;

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

}
