package co.ke.xently.learnspring.order.exceptions

import co.ke.xently.learnspring.order.models.Order

class InvalidOrderStatusTransitionException(order: Order) : RuntimeException(
    "Invalid order status transition! The order #${order.number} can only be transitioned to: ${
        order.acceptableStatusTransitions.joinToString()
    }."
)