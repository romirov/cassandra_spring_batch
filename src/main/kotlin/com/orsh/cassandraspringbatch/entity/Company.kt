package com.orsh.cassandraspringbatch.entity

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table(value = "company", keyspace = "#{keyspaceName}")
data class Company (
	@PrimaryKey
	val id: UUID,
	@Column("name")
	val name: String,
	@Column("address")
	val address: Address
)