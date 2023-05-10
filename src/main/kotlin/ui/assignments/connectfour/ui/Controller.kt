package ui.assignments.connectfour.ui

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.event.EventHandler
import javafx.geometry.*
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import ui.assignments.connectfour.HEIGHT
import ui.assignments.connectfour.model.Model
import ui.assignments.connectfour.model.Player

class Controller(private val animation: Animation) : VBox(), InvalidationListener {
    private var gameStart = true

    private val playerOne = Label("Player #1").apply {
        textFill = Color.BLACK
        font = Font.font(30.0)
        isDisable = true
    }

    private val playerTwo = Label("Player #2").apply {
        textFill = Color.BLACK
        font = Font.font(30.0)
        isDisable = true
    }

    private val player = HBox(playerOne,Pane().apply {
        HBox.setHgrow(this, Priority.ALWAYS)
    }, playerTwo).apply {
        spacing = 10.0
        padding = Insets(10.0)
    }

    private val startButton = Button("Click here to start game!").apply {
        background = Background(BackgroundFill(Color.LIGHTGREEN, null, null))
        font = Font.font(30.0)
        alignment = Pos.CENTER
        padding = Insets(30.0)
    }

    private val restartButton = Button("Restart game!").apply {
        background = Background(BackgroundFill(Color.LIGHTGREEN, null, null))
        font = Font.font(30.0)
        alignment = Pos.CENTER
        padding = Insets(30.0)
        translateX = 485.0
    }

    override fun invalidated(observable: Observable?) {
        if (Model.onGameWin.value != Player.NONE || Model.onGameDraw.value == true) { // game set
            playerOne.isDisable = true
            playerTwo.isDisable = true
            gameStart = false
            animation.gameSet(Model.onNextPlayer.value)
            animation.children.add(restartButton)
        } else {
            if (Model.onNextPlayer.value == Player.ONE) {
                playerOne.isDisable = false
                playerTwo.isDisable = true
                if (gameStart) animation.showRed()
            }
            if (Model.onNextPlayer.value == Player.TWO) {
                playerOne.isDisable = true
                playerTwo.isDisable = false
                if (gameStart) animation.showYellow()
            }
        }
    }

    init {
        children.addAll(player, startButton)
        alignment = Pos.CENTER
        Model.onNextPlayer.addListener(this)
        Model.onGameWin.addListener(this)

        startButton.onMouseClicked = EventHandler {
            children.remove(startButton)
            animation.grid.y = HEIGHT - 100.0 * Model.height - 180 + 96.25
            Model.startGame()
        }

        restartButton.onMouseClicked = EventHandler {
            children.remove(restartButton)
            animation.children.apply {
                removeAll(this)
            }
            Model.endGame()
            animation.grid.y = HEIGHT - 100.0 * Model.height - 180 + 96.25
            animation.children.add(animation.grid)
            Model.onNextPlayer.removeListener(this)
            Model.onGameWin.removeListener(this)
            Model.onNextPlayer.addListener(this)
            Model.onGameWin.addListener(this)
            gameStart = true
            Model.startGame()
        }
    }
}