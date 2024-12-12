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

    fun showMessageBr(message: String) {
        println(message)
    }

    fun showMessage(message: String) {
        print(message)
    }
}