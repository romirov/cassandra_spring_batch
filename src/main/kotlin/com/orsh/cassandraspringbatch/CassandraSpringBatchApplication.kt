package com.orsh.cassandraspringbatch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CassandraSpringBatchApplication

fun main(args: Array<String>) {
	runApplication<CassandraSpringBatchApplication>(*args)
}
