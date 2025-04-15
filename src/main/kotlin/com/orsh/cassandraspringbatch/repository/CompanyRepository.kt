package com.orsh.cassandraspringbatch.repository

import com.orsh.cassandraspringbatch.entity.Company
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyRepository: CrudRepository<Company, UUID>