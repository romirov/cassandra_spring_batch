package com.orsh.cassandraspringbatch.repository

import com.orsh.cassandraspringbatch.entity.Employee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.cassandra.core.CassandraTemplate
import org.springframework.data.cassandra.core.query.Criteria
import org.springframework.data.cassandra.core.query.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class EmployeeCriteriaRepository {
	@Autowired
	lateinit var template: CassandraTemplate

	fun saveBySpecification(employee: Employee): Employee {
		return template.insert<Employee>(employee)
	}

	fun findById(id: UUID): Employee {
		val criteria = Criteria.where("id").`is`(id)
		val query = Query.query(criteria)
		return template.selectOne(query, Employee::class.java)
	}
}