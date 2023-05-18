package com.scatt.eredbe.rries.utils

import com.scatt.eredbe.rries.utils.device.DiskUtil

object GameUtil {

    fun randomId(): Int{
        return DiskUtil.images().random()
    }

    fun randomList(): List<Int>{
        val list = mutableListOf<Int>()
        (0 until 15).forEach {
            list.add(randomId())
        }
        return list
    }
}