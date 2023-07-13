package com.example.module_3_lesson_5_hw_2_compose

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.module_3_lesson_5_hw_2_compose.ui.theme.Module_3_Lesson_5_hw_2_ComposeTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.module_3_lesson_5_hw_2_compose.ui.theme.Pink50

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val timer = Timer()

        setContent {
            Module_3_Lesson_5_hw_2_ComposeTheme {
                MyApp(timer)

                Button(onClick = { timer.startTest(10L) }) {
                    Text(text = "START")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(timer: Timer) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var timerTextfieldSeconds by remember { mutableStateOf("") }

    var timerTextHours by remember { mutableStateOf("00") }
    var timerTextMinutes by remember { mutableStateOf("00") }
    var timerTextSeconds by remember { mutableStateOf("00") }

//    val timerTest = Timer()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Pink50)
            .clickable { focusManager.clearFocus() }
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_xlarge)),
                text = stringResource(id = R.string.header),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
            )
            Text(
                modifier = Modifier.padding(
                    vertical = dimensionResource(id = R.dimen.padding_small),
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                ),
                text = if (timerTextHours == "00") {
                    stringResource(
                        id = R.string.time_showing_less_than_hour,
                        timerTextMinutes,
                        timerTextSeconds
                    )
                } else {
                    stringResource(
                        id = R.string.time_showing_more_than_hour,
                        timerTextHours,
                        timerTextMinutes,
                        timerTextSeconds
                    )
                },
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            OutlinedTextField(
                modifier = Modifier,
                value = timerTextfieldSeconds,
                onValueChange = { newValue ->
                    var totalSeconds = newValue.toLongOrNull() ?: 0
                    if (totalSeconds > 3599999) {
                        totalSeconds = 3599999
                        timerTextfieldSeconds = totalSeconds.toString()
                    } else {
                        timerTextfieldSeconds = newValue
                    }
                    val hours = totalSeconds / 3600
                    val minutes = (totalSeconds % 3600) / 60
                    val seconds = totalSeconds % 60
                    timerTextHours = String.format("%02d", hours)
                    timerTextMinutes = String.format("%02d", minutes)
                    timerTextSeconds = String.format("%02d", seconds)

                },
                label = { Text(text = stringResource(id = R.string.seconds)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = {
                    if (timerTextfieldSeconds.equals("")) {
                        Toast.makeText(
                            context,
                            R.string.toast_no_time,
                            Toast.LENGTH_SHORT
                        ).apply {
                            setGravity(Gravity.CENTER, 0, 0)
                            show()
                        }
                    } else {
                        timer.start(timerTextfieldSeconds.toLong())
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.button_start))
            }
            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = { timer.stop() }
            ) {
                Text(text = stringResource(id = R.string.button_stop))
            }
        }
    }

}


