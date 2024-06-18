package com.toni.hosteleriatfg.data.model

import android.os.Parcelable
import java.io.Serializable

data class OrderItemInstance(
    val orderItemList: MutableList<OrderItem> = mutableListOf(),
) : Serializable