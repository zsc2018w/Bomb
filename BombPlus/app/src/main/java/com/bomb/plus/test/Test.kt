package com.bomb.plus.test

import com.bomb.common.net.log
import kotlin.properties.Delegates

class Test {

   private  var cv: Any?=null
    private  var cv1: Any?=null
    fun test() {
        cv = 1
        cv = 1
        log(cv.toString())
    }
}