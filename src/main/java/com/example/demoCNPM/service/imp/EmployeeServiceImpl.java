package com.example.demoCNPM.service.imp;

import com.example.demoCNPM.entity.Employee;
import com.example.demoCNPM.repository.EmployeeRepository;
import com.example.demoCNPM.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
}
