package dev.baseio.libjetcalendar.monthly

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import java.time.DayOfWeek

@Composable
fun JetCalendarMonthlyView(
  jetMonth: JetMonth,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  isGridView: Boolean,
  needsMonthNavigator: Boolean = false,
  onPreviousMonth: () -> Unit = {},
  onNextMonth: () -> Unit = {}
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .padding(4.dp),
    verticalArrangement = Arrangement.SpaceAround,
  ) {
    if (needsMonthNavigator) {
      MonthNavigator(onPreviousMonth, jetMonth, isGridView, selectedDates, onNextMonth)
    } else {
      MonthName(jetMonth, isGridView, selectedDates)
    }
    jetMonth.monthWeeks.forEach { week ->
      JetCalendarWeekView(
        modifier = Modifier.fillMaxWidth(),
        week = week,
        onDateSelected = onDateSelected,
        selectedDates = selectedDates,
        isGridView
      )
    }

  }
}

@Composable
private fun MonthNavigator(
  onPreviousMonth: () -> Unit,
  jetMonth: JetMonth,
  isGridView: Boolean,
  selectedDates: Set<JetDay>,
  onNextMonth: () -> Unit
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    IconButton(onClick = {
      onPreviousMonth()
    }) {
      Icon(
        Icons.Filled.ArrowBack, "Previous Month",
        tint = Color.Red
      )
    }
    MonthName(jetMonth, isGridView, selectedDates)
    IconButton(onClick = {
      onNextMonth()
    }) {
      Icon(
        Icons.Filled.ArrowForward, "Next Month",
        tint = Color.Red
      )
    }
  }
}

@Composable
private fun MonthName(
  jetMonth: JetMonth,
  isGridView: Boolean,
  selectedDates: Set<JetDay>
) {
  Text(
    text = jetMonth.name(),
    style = TextStyle(
      fontSize = if (isGridView) 16.sp else 18.sp,
      fontWeight = FontWeight.Medium,
      color = colorCurrentMonthSelected(selectedDates, jetMonth)
    ),
    modifier = Modifier.padding(8.dp)
  )
}

@Composable
fun colorCurrentMonthSelected(selectedDates: Set<JetDay>, jetMonth: JetMonth): Color {
  return if (isSameMonth(
      jetMonth,
      selectedDates
    )
  ) Color.Red else MaterialTheme.typography.body1.color
}

@Composable
private fun isSameMonth(
  jetMonth: JetMonth,
  selectedDates: Set<JetDay>
): Boolean {
  return selectedDates.any { "${it.date.monthValue}${it.date.year}" == "${jetMonth.startDate.monthValue}${jetMonth.startDate.year}" }
}


@Composable
fun WeekNames(isGridView: Boolean, dayOfWeek: DayOfWeek) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 16.dp, end = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    dayNames(dayOfWeek = dayOfWeek).forEach {
      Box(
        modifier = Modifier
          .padding(2.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = it, modifier = Modifier.padding(2.dp),
          style = TextStyle(
            fontSize = if (isGridView) 8.sp else 12.sp,
            fontWeight = FontWeight.Bold
          )
        )
      }

    }
  }
}