package co.ke.xently.learnspring.employee.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

class EmployeeNotFoundException(id: Long) : RuntimeException("Employee with the ID '${id}' was not found!") {
    @ControllerAdvice
    inner class EmployeeNotFoundAdvice {
        @ResponseBody
        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(EmployeeNotFoundException::class)
        fun handler(exception: EmployeeNotFoundException): String {
            return exception.localizedMessage
        }
    }
}