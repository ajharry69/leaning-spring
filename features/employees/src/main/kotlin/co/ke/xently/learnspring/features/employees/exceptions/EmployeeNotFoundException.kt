package co.ke.xently.learnspring.features.employees.exceptions

class EmployeeNotFoundException(id: Long) : RuntimeException("Employee with the ID '${id}' was not found!")