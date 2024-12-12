package oncall.domain

import oncall.resources.Messages.*

class InputValidator {

    fun validateDate(input: String): Pair<Int, String> {
        require(input.isNotBlank()) { EMPTY_INPUT.errorMessage() }
        require(input.count { it == ',' } == 1) { DELIMITER_COUNT.errorMessage() }
        val splitInput = input.split(',')
        require(splitInput[0].toInt() in 1..12) { WRONG_MONTH.errorMessage() }
        require(splitInput[1] in dateNames) { WRONG_DATE_NAME.errorMessage() }
        return Pair(splitInput[0].toInt(), splitInput[1])
    }

    fun validateNamesInput(input: String): List<String> {
        require(input.isNotBlank()) { EMPTY_INPUT.errorMessage() }
        val splitInput = input.split(',')
        require(splitInput.size in 5..35) { NAME_COUNT.errorMessage() }
        require(splitInput.all { it.length <= 5 && it.isNotEmpty() }) { INVALID_NAME.errorMessage() }
        require(splitInput.distinct().size == splitInput.size) { DUPLICATE_NAME.errorMessage() }
        return splitInput
    }

    companion object {
        val dateNames = listOf("월", "화", "수", "목", "금", "토", "일")
    }
}