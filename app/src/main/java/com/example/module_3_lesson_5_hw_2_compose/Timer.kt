package com.example.module_3_lesson_5_hw_2_compose

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import java.util.concurrent.TimeUnit

class Timer() {
    private lateinit var timer: DisposableSubscriber<Long>

    fun start(until: Long) {
        timer = object : DisposableSubscriber<Long>() {
            override fun onNext(t: Long?) {
                Log.d("MYLOG", "3 next : $t")


//                timerTextHours = String.format("%02d", hours)

            }

            override fun onError(t: Throwable?) {  }
            override fun onComplete() {  }

        }

        Flowable.intervalRange(0, until, 0, 1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(timer)
    }

    fun stop() {
        timer.dispose()
    }

    fun startTest(until: Long) {
        timer = object : DisposableSubscriber<Long>() {
            override fun onNext(t: Long?) {
                Log.d("MYLOG", "2 next : $t")
            }

            override fun onError(t: Throwable?) {  }
            override fun onComplete() {  }

        }

        Flowable.intervalRange(0, until, 0, 1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(timer)
    }
}