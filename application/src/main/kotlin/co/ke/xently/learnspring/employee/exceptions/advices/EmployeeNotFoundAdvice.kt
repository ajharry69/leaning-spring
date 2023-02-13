package co.ke.xently.learnspring.employee.exceptions.advices

import co.ke.xently.learnspring.employee.exceptions.EmployeeNotFoundException
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.mediatype.problem.Problem
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class EmployeeNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException::class)
    fun handler(exception: EmployeeNotFoundException) = ResponseEntity.status(HttpStatus.NOT_FOUND)
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(
            Problem.create().withDetail(exception.localizedMessage).withProperties(mapOf("code" to "not_found"))
                .withStatus(HttpStatus.NOT_FOUND)
        )
}