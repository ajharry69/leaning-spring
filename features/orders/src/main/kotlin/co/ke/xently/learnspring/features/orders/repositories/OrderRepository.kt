package co.ke.xently.learnspring.features.orders.repositories

import co.ke.xently.learnspring.features.orders.models.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Long>