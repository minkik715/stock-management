package com.example.stockmanagement.service

import com.example.stockmanagement.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PessimisticLockStockService(
    private val stockRepository: StockRepository,
) {

    @Transactional
    fun decrease(id: String, quantity: Long): Long {
        kotlin.runCatching {
            val stock = stockRepository.findByIdWithPessimisticLock(id)
            stock.decrease(quantity)
            stock
        }.onSuccess {
            stockRepository.save(it)
            return it.quantity
        }.onFailure {
            it.printStackTrace()
        }
        return 0L
    }
}