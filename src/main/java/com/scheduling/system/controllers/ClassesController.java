package com.scheduling.system.controllers;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scheduling.system.models.ClassGroup;
import com.scheduling.system.services.IClassGroupService;

@Controller
public class ClassesController {

	@Autowired
	private IClassGroupService classService;

	/**
	 * A view with the list of classes
	 * @param query list's filter
	 * @param model
	 * @return a view with the list of classes
	 */
	@GetMapping({ "/", "/home", "/classes-list" })
	public String index(@RequestParam(name = "query", required = false, defaultValue = "") String query,Model model) {
		if (query == null || query.isEmpty()) {
			model.addAttribute("list", classService.findAll());
			model.addAttribute("query", query);
		} else {			
			model.addAttribute("list", classService.findByQuery(query));
			model.addAttribute("query", query);
		}
		return "classes-list";
	}
	/**
	 * A form view to register a new class
	 * @param model
	 * @return a form view to register a new class
	 */
	@GetMapping("/class-form")
	public String registerClass(Map<String, Object> model) {
		model.put("classGroup", new ClassGroup());
		model.put("title", "Register a new class");
		return "class-form";
	}
	/**
	 * Save a new class
	 * @param model
	 * @param request
	 * @param response
	 * @return 
	 */
	@PostMapping("/class-form")
	public String registerClass(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		ClassGroup classGroup = new ClassGroup(code, title, description);
		if (!classGroup.isValid()) {
			model.put("classGroup", classGroup);
			model.put("err", "Complete all the fields");
			return "class-form";
		}
		classService.save(classGroup);
		return "redirect:classes-list";
	}
	/**
	 * A form view to update a Class
	 * @param id class' id
	 * @param model
	 * @return
	 */
	@GetMapping("/class-form/{id}")
	public String updateClass(@PathVariable(value = "id") String id, Map<String, Object> model) {
		ClassGroup classGroup = null;
		if (id == null || id.isEmpty() ) {
			return "redirect:/not-found";
		}
		classGroup = classService.findOne(UUID.fromString(id));
		if (classGroup == null ) {
			return "redirect:/not-found";
		}
		model.put("classGroup", classGroup);
		model.put("title", "Update the class");
		return "class-form";
	}
	/**
	 * Update the class
	 * @param id class' id
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/class-form/{id}")
	public String updateClass(@PathVariable(value = "id") String id, Map<String, Object> model, HttpServletRequest request) {
		String code = request.getParameter("code");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String classID = request.getParameter("classID");
		ClassGroup classGroup = new ClassGroup(UUID.fromString(classID),code,title,description);
		if (!classGroup.isValid()) {
			model.put("classGroup", classGroup);
			model.put("err", "Complete all the fields");
			return "class-form";
		}
		classService.save(classGroup);
		return "redirect:/classes-list";
	}
	
	/**
	 * Delete a class
	 * @param id of the class
	 * @return not found if is there an error
	 */
	@GetMapping("/class-delete/{id}")
	public String deleteClass(@PathVariable(value = "id") String id) {
		ClassGroup classGroup = null;
		if (id == null || id.isEmpty() ) {
			return "redirect:/not-found";
		}
		classGroup = classService.findOne(UUID.fromString(id));
		if (classGroup == null ) {
			return "redirect:/not-found";
		}
		classService.delete(UUID.fromString(id));
		return "redirect:/classes-list";
	}

	/**
	 * Show a view with the students of a class group
	 * 
	 * @param id    The id of a class group
	 * @param model The object class group
	 * @return a view with the students of a class group
	 */
	@GetMapping("/students-of-class/{id}")
	public String studentsOfClass(@PathVariable(value = "id") String id, Map<String, Object> model) {
		ClassGroup classGroup = null;
		if (id == null || id.isEmpty()) {
			return "redirect:/not-found";
		}
		classGroup = classService.findOne(UUID.fromString(id));
		if (classGroup == null) {
			return "redirect:/not-found";
		}
		model.put("classGroup", classGroup);
		return "students-of-class";
	}

	/**
	 * Show a view to add students to a specific class
	 * 
	 * @param id    The id of a class group
	 * @param model The object class group
	 * @return view to add students to a specific class
	 */
	@GetMapping("/add-students-to-class/{id}")
	public String addStudentsToClass(@PathVariable(value = "id") String id, Map<String, Object> model) {
		ClassGroup classGroup = null;
		if (id == null || id.isEmpty()) {
			return "redirect:/not-found";
		}
		classGroup = classService.findOne(UUID.fromString(id));
		if (classGroup == null) {
			return "redirect:/not-found";
		}
		model.put("classGroup", classGroup);
		model.put("students", classService.findAllStudents(UUID.fromString(id)));
		return "add-student-class";
	}

	/**
	 * Add students to a class
	 * 
	 * @param id class' id
	 * @param model
	 * @param studentsIds
	 * @return 
	 */
	@PostMapping("/add-students-to-class/{id}")
	@ResponseBody
	public List<String> addStudentsToClass(@PathVariable(value = "id") String id, Map<String, Object> model,
			@RequestBody List<String> studentsIds) {
		try {
			classService.addStudents(UUID.fromString(id), studentsIds);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return studentsIds;
	}
	/**
	 * Delete a student of a class
	 * @param studentID 
	 * @param classID
	 * @return
	 */
	@GetMapping("/delete-student-of-class/{studentID}/{classID}")
	public String deleteStudentOfClass(@PathVariable(value = "studentID") String studentID,
			@PathVariable(value = "classID") String classID) {
		try {
			if (studentID == null || studentID.isEmpty() || classID == null || classID.isEmpty()) {
			return "redirect:/not-found";
		}
			classService.deleteStudentOfClass(UUID.fromString(classID), UUID.fromString(studentID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/students-of-class/{classID}";
	}
	/**
	 * The 404 view
	 * @return not found
	 */
	@GetMapping("/not-found")
	public String notFound() {
		return "not-found";
	}
}
