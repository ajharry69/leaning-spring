package co.ke.xently.learnspring

import co.ke.xently.learnspring.features.employees.models.Employee
import co.ke.xently.learnspring.features.employees.repositories.EmployeeRepository
import co.ke.xently.learnspring.features.orders.models.Order
import co.ke.xently.learnspring.features.orders.repositories.OrderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class LoadDatabase {
    @Bean
    fun initDatabase(repository: EmployeeRepository, orderRepository: OrderRepository): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
            log.info(
                buildString {
                    append("Preloading ")
                    append(repository.save(Employee(firstName = "Bilbo", lastName = "Baggins", role = "burglar")))
                }
            )
            log.info(
                buildString {
                    append("Preloading ")
                    append(repository.save(Employee(firstName = "Frodo", lastName = "Baggins", role = "thief")))
                }
            )
            log.info(
                buildString {
                    append("Preloading ")
                    append(orderRepository.save(Order(description = "Please deliver the order at Westlands, Nairobi, Kenya")))
                }
            )
            log.info(
                buildString {
                    append("Preloading ")
                    append(orderRepository.save(Order(description = "Laptop to be delivered at Lavington, Nairobi, Kenya")))
                }
            )
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(LoadDatabase::class.java)
    }
}