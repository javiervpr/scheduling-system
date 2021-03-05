package com.scheduling.system.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity()
@Table(name = "students")
public class Student implements Serializable {

	@Id
	@Column(nullable = false)
	private UUID studentID;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@ManyToMany(mappedBy = "students", cascade = CascadeType.MERGE)
	private List<ClassGroup> classes;
	
	private static final long serialVersionUID = 1L;

	public Student() {
		super();
	}

	public Student(String firstName, String lastName) {
		this.studentID = UUID.randomUUID();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(UUID studentID, String firstName, String lastName) {
		super();
		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public void deleteClasses() {
		classes = null;
	}
	public List<ClassGroup> getClasses() {
		return classes;
	}

	public UUID getStudentID() {
		return studentID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
