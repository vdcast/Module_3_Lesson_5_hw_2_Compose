package com.example.module_3_lesson_5_hw_2_compose

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.TimeUnit

class AppViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private lateinit var timer: DisposableSubscriber<Long>

    fun startTimer(until: Long) {

        timer = object : DisposableSubscriber<Long>() {
            override fun onNext(t: Long?) {
                Log.d("MYLOG", "4 next : $t")
            }

            override fun onError(t: Throwable?) {  }
            override fun onComplete() {  }

        }

        Flowable.intervalRange(0, until + 1, 0, 1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(timer)
    }

    fun stopTimer() {
        timer.dispose()
    }


    fun calculateTime(newValue: String) {
        var totalSeconds = newValue.toLongOrNull() ?: 0
        if (totalSeconds > 3599999) {
            totalSeconds = 3599999
        }
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        _uiState.value = AppUiState(
            inputSeconds = totalSeconds.toString(),
            currentHours = String.format("%02d", hours),
            currentMinutes = String.format("%02d", minutes),
            currentSeconds = String.format("%02d", seconds)
        )
//        _uiState.update { currentState ->
//            currentState.copy(
//                inputSeconds = totalSeconds.toString(),
//                currentHours = String.format("%02d", hours),
//                currentMinutes = String.format("%02d", minutes),
//                currentSeconds = String.format("%02d", seconds)
//            )
//        }
    }



    fun resetTime() {
        _uiState.value = AppUiState(
            currentHours = "00",
            currentMinutes = "00",
            currentSeconds = "00"
        )
    }

    init {
        resetTime()
    }
}