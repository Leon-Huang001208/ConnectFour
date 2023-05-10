package ui.assignments.connectfour.ui

import javafx.animation.*
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.Font
import javafx.util.Duration
import ui.assignments.connectfour.HEIGHT
import ui.assignments.connectfour.WIDTH
import ui.assignments.connectfour.model.Model
import ui.assignments.connectfour.model.Player
import kotlin.math.*

// Reference: cs349 public repo: https://git.uwaterloo.ca/j2avery/cs349-public/-/tree/master/Input/Dragging

class Animation : Pane() {
    private var onGrid = false
    private var column = 0
    private var row = 0

    val grid = ImageView(javaClass.getResource("/ui/assignments/connectfour/grid_8x7.png")?.toString() ?: throw IllegalArgumentException("Image file not found")).apply {
        x = 200.0
        y = HEIGHT - 100.0 * Model.height - 180.0
        isPreserveRatio = true
        isSmooth = true
    }

    fun showRed() {
        val redPiece = Circle(50.0, Color.RED).apply {
            translateX = 100.0
            translateY = 50.0
            var offsetX = 0.0
            var offsetY = 0.0

            onMousePressed = EventHandler {
                onGrid = false
                offsetX = translateX - it.sceneX
                offsetY = translateY - it.sceneY
                it.consume()
            }
            onMouseDragged = EventHandler {
                onGrid = false
                translateX = it.sceneX + offsetX
                // translateY = it.sceneY + offsetY
                if (translateX >= grid.x && translateX <= grid.x + 100 * Model.width) {
                    column = ((translateX - grid.x) / 100.0).toInt()
                    translateX = column * 100.0 + grid.x + 50.0
                    onGrid = true
                } else translateX = min(max(translateX, 50.0), WIDTH - 50.0)
                it.consume()
            }

            onMouseReleased = EventHandler {
                val animation1 = Timeline(KeyFrame(Duration(500.0),
                    KeyValue(translateXProperty(), 100.0),
                    KeyValue(translateYProperty(), 50.0)))

                if (onGrid) {
                    Model.dropPiece(column)
                    if (Model.onPieceDropped.value != null) {
                        row = Model.onPieceDropped.value!!.y
                        print("Piece: column:$column row:$row\n")
                        val animation2 = Timeline(KeyFrame(Duration(500.0),
                            KeyValue(translateYProperty(), grid.y + 100.0 * row + 50.0)))
                        animation2.play()
                        isDisable = true

                        /*
                        if (Model.onGameWin.value == Player.NONE && Model.onGameDraw.value == false) {
                            showYellow()
                        } else {
                            gameSet(Model.onNextPlayer.value)
                        }

                         */

                    } else animation1.play()
                } else animation1.play()
            }
        }
        children.add(0, redPiece)
    }

    fun showYellow() {
        val yellowPiece = Circle(50.0, Color.YELLOW).apply {
            translateX = WIDTH - 100.0
            translateY = 50.0
            var offsetX = 0.0
            var offsetY = 0.0

            onMousePressed = EventHandler {
                onGrid = false
                offsetX = translateX - it.sceneX
                offsetY = translateY - it.sceneY
                it.consume()
            }
            onMouseDragged = EventHandler {
                onGrid = false
                translateX = it.sceneX + offsetX
                // translateY = it.sceneY + offsetY
                if (translateX >= grid.x && translateX <= grid.x + 100 * Model.width) {
                    column = ((translateX - grid.x) / 100.0).toInt()
                    translateX = column * 100.0 + grid.x + 50.0
                    onGrid = true
                } else translateX = min(max(translateX, 50.0), WIDTH - 50.0)
                it.consume()
            }

            onMouseReleased = EventHandler {
                val animation1 = Timeline(KeyFrame(Duration(500.0),
                    KeyValue(translateXProperty(), WIDTH - 100.0),
                    KeyValue(translateYProperty(), 50.0)))

                if (onGrid) {
                    Model.dropPiece(column)
                    if (Model.onPieceDropped.value != null) {
                        row = Model.onPieceDropped.value!!.y
                        print("Piece: column:$column row:$row\n")
                        val animation2 = Timeline(KeyFrame(Duration(500.0),
                            KeyValue(translateYProperty(), grid.y + 100.0 * row + 50.0)))
                        animation2.play()
                        isDisable = true

                        /*
                        if (Model.onGameWin.value == Player.NONE && Model.onGameDraw.value == false) {
                            showRed()
                        } else {
                            gameSet(Model.onNextPlayer.value)
                        }

                         */
                    } else animation1.play()
                } else animation1.play()
            }
        }
        children.add(0, yellowPiece)
    }

    fun gameSet(player: Player) {
        val message = Label("").apply {
            translateX = WIDTH / 2  - 360.0
            translateY = HEIGHT / 2 - 200.0
            padding = Insets(30.0)
            font = Font.font(100.0)
        }

        if (player == Player.NONE) {
            message.translateX += 220.0
            message.text = "Draw"
            message.background = Background(BackgroundFill(Color.GRAY, null, null))
        }
        if (player == Player.ONE) {
            message.text = "Player #1 won!!!"
            message.background = Background(BackgroundFill(Color.RED, null, null))
        }
        if (player == Player.TWO) {
            message.text = "Player #2 won!!!"
            message.background = Background(BackgroundFill(Color.YELLOW, null, null))
        }

        children.add(message)
    }

    init {
        children.add(Group(grid))
    }
}