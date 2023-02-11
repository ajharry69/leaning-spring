package co.ke.xently.learnspring

import co.ke.xently.learnspring.employee.models.Employee
import co.ke.xently.learnspring.employee.repositories.EmployeeRepository
import co.ke.xently.learnspring.order.models.Order
import co.ke.xently.learnspring.order.repositories.OrderRepository
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
                    append(orderRepository.save(Order(description = "Please deliver the order to Westlands, Nairobi, Kenya")))
                }
            )
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(LoadDatabase::class.java)
    }
}