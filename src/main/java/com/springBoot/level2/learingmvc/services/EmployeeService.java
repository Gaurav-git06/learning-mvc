package com.springBoot.level2.learingmvc.services;

import com.springBoot.level2.learingmvc.dto.EmployeeDTO;
import com.springBoot.level2.learingmvc.entities.EmployeeEntity;
import com.springBoot.level2.learingmvc.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        public Optional<EmployeeDTO> getEmployeeId(Long id) {
            Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
//            ModelMapper modelMapper = new ModelMapper();
//            return modelMapper.map(employeeEntity,EmployeeDTO.class);
            return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1,EmployeeDTO.class));
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

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
            EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
            employeeEntity.setId(employeeId);
            EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
            return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);

    }

    public Boolean deleteEmployeeById(Long employeeId) {
//            boolean exists = employeeRepository.existsById(employeeId);
        boolean exists = isExistsById(employeeId);
            if(!exists){
                return false;
            }
            employeeRepository.deleteById(employeeId);
            return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
            boolean exists = isExistsById(employeeId);
            if(!exists) return null;
            EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
            updates.forEach((field, value)-> {
                Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, field);
                fieldToBeUpdated.setAccessible(true);
                ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
            });
            return modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);
    }

    public boolean isExistsById(Long employeeId){
            return employeeRepository.existsById(employeeId);
    }
}
