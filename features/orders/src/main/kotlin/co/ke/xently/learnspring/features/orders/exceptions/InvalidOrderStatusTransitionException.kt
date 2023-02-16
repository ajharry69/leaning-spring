package co.ke.xently.learnspring.features.orders.exceptions

import co.ke.xently.learnspring.features.orders.models.Order

class InvalidOrderStatusTransitionException(order: Order, newOrderStatus: Order.Status) : RuntimeException(
    if (order.acceptableStatusTransitions.isEmpty()) {
        "An order in a terminal status (${order.status.description}) cannot be transitioned to ${newOrderStatus.description}."
    } else {
        val acceptableStatusTransitions = order.acceptableStatusTransitions
        val showPlural = acceptableStatusTransitions.size > 1
        """A "${order.status.description}" order cannot be transitioned to "${newOrderStatus.description}". Acceptable status transition${if (showPlural) "s" else ""} for order #${order.number} ${if (showPlural) "are" else "is"}: ${
            acceptableStatusTransitions.joinToString(
                "\", \"",
                "\"",
                "\"",
            ) { it.description }
        }."""
    }
)