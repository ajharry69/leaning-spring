package co.ke.xently.learnspring.order.exceptions

class OrderNotFoundException(id: Long) : RuntimeException("Order with the ID '${id}' was not found!")