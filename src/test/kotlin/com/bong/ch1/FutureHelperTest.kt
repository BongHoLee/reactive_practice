package com.bong.ch1

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class FutureHelperTest {

    @Test
    fun test() {
        println(getFutureCompleteAfter1S().get())

    }
}