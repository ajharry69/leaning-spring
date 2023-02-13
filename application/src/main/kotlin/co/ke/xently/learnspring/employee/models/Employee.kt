package co.ke.xently.learnspring.employee.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

//import javax.persistence.GeneratedValue
//import javax.persistence.Entity
//import javax.persistence.Id

@Entity
data class Employee(
    var firstName: String? = null,
    var lastName: String? = null,
    val role: String? = null,
    @Id
    @GeneratedValue
    val id: Long? = null,
) {
    var name: String? = null
        set(value) {
            value?.split(' ', limit = 2)?.let {
                firstName = it.getOrElse(0) { "" }.ifBlank { firstName }
                lastName = it.getOrElse(1) { "" }.ifBlank { lastName }
            }
            field = value
        }
        get() = listOfNotNull(firstName, lastName).joinToString(separator = " ").ifBlank { null }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (role != other.role) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (role?.hashCode() ?: 0)
        return result
    }
}