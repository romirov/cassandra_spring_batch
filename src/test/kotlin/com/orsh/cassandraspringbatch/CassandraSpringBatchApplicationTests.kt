package com.orsh.cassandraspringbatch

import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest(
	classes = [CassandraDataAutoConfiguration::class],
)
class CassandraSpringBatchApplicationTests {

	@Test
	fun contextLoads() {
	}

}
