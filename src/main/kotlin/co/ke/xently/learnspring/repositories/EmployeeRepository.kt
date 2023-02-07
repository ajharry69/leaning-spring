package co.ke.xently.learnspring.repositories

import co.ke.xently.learnspring.models.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository: JpaRepository<Employee, Long>