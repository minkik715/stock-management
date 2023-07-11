package com.example.stockmanagement.domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Version

@Entity
class Stock(
    @Id
    val id: String = UUID.randomUUID().toString(),

    var productId: Long = 0L,

    var quantity: Long = 0L,

    @Version
    var version: Long = 0L
) {

    constructor(productId: Long, quantity: Long) : this(
        UUID.randomUUID().toString(), productId, quantity
    )

    fun decrease(quantity: Long){
        if(this.quantity >= quantity){
            this.quantity -= quantity
        }else{
            throw IllegalArgumentException("재고 부족")
        }
    }
}