package oncall.view

import camp.nextstep.edu.missionutils.Console

class View {
    // Input Views
    fun readLine(): String {
        return Console.readLine()
    }

    // Output Views
    fun showBlankLine() {
        println()
    }

    fun showMessage(message: String) {
        println(message)
    }
}