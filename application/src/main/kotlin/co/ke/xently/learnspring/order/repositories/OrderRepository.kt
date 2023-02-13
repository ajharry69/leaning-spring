package co.ke.xently.learnspring.order.repositories

import co.ke.xently.learnspring.order.models.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Long>