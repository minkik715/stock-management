package com.example.stockmanagement.service

import com.example.stockmanagement.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
    private val stockRepository: StockRepository,
) {

    @Synchronized
    fun decrease(id: String, quantity: Long): Long {
        kotlin.runCatching {
            val stock = stockRepository.findById(id).orElseThrow {
                throw IllegalArgumentException("없어요")
            }
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun plainDecrease(id: String, quantity: Long): Long {
        kotlin.runCatching {
            val stock = stockRepository.findById(id).orElseThrow {
                throw IllegalArgumentException("없어요")
            }
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