package co.ke.xently.learnspring.features.employees

import co.ke.xently.learnspring.features.employees.controllers.EmployeeController
import co.ke.xently.learnspring.features.employees.models.Employee
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.stereotype.Component

@Component
class EmployeeModelAssembler : RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    override fun toModel(entity: Employee): EntityModel<Employee> {
        return EntityModel.of(
            entity,
            linkTo(methodOn(EmployeeController::class.java).get(entity.id!!)).withSelfRel(),
            linkTo(methodOn(EmployeeController::class.java).get()).withRel("employees"),
        )
    }
}