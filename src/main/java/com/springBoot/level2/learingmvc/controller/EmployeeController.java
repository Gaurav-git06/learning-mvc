package com.springBoot.level2.learingmvc.controller;


import com.springBoot.level2.learingmvc.dto.EmployeeDTO;
import com.springBoot.level2.learingmvc.entities.EmployeeEntity;
import com.springBoot.level2.learingmvc.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

//    @GetMapping(path = "/message")
//    public String myMessage(){
//        return "my message is rishabh is my beta";
//    }

//    private final EmployeeRepository employeeRepository;
//
//    public EmployeeController(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


//    @GetMapping(path = "/{employeeId}")
//    public EmployeeDTO getEmployeeId(@PathVariable Long employeeId){
//        return new EmployeeDTO(employeeId,"abc", "email.com", 12, LocalDate.of(2020,1,1),true);
//    }

//    @GetMapping(path = "/{employeeId}")
//    public EmployeeEntity getEmployeeId(@PathVariable(name = "employeeId") Long employeeId){
//        return employeeRepository.findById(employeeId).orElse(null);
//    }

//    @GetMapping(path = "/{employeeId}")
//    public EmployeeEntity getEmployeeId(@PathVariable(name = "employeeId") Long id){
//        return employeeService.getEmployeeId(id); // This is related to commented method in service class
//    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDTO getEmployeeId(@PathVariable(name = "employeeId") Long id){
        return employeeService.getEmployeeId(id);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployeeById(@RequestParam(required = false) Integer age,
                                     @RequestParam(required = false) String sortBy){
        return employeeService.getAllEmployeeById();
    }

    @PostMapping
    public EmployeeDTO saveNewEmployeeEntity(@RequestBody EmployeeDTO inputEmployee){
        return employeeService.saveNewEmployeeEntity(inputEmployee);
    }
}
