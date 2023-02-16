package co.ke.xently.learnspring.features.orders.models

import co.ke.xently.learnspring.features.orders.exceptions.InvalidOrderStatusActionException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class OrderStatusTest {
    @Test
    fun `fromAction raises an exception for an unsupported action`() {
        assertThrows(InvalidOrderStatusActionException::class.java) {
            Order.Status.fromAction("unknown/unsupported")
        }
    }

    @Test
    fun `fromAction returns status matching action`() {
        assertEquals(Order.Status.Confirmed, Order.Status.fromAction("confirm"))
    }
}