package com.bong.ch1

import java.util.concurrent.*

class FutureHelper

fun getFuture(): Future<Int> {
    val executor = Executors.newSingleThreadExecutor()
    return executor.submit(Callable { 1 }).also { executor.shutdown() }
}

fun getFutureCompleteAfter1S(): Future<Int> {
    val executor = Executors.newSingleThreadExecutor()

    return executor.submit(
        Callable { Thread.sleep(1000)
            1
        }
    ).also { executor.shutdown() }

}
