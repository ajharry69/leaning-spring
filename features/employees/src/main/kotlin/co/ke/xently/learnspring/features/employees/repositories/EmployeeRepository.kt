package co.ke.xently.learnspring.features.employees.repositories

import co.ke.xently.learnspring.features.employees.models.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository: JpaRepository<Employee, Long>