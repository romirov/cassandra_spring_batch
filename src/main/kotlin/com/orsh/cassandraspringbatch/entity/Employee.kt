package com.orsh.cassandraspringbatch.entity

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table(value = "employee", keyspace = "#{keyspaceName}")
data class Employee (
	@PrimaryKey("id")
	val id: UUID,
	@Column("inn")
	val inn: String,
	@Column("companyid")
	val companyId: UUID,
	@Column("firstname")
	val firstName: String,
	@Column("lastname")
	val lastName: String
)
