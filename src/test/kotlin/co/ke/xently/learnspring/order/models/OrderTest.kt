package co.ke.xently.learnspring.order.models

import co.ke.xently.learnspring.order.exceptions.InvalidOrderStatusTransitionException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

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
    fun `setStatus cannot transition a Cancelled order further - Cancelled is a terminal status`() {
        assertEquals(
            Order.Status.Cancelled,
            Order().apply {
                status = Order.Status.Cancelled
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
    fun `setStatus retains order status if the current status is the same one that is currently set`() {
        assertEquals(
            Order.Status.Received,
            Order().apply {
                status = Order.Status.Received
            }.status,
        )
    }
}