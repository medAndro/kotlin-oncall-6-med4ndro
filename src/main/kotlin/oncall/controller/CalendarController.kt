package oncall.controller

import oncall.view.View
import oncall.domain.InputValidator
import oncall.domain.CalendarService
import oncall.model.MonthCalender
import oncall.model.NumberBasket
import oncall.resources.Messages.*

class CalendarController(
    private val view: View,
    private val validator: InputValidator,
    private val calendarService: CalendarService
) {
    fun makeWorkSchedule() {
        val monthWithDate: Pair<Int,String> = readStartDayWithRetry()
        val names: Pair<List<String>, List<String>> = readEmergencyNamesWithRetry()
        val monthCalender = MonthCalender(monthWithDate, names)
        view.showBlankLine()
        view.showMessageBr(monthCalender.getEmergencySchedule().joinToString("\n"))
    }

    private fun readStartDayWithRetry(): Pair<Int, String> {
        while (true) {
            try {
                view.showMessage(START_DAY_INPUT_HEADER.message())
                return validator.validateDate(view.readLine())
            } catch (e: IllegalArgumentException) {
                view.showMessageBr(e.message ?: INVALID_ERROR.errorMessage())
            }
        }
    }

    private fun readEmergencyNamesWithRetry(): Pair<List<String>, List<String>>{
        while (true) {
            try {
                view.showMessage(WEEKDAY_NAME_INPUT_HEADER.message())
                val weekday = validator.validateNamesInput(view.readLine())
                view.showMessage(HOLIDAY_NAME_INPUT_HEADER.message())
                val holiday = validator.validateNamesInput(view.readLine())
                return Pair(weekday, holiday)
            } catch (e: IllegalArgumentException) {
                view.showMessageBr(e.message ?: INVALID_ERROR.errorMessage())
            }
        }
    }

    private fun readNumberWithRetry(infoMessage: String): Int {
        while (true) {
            try {
                view.showMessage(infoMessage)
                return validator.validateInteger(view.readLine())
            } catch (e: IllegalArgumentException) {
                view.showMessage(e.message ?: INVALID_ERROR.errorMessage())
            }
        }
    }

    private fun announceSumNumbers(numberBasket: NumberBasket) {
        val expression = calendarService.getExpression(numberBasket)
        val sumValue = calendarService.plusTwoNumber(numberBasket)
        view.showMessage(SUM_RESULT.formattedMessage(expression, sumValue))
    }

    companion object {
        fun create(): CalendarController {
            val view = View()
            val inputValidator = InputValidator()
            val calendarService = CalendarService()
            return CalendarController(view, inputValidator, calendarService)
        }
    }
}