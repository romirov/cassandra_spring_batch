package com.orsh.cassandraspringbatch

import org.springframework.boot.fromApplication


fun main(args: Array<String>) {
	fromApplication<CassandraSpringBatchApplication>().run(*args)
}
