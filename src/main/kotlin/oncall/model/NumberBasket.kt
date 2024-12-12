package oncall.model

class NumberBasket {
    private val numbers: MutableList<Int> = mutableListOf()

    fun getNumbers() = numbers

    fun addNumber(number: Int) {
        numbers.add(number)
    }
}