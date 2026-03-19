package com.springBoot.level2.learingmvc.services;

import com.springBoot.level2.learingmvc.dto.EmployeeDTO;
import com.springBoot.level2.learingmvc.entities.EmployeeEntity;
import com.springBoot.level2.learingmvc.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


//    public EmployeeEntity getEmployeeId(Long id) {
//        return employeeRepository.findById(id).orElse(null);
//
//    }

//    public EmployeeDTO getEmployeeId(Long id) {
//        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
////        return new EmployeeDTO(employeeEntity.getId(), employeeEntity.getName(), employeeEntity.getEmail(), employeeEntity.getAge(), ...);
//        ModelMapper modelMapper = new ModelMapper();
//        return modelMapper.map(employeeEntity,EmployeeDTO.class);
//
//    }

        public EmployeeDTO getEmployeeId(Long id) {
            EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
//            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(employeeEntity,EmployeeDTO.class);
        }




    public List<EmployeeDTO> getAllEmployeeById() {
//        return employeeRepository.findAll();
        List<EmployeeEntity> employeeEntity =  employeeRepository.findAll();
        return employeeEntity
                .stream()
                .map(employeeEntity1 -> modelMapper.map(employeeEntity1, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO saveNewEmployeeEntity(EmployeeDTO inputEmployee) {
            //to log something
            EmployeeEntity convertDtoToEntity = modelMapper.map(inputEmployee,EmployeeEntity.class);
        EmployeeEntity employeeEntity =  employeeRepository.save(convertDtoToEntity);
        return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }
}
