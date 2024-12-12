package oncall.model

import oncall.controller.CalendarController
import oncall.domain.CalendarService
import oncall.domain.InputValidator
import oncall.resources.Messages.*
import oncall.resources.Messages.NOT_INTEGER
import oncall.view.View
import java.util.*

class MonthCalender(private val monthWithDate: Pair<Int,String>, private val names: Pair<List<String>, List<String>>) {
    private val organizedNames: MutableList<String> = mutableListOf()

    fun getEmergencySchedule(): List<String> {
        setOrganizedNames()
        var dateIndex =
            dateNameIndex[monthWithDate.second] ?: throw IllegalArgumentException(INVALID_ERROR.errorMessage())
        val fullSchedule: MutableList<String> = mutableListOf()

        for (day in 1..monthLengths[monthWithDate.first - 1]){
            fullSchedule.add("${monthWithDate.first}월 ${day}일 ${dateNames[dateIndex % 7]}${getSpecialHolidayTag(day)} ${organizedNames[day-1]}")
            dateIndex += 1
        }
        return fullSchedule
    }

    private fun setOrganizedNames() {
        val workdayStack = Stack<String>()
        val holidayStack = Stack<String>()
        var workdayIndex = 0
        var holidayIndex = 0
        val monthLength = monthLengths[monthWithDate.first - 1]
        var dateIndex =
            dateNameIndex[monthWithDate.second] ?: throw IllegalArgumentException(INVALID_ERROR.errorMessage())
        var lastName = ""
        for (currentDay in 1..monthLength) {
            val isHoliday = isHoliday(currentDay, dateIndex)

            if (!isHoliday) {
                while (workdayStack.isEmpty() || workdayStack.last() == lastName){
                    workdayStack.push(names.first[workdayIndex])
                    workdayIndex = (workdayIndex+1) % names.first.size
                }
                organizedNames.add(workdayStack.pop())
                lastName = organizedNames.last()
            }

            if (isHoliday) {
                while (holidayStack.isEmpty() || holidayStack.last() == lastName){
                    holidayStack.push(names.second[holidayIndex])
                    holidayIndex = (holidayIndex+1) % names.second.size
                }
                organizedNames.add(holidayStack.pop())
                lastName = organizedNames.last()
            }

            dateIndex += 1
        }
    }


    private fun isHoliday(currentDay: Int, dateIndex: Int): Boolean {
        val specialHoliday =
            specialHolidays[monthWithDate.first] ?: listOf()
        return (dateIndex % 7 >= 5) || currentDay in specialHoliday
    }

    private fun getSpecialHolidayTag(currentDay: Int): String {
        val specialHoliday =
            specialHolidays[monthWithDate.first] ?: listOf()
        if (currentDay in specialHoliday){
            return "(휴일)"
        }
        return ""
    }

    companion object {
        val monthLengths = listOf(31,28,31,30,31,30,31,31,30,31,30,31)
        val specialHolidays = mapOf(1 to listOf(1), 3 to listOf(1), 5 to listOf(5),
            6 to listOf(6), 8 to listOf(15), 10 to listOf(3, 9), 12 to listOf(25))
        val dateNameIndex = mapOf("월" to 0, "화" to 1, "수" to 2, "목" to 3, "금" to 4, "토" to 5, "일" to 6)
        val dateNames = listOf("월", "화", "수", "목", "금", "토", "일")
    }
}