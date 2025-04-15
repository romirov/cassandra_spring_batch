package com.orsh.cassandraspringbatch.abstractions

import com.orsh.cassandraspringbatch.config.CassandraConfig
import com.orsh.cassandraspringbatch.util.Constants.CassandraTestcontainer.IMAGE_NAME
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.cassandra.core.CassandraTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.cassandra.CassandraContainer
import org.testcontainers.utility.DockerImageName


@SpringBootTest(classes = [CassandraConfig::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class AbstractCassandraTest {
	@Autowired
	lateinit var template: CassandraTemplate

	@Autowired
	lateinit var properties: CassandraProperties

//	@BeforeAll
//	fun beforeAll() {
//		val script = this::class.java
//			.getResourceAsStream("/data/init.cql")
//			.bufferedReader()
//			.use { it.readText() }
//		logger.info("Executing init script: $script")
//		template.cqlOperations.execute(script)
////		template.cqlOperations.execute("USE ${properties.keyspaceName}")
//	}
//
//		template.cqlOperations.execute(createKeyspace())
//		template.cqlOperations.execute(createCompanyTable())
////		template.cqlOperations.execute(createCompanyIndex())
//		template.cqlOperations.execute(createEmployeeTable())
////		template.cqlOperations.execute(createEmployeeIndex())
//	}
//
//	private fun createKeyspace(): String {
//		val createKeyspace = SpecificationBuilder.createKeyspace(properties.keyspaceName)
//			.with(KeyspaceOption.REPLICATION, KeyspaceAttributes.newSimpleReplication())
//			.with(KeyspaceOption.DURABLE_WRITES, true)
//		// results in CREATE KEYSPACE my_keyspace WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'} AND durable_writes = true
//		val cqlCreateKeyspace = CqlGenerator.toCql(createKeyspace)
//		return cqlCreateKeyspace
//	}
//
//	private fun createCompanyTable(): String {
//		val createTable = SpecificationBuilder.createTable(properties.keyspaceName, "company")
//			.partitionKeyColumn("id", DataTypes.UUID)
//			.column("name", DataTypes.TEXT)
//			.column("address", DataTypes.TEXT)
//
//		val cqlCreateTable = CqlGenerator.toCql(createTable)
//		return cqlCreateTable
//	}
//
//	private fun createCompanyIndex(): String {
//		val createTable = SpecificationBuilder.createIndex()
//			.tableName("company").keys().columnName("id")
//
//		// results in CREATE INDEX ON company (KEYS(id))
//		var cql = CqlGenerator.toCql(createTable)
//		return cql
//	}
//
//	private fun createEmployeeTable(): String {
//		val createTable = SpecificationBuilder.createTable(properties.keyspaceName, "employee")
//			.partitionKeyColumn("id", DataTypes.UUID)
//			.partitionKeyColumn("company_id", DataTypes.UUID)
//			.column("first_name", DataTypes.TEXT)
//			.column("last_name", DataTypes.TEXT)
//
//		val cqlCreateTable = CqlGenerator.toCql(createTable)
//		return cqlCreateTable
//	}
//
//	private fun createEmployeeIndex(): String {
//		val createTable = SpecificationBuilder.createIndex()
//			.tableName("employee").keys().columnName("id")
//
//		// results in CREATE INDEX ON employee (KEYS(id))
//		var cql = CqlGenerator.toCql(createTable)
//		return cql
//	}

	companion object {
		val logger: Logger = LoggerFactory.getLogger(this::class.java)

		val cassandraContainer: CassandraContainer =
			CassandraContainer(DockerImageName.parse(IMAGE_NAME))
				.apply {
					logger.info("Starting Cassandra container..")
					withCopyFileToContainer(
						org.testcontainers.utility.MountableFile.forClasspathResource("data/init.cql"),
						"/init.cql"
					)
					withCommand("cassandra -f")
					start()
					logger.info("Cassandra container started")
					logger.info("""
						Port: $firstMappedPort,
						LocalDatacenter: $localDatacenter
						ContactPoint: $contactPoint,
					"""
					)
					execInContainer("cqlsh", "-f", "/init.cql")
				}

		@DynamicPropertySource
		@JvmStatic
		fun overrideProperties(registry: DynamicPropertyRegistry): DynamicPropertyRegistry {
			registry.add("spring.cassandra.port", cassandraContainer.contactPoint::getPort)
			registry.add("spring.cassandra.contact-points", cassandraContainer.contactPoint.address.hostAddress::toString)
			registry.add("spring.cassandra.local-datacenter", cassandraContainer::getLocalDatacenter)
			registry.add("spring.cassandra.username", cassandraContainer::getUsername)
			registry.add("spring.cassandra.password", cassandraContainer::getPassword)
			return registry
		}
	}
}