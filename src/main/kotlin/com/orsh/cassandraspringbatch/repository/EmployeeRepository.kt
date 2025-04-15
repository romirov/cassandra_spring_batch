package com.orsh.cassandraspringbatch.repository

import com.orsh.cassandraspringbatch.entity.Employee
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmployeeRepository : CrudRepository<Employee, UUID> {

	@Query("INSERT INTO my_keyspace.employee (id,inn,companyid,firstname,lastname) VALUES (:id,:inn,:companyId,:firstName,:lastName)")
	fun saveByQuery(
		id: UUID,
		inn: String,
		companyId: UUID,
		firstName: String,
		lastName: String
	)
}