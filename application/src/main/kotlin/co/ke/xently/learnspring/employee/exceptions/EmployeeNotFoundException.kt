package co.ke.xently.learnspring.employee.exceptions

class EmployeeNotFoundException(id: Long) : RuntimeException("Employee with the ID '${id}' was not found!")