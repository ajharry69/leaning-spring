package co.ke.xently.learnspring.features.orders.exceptions

class InvalidOrderStatusActionException(action: String, supportedActions: Set<String>) : RuntimeException(
    """"$action" is an invalid order (status) action! ${
        supportedActions.joinToString(prefix = "\"", postfix = "\"")
    } are the only supported order (status) actions."""
)