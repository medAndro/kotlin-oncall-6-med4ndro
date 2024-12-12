package oncall

import oncall.controller.CalendarController

fun main() {
    val calender = CalendarController.create()
    calender.makeWorkSchedule()
}
