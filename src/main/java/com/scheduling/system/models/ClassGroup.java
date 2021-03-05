package com.scheduling.system.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity()
@Table(name = "classes")
public class ClassGroup implements Serializable {

	@Id
	@Column(nullable = false)
	private UUID classID;
	@Column(nullable = false)
	private String code;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@JoinTable(name = "rel_classes_students", joinColumns = @JoinColumn(name = "FK_CLASS", nullable = false), inverseJoinColumns = @JoinColumn(name = "FK_STUDENT", nullable = false))
	@ManyToMany(cascade = CascadeType.MERGE)
	private List<Student> students;

	private static final long serialVersionUID = 1L;

	public ClassGroup() {
		super();
	}

	public ClassGroup(String code, String title, String description) {
		this.classID = UUID.randomUUID();
		this.code = code;
		this.title = title;
		this.description = description;
	}

	public ClassGroup(UUID classID, String code, String title, String description) {
		super();
		this.classID = classID;
		this.code = code;
		this.title = title;
		this.description = description;
	}

	public void addStudent(Student student) {
		if (this.students == null) {
			this.students = new ArrayList<>();
		}
		this.students.add(student);
	}
	
	public void deleteStudent(Student student) throws Exception {
		int index = students.indexOf(student);
		if (index == -1) {
			throw new Exception("The student is not part of the class");
		}
		students.remove(index);
	}

	public Boolean isValid() {
		if (code == null || code.isEmpty() || title == null || title.isEmpty() || description == null
				|| description.isEmpty()) {
			return false;
		}
		return true;
	}

	
	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Student> getStudents() {
		return students;
	}

	public UUID getClassID() {
		return classID;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

}
