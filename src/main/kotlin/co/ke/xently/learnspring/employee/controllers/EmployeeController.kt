package co.ke.xently.learnspring.employee.controllers

import co.ke.xently.learnspring.employee.EmployeeModelAssembler
import co.ke.xently.learnspring.employee.exceptions.EmployeeNotFoundException
import co.ke.xently.learnspring.employee.models.Employee
import co.ke.xently.learnspring.employee.repositories.EmployeeRepository
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
class EmployeeController constructor(
    private val repository: EmployeeRepository,
    private val assembler: EmployeeModelAssembler,
) {

    @GetMapping("/employees")
    fun get(): CollectionModel<EntityModel<Employee>> {
        val entities = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList())
        return CollectionModel.of(entities, linkTo(methodOn(this::class.java).get()).withSelfRel())
    }

    @GetMapping("/employees/{id}")
    fun get(@PathVariable id: Long): EntityModel<Employee> {
//        return repository.getReferenceById(id)
        val employee = repository.findById(id).orElseThrow {
            EmployeeNotFoundException(id)
        }
        return assembler.toModel(employee)
    }

    @PostMapping("/employees")
    fun add(@RequestBody employee: Employee) = repository.save(employee).let(assembler::toModel).run {
        ResponseEntity.created(getRequiredLink(IanaLinkRelations.SELF).toUri()).body(this)
    }

    @PutMapping("/employees/{id}")
    fun update(@RequestBody employee: Employee, @PathVariable id: Long) = repository.findById(id).map {
        repository.save(employee.copy(id = id))
    }.orElseGet {
        repository.save(employee.copy(id = id))
    }.let(assembler::toModel).run {
        ResponseEntity.created(getRequiredLink(IanaLinkRelations.SELF).toUri()).body(this)
    }

    @DeleteMapping("/employees/{id}")
    fun remove(@PathVariable id: Long): ResponseEntity<*> {
        repository.deleteById(id)
        return ResponseEntity.noContent().build<Nothing>()
    }
}