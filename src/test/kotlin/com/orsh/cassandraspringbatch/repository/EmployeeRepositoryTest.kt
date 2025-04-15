package com.orsh.cassandraspringbatch.repository

import com.ocadotechnology.gembus.test.Arranger.some
import com.orsh.cassandraspringbatch.abstractions.AbstractCassandraTest
import com.orsh.cassandraspringbatch.entity.Employee
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
		val employeeId= employee.id
		with(employee) {
			logger.info("Employee for saveTest: $employee")
			repo.save(employee)
		}

		val savedEmployee = repo.findById(employeeId).get()
		assertEquals(employee, savedEmployee)
	}

	@Test
	fun saveByQueryTest() {
		val employee = some(Employee::class.java)
		val employeeId= employee.id
		with(employee) {
			logger.info("Employee for saveByQueryTest: $employee")
			repo.saveByQuery(id, inn, companyId, firstName, lastName)
		}

		val savedEmployee = repo.findById(employeeId).get()
		assertEquals(employee, savedEmployee)
	}



	private companion object{
		val logger: Logger = LoggerFactory.getLogger(this::class.java)
	}
}