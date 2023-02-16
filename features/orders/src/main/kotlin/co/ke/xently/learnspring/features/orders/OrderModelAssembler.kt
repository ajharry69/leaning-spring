package co.ke.xently.learnspring.features.orders

import co.ke.xently.learnspring.features.orders.controllers.OrderController
import co.ke.xently.learnspring.features.orders.models.Order
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.stereotype.Component

@Component
class OrderModelAssembler : RepresentationModelAssembler<Order, EntityModel<Order>> {
    override fun toModel(entity: Order): EntityModel<Order> {
        return EntityModel.of(
            entity,
            linkTo(methodOn(OrderController::class.java).get(entity.id)).withSelfRel(),
            linkTo(methodOn(OrderController::class.java).get()).withRel("orders"),
        ).apply {
            for (transitionStatus in entity.acceptableStatusTransitions) {
                add(
                    linkTo(
                        methodOn(OrderController::class.java).changeStatus(
                            entity.id,
                            transitionStatus.action,
                        )
                    ).withRel(transitionStatus.action)
                )
            }
        }
    }
}