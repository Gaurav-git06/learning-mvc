package com.springBoot.level2.learingmvc.controller;


import com.springBoot.level2.learingmvc.dto.EmployeeDTO;
import com.springBoot.level2.learingmvc.entities.EmployeeEntity;
import com.springBoot.level2.learingmvc.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<EmployeeDTO> getEmployeeId(@PathVariable(name = "employeeId") Long id){
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeId(id);
//        if(employeeDTO == null) return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(employeeDTO); // Not using this line because here we are using Optional EmployeeDTO
        return employeeDTO.map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeeById(@RequestParam(required = false) Integer age,
                                     @RequestParam(required = false) String sortBy){
//        return employeeService.getAllEmployeeById(); // -> Here also we added Response Entity so we had to make changes in the body.
        return ResponseEntity.ok(employeeService.getAllEmployeeById());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveNewEmployeeEntity(@RequestBody EmployeeDTO inputEmployee){
//        return employeeService.saveNewEmployeeEntity(inputEmployee); -> After adding Response Entity here we need to change
        EmployeeDTO savedNewEmployee = employeeService.saveNewEmployeeEntity(inputEmployee);
        return new ResponseEntity<>(savedNewEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@PathVariable Long employeeId, @RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId, employeeDTO));
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId){
//        return employeeService.deleteEmployeeById( employeeId);
        boolean gotDeleted = employeeService.deleteEmployeeById( employeeId);
        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@PathVariable Long employeeId, @RequestBody Map<String,Object> updates){
//        return employeeService.updatePartialEmployeeById(employeeId, updates);
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeId, updates);
        if(employeeDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }

}
