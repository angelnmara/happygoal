package com.example.happygoaldemo.tools

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class BeeperControl {
    private val scheduler = Executors.newScheduledThreadPool(1)
    public fun beepForAnHour(){
        val beeper = Runnable { println("beep") }
        val beeperHandle = scheduler.scheduleAtFixedRate(beeper, 10, 10, TimeUnit.SECONDS)
        scheduler.schedule(Runnable { beeperHandle.cancel(true) }, 60 * 60, TimeUnit.SECONDS)
    }
}
