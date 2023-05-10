package ui.assignments.connectfour

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.*
import javafx.stage.Stage
import ui.assignments.connectfour.model.Model
import ui.assignments.connectfour.ui.Animation
import ui.assignments.connectfour.ui.Controller

const val WIDTH = Model.width * 100.0 + 400.0
const val HEIGHT = Model.height * 100 + 200.0

class ConnectFourApp : Application() {
    override fun start(stage: Stage) {

        val animation = Animation()
        val ctrl = Controller(animation)

        val root = VBox(ctrl, animation).apply {
            spacing = 10.0
        }

        val  scene = Scene(root, WIDTH, HEIGHT)
        stage.title = "CS349 - A3 Connect Four - y649huan"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }
}