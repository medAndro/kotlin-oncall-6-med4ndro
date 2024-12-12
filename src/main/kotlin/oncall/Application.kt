package oncall

import oncall.controller.CalendarController

fun main() {
    val game = CalendarController.create()
    game.gameStart()
}
