package com.example.stockmanagement.repository

import com.example.stockmanagement.domain.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.persistence.LockModeType

@Repository
interface StockRepository : JpaRepository<Stock, String>{

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.id = :id")
    fun findByIdWithPessimisticLock(id : String) : Stock


    @Lock(LockModeType.OPTIMISTIC)
    @Query("select s from Stock s where s.id = :id")
    fun findByIdWithOptimisticLock(id : String) : Stock
}