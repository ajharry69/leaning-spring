package co.ke.xently.learnspring.employee.repositories

import co.ke.xently.learnspring.employee.models.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository: JpaRepository<Employee, Long>