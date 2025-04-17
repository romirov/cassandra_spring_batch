package com.orsh.cassandraspringbatch.repository

import com.ocadotechnology.gembus.test.Arranger.some
import com.orsh.cassandraspringbatch.abstractions.AbstractCassandraTest
import com.orsh.cassandraspringbatch.entity.Employee
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

class EmployeeRepositoryTest : AbstractCassandraTest() {
	@Autowired
	lateinit var repo: EmployeeRepository

	@Test
	fun saveTest() {
		val employee = some(Employee::class.java)
		val employeeId = employee.id
		logger.info("Employee for saveTest: $employee")
		repo.save(employee)
		val savedEmployee = repo.findById(employeeId).get()
		assertEquals(employee, savedEmployee)
	}

	@Test
	fun saveByQueryTest() {
		val employee = some(Employee::class.java)
		val employeeId = employee.id
		with(employee) {
			logger.info("Employee for saveByQueryTest: $employee")
			repo.saveByQuery(id, inn, companyId, firstName, lastName)
		}
		val savedEmployee = repo.findById(employeeId).get()
		assertEquals(employee, savedEmployee)
	}

	@Test
	fun updateTest() {
		val employee = some(Employee::class.java)
		val employeeId = employee.id
		repo.save(employee)
		val savedEmployee = repo.findById(employeeId).get()
		assertEquals(employee, savedEmployee)

		val updEmployee = some(Employee::class.java).copy(id = employeeId)
		repo.save(updEmployee)
		val savedUpdEmployee = repo.findById(employeeId).get()
		assertEquals(updEmployee, savedUpdEmployee)
		assertEquals(1, repo.findAll().toList().size)
	}

	@AfterEach
	fun cleanUp() {
		repo.deleteAll()
	}

	private companion object {
		val logger: Logger = LoggerFactory.getLogger(this::class.java)
	}
}