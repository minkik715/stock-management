package com.example.stockmanagement.repository

import com.example.stockmanagement.domain.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StockRepository : JpaRepository<Stock, String>