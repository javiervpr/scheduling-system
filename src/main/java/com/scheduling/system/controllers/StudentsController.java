package com.scheduling.system.controllers;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scheduling.system.models.Student;
import com.scheduling.system.services.IStudentService;

/**
 * @author Javier
 *
 */
@Controller
public class StudentsController {

	@Autowired
	private IStudentService studentService;

	/**
	 * View with the list of students
	 * 
	 * @param query list's filter
	 * @param model
	 * @return a view with the list of students
	 */
	@GetMapping("/students-list")
	public String list(@RequestParam(name = "query", required = false, defaultValue = "") String query, Model model) {
		if (query == null || query.isEmpty()) {
			model.addAttribute("list", studentService.findAll());
			model.addAttribute("query", query);
		} else {
			model.addAttribute("list", studentService.findByFullName(query));
			model.addAttribute("query", query);
		}
		return "students-list";
	}

	/**
	 * View with student's classes
	 * 
	 * @param id    student's id
	 * @param model
	 * @return a view with student's classes
	 */
	@GetMapping("/classes-of-student/{id}")
	public String classesOfAStudent(@PathVariable(value = "id") String id, Model model) {
		Student student = null;
		if (id == null || id.isEmpty()) {
			return "redirect:/not-found";
		}
		student = studentService.findOne(UUID.fromString(id));
		if (student == null) {
			return "redirect:/not-found";
		}
		model.addAttribute("student", student);
		return "classes-of-student";
	}

	/**
	 * A form view to register a new Student
	 * 
	 * @param model
	 * @return a form view to register a new Student
	 */
	@GetMapping("/student-form")
	public String registerStudent(Map<String, Object> model) {
		model.put("student", new Student());
		model.put("title", "Register a new student");
		return "student-form";
	}

	/**
	 * Save a new register of student
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/student-form")
	public String registerStudent(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
			model.put("student", new Student());
			model.put("err", "Complete all the fields");
			return "student-form";
		}
		Student student = new Student(firstName, lastName);
		studentService.save(student);
		return "redirect:students-list";
	}
	/**
	 * A form view to update a Student 
	 * @param id student's id
	 * @param model
	 * @return A form view to update a Student
	 */
	@GetMapping("/student-form/{id}")
	public String updateStudent(@PathVariable(value = "id") String id, Map<String, Object> model) {
		Student student = null;
		if (id == null || id.isEmpty()) {
			return "redirect:/not-found";
		}
		student = studentService.findOne(UUID.fromString(id));
		if (student == null) {
			return "redirect:/not-found";
		}
		model.put("student", student);
		model.put("title", "Update the student");
		return "student-form";
	}
	/**
	 * Update the student
	 * @param id student's id
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/student-form/{id}")
	public String updateStudent(@PathVariable(value = "id") String id, Map<String, Object> model,
			HttpServletRequest request) {
		String fisrtName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String studentID = request.getParameter("studentID");
		Student student = new Student(UUID.fromString(studentID), fisrtName, lastName);
		studentService.save(student);
		return "redirect:/students-list";
	}
	/**
	 * Delete a student
	 * @param id student's id
	 * @return
	 */
	@GetMapping("/student-delete/{id}")
	public String deleteStudent(@PathVariable(value = "id") String id) {
		Student student = null;
		if (id == null || id.isEmpty()) {
			return "redirect:/not-found";
		}
		student = studentService.findOne(UUID.fromString(id));
		if (student == null) {
			return "redirect:/not-found";
		}
		try {
			studentService.delete(UUID.fromString(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/students-list";
	}
}
