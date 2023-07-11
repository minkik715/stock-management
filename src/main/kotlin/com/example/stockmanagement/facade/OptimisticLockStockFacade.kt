package com.example.stockmanagement.facade

import com.example.stockmanagement.service.OptimisticLockStockService
import org.springframework.stereotype.Service

@Service
class OptimisticLockStockFacade(
    private val optimisticLockStockService: OptimisticLockStockService
) {

    fun decrease(id: String, quantity: Long) {
        while (true) {
            try {
                optimisticLockStockService.decrease(id, quantity)
                break
            } catch (e: Exception) {
                decrease(id, quantity)
            }
        }
    }

}