package co.ke.xently.learnspring.employee.models

import co.ke.xently.learnspring.employee.models.Employee
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class EmployeeTest {
    @Test
    fun `get name returns null by default`() {
        assertNull(Employee().name)
    }

    @Test
    fun `get name returns null if first name and last name is null`() {
        assertNull(Employee().name)
    }

    @Test
    fun `get name only returns first name if the last name is null`() {
        assertEquals("John", Employee(firstName = "John").name)
    }

    @Test
    fun `get name only returns last name if the first name is null`() {
        assertEquals("Doe", Employee(lastName = "Doe").name)
    }

    @Test
    fun `get name returns first and last name if neither is null`() {
        assertEquals("John Doe", Employee(firstName = "John", lastName = "Doe").name)
    }

    @Test
    fun `get name returns first and last name if neither is null and either contains more than 1 name`() {
        assertEquals("Mr. John Doe", Employee(firstName = "Mr. John", lastName = "Doe").name)
    }

    @Test
    fun `set name takes precedence over values passed to the first and last name`() {
        val employee = Employee(firstName = "Mr. John", lastName = "Doe").apply {
            name = "My Name"
        }
        assertEquals("My Name", employee.name)
        assertEquals("My", employee.firstName)
        assertEquals("Name", employee.lastName)
    }

    @Test
    fun `set name does not override the value of first name if name is set to a blank value`() {
        val employee = Employee(firstName = "John", lastName = "Doe").apply {
            name = "  "
        }
        assertEquals("John Doe", employee.name)
        assertEquals("John", employee.firstName)
        assertEquals("Doe", employee.lastName)
    }

    @Test
    fun `set name does not override the value of last name if only one name is passed`() {
        val employee = Employee(firstName = "My", lastName = "Doe").apply {
            name = "John"
        }
        assertEquals("John Doe", employee.name)
        assertEquals("John", employee.firstName)
        assertEquals("Doe", employee.lastName)
    }

    @Test
    fun `set name does not override the value of last name if only one name (with spaces at the end) is passed`() {
        val employee = Employee(firstName = "My", lastName = "Doe").apply {
            name = "John "
        }
        assertEquals("John Doe", employee.name)
        assertEquals("John", employee.firstName)
        assertEquals("Doe", employee.lastName)
    }

    @Test
    fun `set name can be used to set first and last name`() {
        val employee = Employee().apply {
            name = "John Doe"
        }
        assertEquals(employee.firstName, "John")
        assertEquals(employee.lastName, "Doe")
    }

    @Test
    fun `set name with more than 2 words sets the first word as the first name and the rest as the last name`() {
        val employee = Employee().apply {
            name = "Mr. John Doe"
        }
        assertEquals(employee.firstName, "Mr.")
        assertEquals(employee.lastName, "John Doe")
    }
}