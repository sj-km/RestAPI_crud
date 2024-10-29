package org.jsp.crud_rest.controller;

import java.util.List;
import java.util.Optional;

import org.jsp.crud_rest.dto.Student;
import org.jsp.crud_rest.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

	@Autowired
	StudentRepository repository;

	// save one record
	@PostMapping("/students")
	public ResponseEntity<Student> addRecord(@RequestBody Student student) {
		student.setPercentage((student.getMaths() + student.getScience() + student.getEnglish()) / 3.0);
		if (student.getPercentage() < 35 || student.getMaths() < 35 || student.getScience() < 35
				|| student.getEnglish() < 35)
			student.setResult("Fail");
		else if (student.getPercentage() < 60)
			student.setResult("SecondClass");
		else if (student.getPercentage() < 85)
			student.setResult("Firstclass");
		else
			student.setResult("Distinction");

		return new ResponseEntity<Student>(repository.save(student), HttpStatus.CREATED);
	}

	// save multiple record
	@PostMapping("/students/many")
	public ResponseEntity<List<Student>> addRecords(@RequestBody List<Student> students) {
		for (Student student : students) {
			student.setPercentage((student.getMaths() + student.getScience() + student.getEnglish()) / 3.0);
			if (student.getPercentage() < 35 || student.getMaths() < 35 || student.getScience() < 35
					|| student.getEnglish() < 35)
				student.setResult("Fail");
			else if (student.getPercentage() < 60)
				student.setResult("SecondClass");
			else if (student.getPercentage() < 85)
				student.setResult("Firstclass");
			else
				student.setResult("Distinction");
		}

		return new ResponseEntity<List<Student>>(repository.saveAll(students), HttpStatus.CREATED);
	}

	// fetch all records
	@GetMapping("/students")
	public ResponseEntity<List<Student>> fetchAll() {
		List<Student> students = repository.findAll();
		if (students.isEmpty())
			return new ResponseEntity<List<Student>>(students, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<Student>>(students, HttpStatus.FOUND);
	}
	
	//fetch by id
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> fetchById(@PathVariable long id){
		Student student = repository.findById(id).orElse(null);
		if(student==null)
			return new ResponseEntity<Student>(student, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<Student>(student, HttpStatus.FOUND);
	}
	
	//fetch by name
	@GetMapping("/students/name/{name}")
	public ResponseEntity<List<Student>> fetchByName(@PathVariable String name){
		List<Student> students =repository.findByName(name);
		if (students.isEmpty())
			return new ResponseEntity<List<Student>>(students, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<Student>>(students, HttpStatus.FOUND);
	}
}
