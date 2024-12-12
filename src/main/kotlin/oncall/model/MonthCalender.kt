package oncall.model

import oncall.resources.Messages.*
import java.util.*

class MonthCalender(private val monthWithDate: Pair<Int, String>, private val names: Pair<List<String>, List<String>>) {
    private val organizedNames: MutableList<String> = mutableListOf()

    fun getEmergencySchedule(): List<String> {
        setOrganizedNames()
        var dateIndex =
            dateNameIndex[monthWithDate.second] ?: throw IllegalArgumentException(INVALID_ERROR.errorMessage())
        val fullSchedule: MutableList<String> = mutableListOf()

        for (day in 1..monthLengths[monthWithDate.first - 1]) {
            fullSchedule.add("${monthWithDate.first}월 ${day}일 ${dateNames[dateIndex % 7]}${getSpecialHolidayTag(day)} ${organizedNames[day - 1]}")
            dateIndex += 1
        }
        return fullSchedule
    }

    private fun setOrganizedNames() {
        val stacks = mutableListOf(Stack<String>(), Stack<String>())
        val nameIndexes = mutableListOf(0, 0)
        val monthLength = monthLengths[monthWithDate.first - 1]
        var dateIndex =
            dateNameIndex[monthWithDate.second] ?: throw IllegalArgumentException(INVALID_ERROR.errorMessage())
        for (currentDay in 1..monthLength) {
            val holidayIndex = getHolidayIndex(currentDay, dateIndex)
            setOrganizedName(stacks, nameIndexes, holidayIndex)
            dateIndex += 1
        }
    }

    private fun setOrganizedName(stacks: MutableList<Stack<String>>, nameIndexes: MutableList<Int>, holidayIndex: Int) {
        var lastName = ""
        if (organizedNames.isNotEmpty()) {
            lastName = organizedNames.last()
        }
        while (stacks[holidayIndex].isEmpty() || stacks[holidayIndex].last() == lastName) {
            stacks[holidayIndex].push(names.toList()[holidayIndex][nameIndexes[holidayIndex]])
            nameIndexes[holidayIndex] = (nameIndexes[holidayIndex] + 1) % names.toList()[holidayIndex].size
        }
        organizedNames.add(stacks[holidayIndex].pop())
    }

    private fun getHolidayIndex(currentDay: Int, dateIndex: Int): Int {
        val specialHoliday =
            specialHolidays[monthWithDate.first] ?: listOf()
        if ((dateIndex % 7 >= 5) || currentDay in specialHoliday) {
            return 1
        }
        return 0
    }

    private fun getSpecialHolidayTag(currentDay: Int): String {
        val specialHoliday =
            specialHolidays[monthWithDate.first] ?: listOf()
        if (currentDay in specialHoliday) {
            return "(휴일)"
        }
        return ""
    }

    companion object {
        val monthLengths = listOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        val specialHolidays = mapOf(
            1 to listOf(1), 3 to listOf(1), 5 to listOf(5),
            6 to listOf(6), 8 to listOf(15), 10 to listOf(3, 9), 12 to listOf(25)
        )
        val dateNameIndex = mapOf("월" to 0, "화" to 1, "수" to 2, "목" to 3, "금" to 4, "토" to 5, "일" to 6)
        val dateNames = listOf("월", "화", "수", "목", "금", "토", "일")
    }
}