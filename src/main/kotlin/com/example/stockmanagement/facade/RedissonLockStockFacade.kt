package com.example.stockmanagement.facade

import com.example.stockmanagement.repository.RedisRepository
import com.example.stockmanagement.service.StockService
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedissonLockStockFacade(
    private val redissonClient: RedissonClient,
    private val stockService: StockService
) {
    fun decrease(key: String, quantity: Long) {
        val lock = redissonClient.getLock(key)

        kotlin.runCatching {
            val avaliable = lock.tryLock(5, 1, TimeUnit.SECONDS)
            if(!avaliable){
                println("$key 락 획득 실패")
                return
            }
            stockService.decrease(key,quantity)
        }.onFailure {
            throw it
        }.also {
            lock.unlock()
        }


    }

}