package co.ke.xently.learnspring.order.models

import co.ke.xently.learnspring.order.exceptions.InvalidOrderStatusActionException
import org.junit.jupiter.api.Assertions.*
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