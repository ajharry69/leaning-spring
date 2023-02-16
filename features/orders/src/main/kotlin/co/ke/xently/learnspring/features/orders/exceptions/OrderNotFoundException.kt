package co.ke.xently.learnspring.features.orders.exceptions

class OrderNotFoundException(id: Long) : RuntimeException("Order with the ID '${id}' was not found!")