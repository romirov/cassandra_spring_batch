package com.orsh.cassandraspringbatch.config

import com.datastax.oss.driver.api.core.CqlSession
import com.orsh.cassandraspringbatch.repository.EmployeeCriteriaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.SessionFactory
import org.springframework.data.cassandra.config.CqlSessionFactoryBean
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean
import org.springframework.data.cassandra.core.CassandraTemplate
import org.springframework.data.cassandra.core.convert.CassandraConverter
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories
import java.net.InetSocketAddress


@Configuration
@EnableConfigurationProperties(CassandraProperties::class)
@EnableCassandraRepositories(basePackages = ["com.orsh.cassandraspringbatch.repository"])
class CassandraConfig {

	@Autowired
	lateinit var properties: CassandraProperties

	@Bean
	fun keyspaceName(): String = properties.keyspaceName

	@Bean
	//такой подход имеет дополнительное преимущество в отличии от CqlSession,
	//поскольку также предоставляет контейнеру реализацию ExceptionTranslator,
	//которая транслирует исключения Cassandra в исключения в переносимой DataAccessException иерархии Spring
	fun session() = CqlSessionFactoryBean().apply {
		setLocalDatacenter(properties.localDatacenter)
		setContactPoints(
			properties.contactPoints.map { InetSocketAddress(it, properties.port) }
		)
		setPort(properties.port)
		setUsername(properties.username)
		setPassword(properties.password)
	}

	@Bean
	fun sessionFactory(session: CqlSession, converter: CassandraConverter): SessionFactoryFactoryBean =
		SessionFactoryFactoryBean().apply {
			setSession(session)
			setConverter(converter)
			setSchemaAction(SchemaAction.valueOf(properties.schemaAction))
		}

	@Bean
	fun mappingContext(): CassandraMappingContext = CassandraMappingContext()


	@Bean
	fun converter(cqlSession: CqlSession, mappingContext: CassandraMappingContext): CassandraConverter =
		MappingCassandraConverter(mappingContext).apply {
			userTypeResolver = SimpleUserTypeResolver(cqlSession)
		}

	@Bean
	fun cassandraTemplate(sessionFactory: SessionFactory, converter: CassandraConverter): CassandraTemplate =
		CassandraTemplate(sessionFactory, converter)

	@Bean
	fun employeeCriteriaRepository(): EmployeeCriteriaRepository = EmployeeCriteriaRepository()
}