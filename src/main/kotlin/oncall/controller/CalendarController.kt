package oncall.controller

import oncall.view.View
import oncall.domain.InputValidator
import oncall.domain.CalendarService
import oncall.model.NumberBasket
import oncall.resources.Messages.*

class CalendarController(
    private val view: View,
    private val validator: InputValidator,
    private val calendarService: CalendarService
) {
    fun gameStart() {
        val numberBasket = generateNumberBasket()
        view.showMessage(SUM_START_HEADER.message())
        view.showBlankLine()

        announceSumNumbers(numberBasket)
    }

    private fun generateNumberBasket(): NumberBasket {
        val basket = NumberBasket()

        basket.addNumber(readNumberWithRetry(LEFT_VALUE_INPUT.infoMessage()))
        basket.addNumber(readNumberWithRetry(RIGHT_VALUE_INPUT.infoMessage()))

        return basket
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