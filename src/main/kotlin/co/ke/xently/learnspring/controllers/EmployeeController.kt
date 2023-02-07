package co.ke.xently.learnspring.controllers

import co.ke.xently.learnspring.exceptions.EmployeeNotFoundException
import co.ke.xently.learnspring.models.Employee
import co.ke.xently.learnspring.repositories.EmployeeRepository
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.web.bind.annotation.*

@RestController
class EmployeeController constructor(private val repository: EmployeeRepository) {
    @GetMapping("/employees")
    fun get(): List<Employee> {
        return repository.findAll()
    }

    @GetMapping("/employees/{id}")
    fun get(@PathVariable id: Long): EntityModel<Employee> {
//        return repository.getReferenceById(id)
        val employee = repository.findById(id).orElseThrow {
            EmployeeNotFoundException(id)
        }
        return EntityModel.of(
            employee,
            linkTo(methodOn(EmployeeController::class.java).get(id)).withSelfRel(),
            linkTo(methodOn(EmployeeController::class.java).get()).withRel("employees"),
        )
    }

    @PostMapping("/employees")
    fun add(@RequestBody employee: Employee): Employee {
        return repository.save(employee)
    }

    @PutMapping("/employees/{id}")
    fun update(@RequestBody employee: Employee, @PathVariable id: Long): Employee {
        return repository.findById(id).map {
            repository.save(employee.copy(id = id))
        }.orElseGet {
            repository.save(employee.copy(id = id))
        }
    }

    @DeleteMapping("/employees/{id}")
    fun remove(@PathVariable id: Long) {
        repository.deleteById(id)
    }
}