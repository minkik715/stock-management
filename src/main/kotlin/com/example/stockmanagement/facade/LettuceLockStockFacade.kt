package com.example.stockmanagement.facade

import com.example.stockmanagement.repository.RedisRepository
import com.example.stockmanagement.service.StockService
import org.springframework.stereotype.Component

@Component
class LettuceLockStockFacade(
    private val redisRepository: RedisRepository,
    private val stockService: StockService
) {
    fun decrease(key: String, quantity: Long){
        while(!redisRepository.lock(key)!!){
            Thread.sleep(100);
        }

        try {
            stockService.decrease(key, quantity)
        }finally {
            redisRepository.unLock(key)
        }

    }

}