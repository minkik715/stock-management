package com.example.stockmanagement.service

import com.example.stockmanagement.repository.StockRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockRepository: StockRepository,
) {


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
}