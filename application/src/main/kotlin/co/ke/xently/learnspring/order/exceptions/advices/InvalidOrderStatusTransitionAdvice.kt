package co.ke.xently.learnspring.order.exceptions.advices

import co.ke.xently.learnspring.order.exceptions.InvalidOrderStatusTransitionException
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.mediatype.problem.Problem
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class InvalidOrderStatusTransitionAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidOrderStatusTransitionException::class)
    fun handler(exception: InvalidOrderStatusTransitionException) = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(Problem.create().withDetail(exception.localizedMessage))
}