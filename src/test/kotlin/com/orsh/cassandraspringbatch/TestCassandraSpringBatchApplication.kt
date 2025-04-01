package com.orsh.cassandraspringbatch

import com.orsh.cassandraspringbatch.config.TestcontainersConfiguration
import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<CassandraSpringBatchApplication>().with(TestcontainersConfiguration::class).run(*args)
}
