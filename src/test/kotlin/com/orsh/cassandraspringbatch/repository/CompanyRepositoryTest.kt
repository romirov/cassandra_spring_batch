package com.orsh.cassandraspringbatch.repository

import com.ocadotechnology.gembus.test.Arranger.some
import com.orsh.cassandraspringbatch.abstractions.AbstractCassandraTest
import com.orsh.cassandraspringbatch.entity.Company
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals


class CompanyRepositoryTest : AbstractCassandraTest() {
	@Autowired
	lateinit var companyRepository: CompanyRepository

	@Test
	fun saveTest() {
		val company = some(Company::class.java)
		val companyId = company.id
		companyRepository.save(company)
		val result = companyRepository.findById(companyId).get()
		assertEquals(company.id, result.id)
	}

	@Test
	fun updateTest() {
		val company = some(Company::class.java)
		val companyId = company.id
		companyRepository.save(company)
		val result = companyRepository.findById(companyId).get()
		assertEquals(company.id, result.id)
		assertEquals(company.name, result.name)

		val updCompany = some(Company::class.java).copy(id = companyId)
		companyRepository.save(updCompany)
		val resultUpd = companyRepository.findById(companyId).get()
		assertEquals(updCompany.id, resultUpd.id)
		assertEquals(updCompany.name, resultUpd.name)
		assertEquals(1, companyRepository.findAll().toList().size)
	}

	@AfterEach
	fun cleanUp() {
		companyRepository.deleteAll()
	}
}