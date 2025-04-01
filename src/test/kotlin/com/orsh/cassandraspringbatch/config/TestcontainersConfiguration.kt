package com.orsh.cassandraspringbatch.config

import com.orsh.cassandraspringbatch.util.Constants.CassandraTestcontainer.IMAGE_NAME
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.cassandra.CassandraContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
@EnableAutoConfiguration
class TestcontainersConfiguration(
	private val properties: CassandraProperties
) {

	//	companion object {
	@Bean
	fun cassandraContainer(): CassandraContainer =
		CassandraContainer(DockerImageName.parse(IMAGE_NAME))
//				.withCreateContainerCmdModifier { cmd ->
//					cmd.withPortBindings(
//						PortBinding(Ports.Binding.bindPort(9042),
//							ExposedPort(9042))
//					)
//				}
			.apply {
				logger.info("Starting Cassandra container..")
				start()
				logger.info("Cassandra container started")
				logger.info("""
						Port: $firstMappedPort,
						LocalDatacenter: $localDatacenter
						ContactPoint: $contactPoint,
					"""
				)
			}

		@DynamicPropertySource
////		@JvmStatic
		fun overrideProperties(registry: DynamicPropertyRegistry, cassandraContainer: CassandraContainer) {
			registry.add("spring.cassandra.port", cassandraContainer::getFirstMappedPort)
			registry.add("spring.cassandra.contact-points", cassandraContainer::getContactPoint)
			registry.add("spring.cassandra.local-datacenter", cassandraContainer::getLocalDatacenter)
			registry.add("spring.cassandra.local-datacenter", cassandraContainer::getLocalDatacenter)
			registry.add("spring.cassandra.username", cassandraContainer::getUsername)
			registry.add("spring.cassandra.password", cassandraContainer::getPassword)
		}


	val logger: Logger = LoggerFactory.getLogger(this::class.java)
//	}
}