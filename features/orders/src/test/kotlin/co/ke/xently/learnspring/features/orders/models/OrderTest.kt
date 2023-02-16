package co.ke.xently.learnspring.features.orders.models

import co.ke.xently.learnspring.features.orders.exceptions.InvalidOrderStatusTransitionException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class OrderTest {

    @Test
    fun `getStatus returns Received by default`() {
        assertEquals(Order.Status.Received, Order().status)
    }

    @Test
    fun `setStatus can transition status from Received to Confirmed`() {
        assertEquals(
            Order.Status.Confirmed,
            Order().apply {
                status = Order.Status.Confirmed
            }.status,
        )
    }

    @Test
    fun `setStatus can transition status from Received to Cancelled`() {
        assertEquals(
            Order.Status.Cancelled,
            Order().apply {
                status = Order.Status.Cancelled
            }.status,
        )
    }

    @Test
    fun `setStatus can transition status from Confirmed to Dispatched`() {
        assertEquals(
            Order.Status.Dispatched,
            Order().apply {
                status = Order.Status.Confirmed
                status = Order.Status.Dispatched
            }.status,
        )
    }

    @Test
    fun `setStatus throws an exception if transitioning to an unexpected order status`() {
        assertThrows(InvalidOrderStatusTransitionException::class.java) {
            Order().apply {
                status = Order.Status.Dispatched
            }
        }
    }

    @Test
    fun `setStatus retains order status if the new status is the same one that is currently set`() {
        assertEquals(
            Order.Status.Received,
            Order().apply {
                status = Order.Status.Received
            }.status,
        )
    }

    @Test
    fun `setStatus throws an exception if transitioning to an unacceptable status`() {
        assertThrows(InvalidOrderStatusTransitionException::class.java) {
            Order().apply {
                status = Order.Status.Confirmed
                status = Order.Status.Cancelled
            }.status
        }
    }
}