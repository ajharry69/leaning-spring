package co.ke.xently.learnspring.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

//import javax.persistence.GeneratedValue
//import javax.persistence.Entity
//import javax.persistence.Id

@Entity
data class Employee(
    val name: String? = null,
    val role: String? = null,
    @Id
    @GeneratedValue
    val id: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (id != other.id) return false
        if (name != other.name) return false
        if (role != other.role) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (role?.hashCode() ?: 0)
        return result
    }
}