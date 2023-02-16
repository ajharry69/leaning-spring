package co.ke.xently.learnspring.features.orders.models

import co.ke.xently.learnspring.features.orders.exceptions.InvalidOrderStatusActionException
import co.ke.xently.learnspring.features.orders.exceptions.InvalidOrderStatusTransitionException
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity(name = "CUSTOMER_ORDERS")
data class Order(
    val description: String? = null,
    @Id
    @GeneratedValue
    val id: Long = -1,
) {
    val number: String
        get() = "$id"
    var status: Status = Status.Received
        set(value) {
            if (status != value && value !in acceptableStatusTransitions) {
                throw InvalidOrderStatusTransitionException(this, value)
            }
            field = value
        }

    val acceptableStatusTransitions get() = STATUS_PIPELINE[status] ?: emptyList()

    enum class Status(val action: String, val description: String) {
        Received("receive", "received"),
        Confirmed("confirm", "confirmed"),
        Cancelled("cancel", "cancelled"),
        Dispatched("dispatch", "dispatched");

        companion object {
            private val ACTION_STATUS_MAP = buildMap {
                for (status in values()) {
                    this[status.action] = status
                }
            }

            fun fromAction(action: String) =
                ACTION_STATUS_MAP[action] ?: throw InvalidOrderStatusActionException(action, ACTION_STATUS_MAP.keys)
        }
    }

    companion object {
        private val STATUS_PIPELINE = mapOf(
            Status.Received to listOf(Status.Confirmed, Status.Cancelled),
            Status.Confirmed to listOf(Status.Dispatched),
        )
    }
}
