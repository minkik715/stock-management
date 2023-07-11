package com.example.stockmanagement.service

import com.example.stockmanagement.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OptimisticLockStockService(
    private val stockRepository: StockRepository,
) {

    @Transactional
    fun decrease(id: String, quantity: Long): Long {
        val stock = stockRepository.findByIdWithOptimisticLock(id)
        stock.decrease(quantity)
        stockRepository.save(stock)
        return stock.quantity
    }
}