package com.example.stockmanagement.service

import com.example.stockmanagement.domain.Stock
import com.example.stockmanagement.repository.StockRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest

internal class StockServiceTest @Autowired constructor(
    private val stockService: StockService,
    private val stockRepository: StockRepository,
) {
    private val stockId = "12345678-1234-1234-1234-12345678abcd"

    @BeforeEach
    fun beforeEachCreate(){
        val stock =stockRepository.save(Stock(stockId,1L, 100L))

    }

    @AfterEach
    fun afterEachDelete(){
        stockRepository.deleteAll()
    }

    @Test
    fun decrease() {

        val remain = stockService.decrease(stockId, 50L)
        Assertions.assertThat(remain).isEqualTo(50L)
    }


    @Test
    fun decrease_concurrent() {
        val threadCount = 100;

        val executorService = Executors.newFixedThreadPool(threadCount)

        val latch = CountDownLatch(threadCount);

        for (i in 1..threadCount){
            kotlin.runCatching {
                executorService.submit {
                    stockService.decrease(stockId, 1)
                }
            }.getOrDefault{
                latch.countDown()
            }
        }
        latch.await()
        val stock = stockRepository.findById(stockId).get()
        Assertions.assertThat(stock.quantity).isEqualTo(0L)
    }
}