package co.ke.xently.learnspring.features.orders.exceptions.advices

import co.ke.xently.learnspring.features.orders.exceptions.OrderNotFoundException
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.mediatype.problem.Problem
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class OrderNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(OrderNotFoundException::class)
    fun handler(exception: OrderNotFoundException) = ResponseEntity.status(HttpStatus.NOT_FOUND)
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(Problem.create().withTitle("Title").withDetail(exception.localizedMessage))
}