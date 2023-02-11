package co.ke.xently.learnspring.order.controllers

import co.ke.xently.learnspring.order.OrderModelAssembler
import co.ke.xently.learnspring.order.exceptions.OrderNotFoundException
import co.ke.xently.learnspring.order.models.Order
import co.ke.xently.learnspring.order.repositories.OrderRepository
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
class OrderController constructor(
    private val repository: OrderRepository,
    private val assembler: OrderModelAssembler,
) {

    @GetMapping("/orders")
    fun get(): CollectionModel<EntityModel<Order>> {
        val entities = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList())
        return CollectionModel.of(entities, linkTo(methodOn(this::class.java).get()).withSelfRel())
    }

    @GetMapping("/orders/{id}")
    fun get(@PathVariable id: Long): EntityModel<Order> {
        val order = repository.findById(id).orElseThrow {
            OrderNotFoundException(id)
        }
        return assembler.toModel(order)
    }

    @PostMapping("/orders")
    fun add(@RequestBody order: Order) =
        repository.save(order.apply { status = Order.Status.Received }).let(assembler::toModel).run {
            ResponseEntity.created(getRequiredLink(IanaLinkRelations.SELF).toUri()).body(this)
        }

    @PutMapping("/orders/{id}")
    fun update(@RequestBody order: Order, @PathVariable id: Long) = repository.findById(id).map {
        repository.save(order.copy(id = id))
    }.orElseGet {
        repository.save(order.copy(id = id))
    }.let(assembler::toModel).run {
        ResponseEntity.created(getRequiredLink(IanaLinkRelations.SELF).toUri()).body(this)
    }

    @DeleteMapping("/orders/{id}")
    fun remove(@PathVariable id: Long): ResponseEntity<*> {
        repository.deleteById(id)
        return ResponseEntity.noContent().build<Nothing>()
    }

    @GetMapping("/orders/{id}/{action}")
    @PatchMapping("/orders/{id}/{action}")
    fun changeStatus(@PathVariable id: Long, @PathVariable action: String) =
        repository.findById(id).orElseThrow {
            OrderNotFoundException(id)
        }.let {
            repository.save(it.apply { status = Order.Status.fromAction(action) })
        }.let(assembler::toModel).run {
            ResponseEntity.accepted().location(getRequiredLink(IanaLinkRelations.SELF).toUri()).body(this)
        }
}