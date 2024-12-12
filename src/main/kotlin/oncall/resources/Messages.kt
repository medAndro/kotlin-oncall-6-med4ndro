package oncall.resources

enum class Messages(private val message: String) {
    INFO("[INFO] %s"),
    START_DAY_INPUT_HEADER("비상 근무를 배정할 월과 시작 요일을 입력하세요> "),
    LEFT_VALUE_INPUT("좌변의 값을 입력하세요"),
    RIGHT_VALUE_INPUT("우변의 값을 입력하세요"),
    SUM_RESULT(
        """
        덧셈 결과
        ---
        %s = %s
        """.trimIndent()
    ),

    ERROR("[ERROR] %s"),
    EMPTY_INPUT("입력값이 비어있습니다."),
    DELIMITER_COUNT("구분자 , 1개로 구분하여 입력해주세요"),
    WRONG_MONTH("1~12 사이의 숫자로 월을 입력해 주세요"),
    WRONG_DATE_NAME("요일은 월,화,수,목,금,토,일 중 하나로 입력해 주세요"),
    NOT_INTEGER("입력값이 정수가 아닙니다."),
    INVALID_ERROR("알 수 없는 오류가 발생했습니다.");

    fun message(): String = message
    fun infoMessage(): String = INFO.formattedMessage(message)
    fun errorMessage(): String = ERROR.formattedMessage(message)
    fun formattedMessage(vararg args: Any): String = String.format(message, *args)
}
