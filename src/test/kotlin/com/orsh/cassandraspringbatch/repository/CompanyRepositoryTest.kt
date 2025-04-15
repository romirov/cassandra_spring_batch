//package com.orsh.cassandraspringbatch.repository
//
//import com.datastax.driver.core.utils.UUIDs
//import com.ocadotechnology.gembus.test.Arranger.some
//import com.orsh.cassandraspringbatch.abstractions.AbstractCassandraTest
//import com.orsh.cassandraspringbatch.entity.Company
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import kotlin.test.assertEquals
//
//
//class CompanyRepositoryTest: AbstractCassandraTest() {
//	@Autowired
//	lateinit var companyRepository: CompanyRepository
//
//	@Test
//	fun saveTest() {
////		val companyId = UUIDs.timeBased()
////		val company = some(Company::class.java).copy(id = companyId)
//////		companyRepository.save(company)
////		template.insert<Company>(company)
////		template.exists(company.id, Company::class.java)
//////		val savedCompany = companyRepository.findById(companyId).get()
//////		assertEquals(company, savedCompany)
//		val companyId = UUIDs.timeBased()
//		val company = some(Company::class.java).copy(id = companyId)
//		template.cqlOperations.execute("insert into my_keyspace.company (id, name, address) values (?, ?, ?)", company.id, company.name, company.address.value)
//		val result =template.cqlOperations.queryForList("select * from my_keyspace.company").first()
//		assertEquals(company.id, result["id"])
//	}
//
//}