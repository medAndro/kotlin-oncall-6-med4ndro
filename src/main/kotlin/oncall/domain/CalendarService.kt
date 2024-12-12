package oncall.domain

import oncall.model.NumberBasket

class CalendarService {
    fun plusTwoNumber(numberBasket: NumberBasket): Int {
        return numberBasket.getNumbers().sum()
    }

    fun getExpression(numberBasket: NumberBasket): String {
        return numberBasket.getNumbers().joinToString(separator = " + ")
    }
}