package co.ke.xently.learnspring.features.orders.controllers

import co.ke.xently.learnspring.features.orders.OrderModelAssembler
import co.ke.xently.learnspring.features.orders.exceptions.OrderNotFoundException
import co.ke.xently.learnspring.features.orders.models.Order
import co.ke.xently.learnspring.features.orders.repositories.OrderRepository
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/orders")
class OrderController constructor(
    private val repository: OrderRepository,
    private val assembler: OrderModelAssembler,
) {

    @GetMapping
    fun get(): CollectionModel<EntityModel<Order>> {
        val entities = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList())
        return CollectionModel.of(entities, linkTo(methodOn(this::class.java).get()).withSelfRel())
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): EntityModel<Order> {
        val order = repository.findById(id).orElseThrow {
            OrderNotFoundException(id)
        }
        return assembler.toModel(order)
    }

    @PostMapping
    fun add(@RequestBody order: Order) =
        repository.save(order.apply { status = Order.Status.Received }).let(assembler::toModel).run {
            ResponseEntity.created(getRequiredLink(IanaLinkRelations.SELF).toUri()).body(this)
        }

    @PutMapping("/{id}")
    fun update(@RequestBody order: Order, @PathVariable id: Long) = repository.findById(id).map {
        repository.save(order.copy(id = id))
    }.orElseGet {
        repository.save(order.copy(id = id))
    }.let(assembler::toModel).run {
        ResponseEntity.created(getRequiredLink(IanaLinkRelations.SELF).toUri()).body(this)
    }

    @DeleteMapping("/{id}")
    fun remove(@PathVariable id: Long): ResponseEntity<*> {
        repository.deleteById(id)
        return ResponseEntity.noContent().build<Nothing>()
    }

    @RequestMapping("/{id}/{action}", method = [RequestMethod.GET, RequestMethod.PATCH])
    fun changeStatus(@PathVariable id: Long, @PathVariable action: String): ResponseEntity<EntityModel<Order>> {
        var isStatusSameAsCurrent = false
        return repository.findById(id).orElseThrow {
            OrderNotFoundException(id)
        }.let {
            val newStatus = Order.Status.fromAction(action)
            if (it.status == newStatus) {
                isStatusSameAsCurrent = true
                it
            } else {
                repository.save(it.apply { status = newStatus })
            }
        }.let(assembler::toModel).run {
            if (isStatusSameAsCurrent) {
                ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
            } else {
                ResponseEntity.accepted()
            }.location(getRequiredLink(IanaLinkRelations.SELF).toUri()).body(this)
        }
    }
}