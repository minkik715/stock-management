package com.example.stockmanagement.facade

import com.example.stockmanagement.repository.LockRepository
import com.example.stockmanagement.service.StockService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NamedLockStockFacade(
    private val stockService: StockService,
    private val lockRepository: LockRepository
) {

    @Transactional
    fun decrease(id: String, quantity: Long) {
        kotlin.runCatching {
            lockRepository.getLock(id)
            stockService.plainDecrease(id, quantity)
        }.also {
            lockRepository.releaseLock(id)
        }
    }
}
