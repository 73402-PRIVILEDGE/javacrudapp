
package com._2.javacrudapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("students", repository.findAll());
        return "students";
    }

    @PostMapping("/students")
    public String addStudent(@RequestParam String name,
                             @RequestParam String email,
                             RedirectAttributes redirectAttributes) {
        repository.save(new Student(name, email));
        redirectAttributes.addFlashAttribute("message", "Student added successfully!");
        return "redirect:/";
    }

    @PostMapping("/students/{id}/update")
    public String updateStudent(@PathVariable Long id,
                                @RequestParam String name,
                                @RequestParam String email) {
        repository.findById(id).ifPresent(student -> {
            student.setName(name);
            student.setEmail(email);
            repository.save(student);
        });
        return "redirect:/";
    }

    @PostMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}
