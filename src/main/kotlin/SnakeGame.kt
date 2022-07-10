import org.openrndr.*
import org.openrndr.extra.olive.Olive

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.extra.timer.repeat
import org.openrndr.extra.timer.timeOut
import org.openrndr.math.Vector2
import kotlin.math.abs
import kotlin.system.measureNanoTime
import kotlin.time.measureTime

const val windowWidth: Int = 840
const val windowHeight: Int  = 840
const val nSquares: Double = 10.0
const val widthPerSquare: Double = windowWidth / nSquares
const val heightPerSquare: Double = windowHeight / nSquares

val allCoords: MutableList<Point> = mutableListOf()

val FPS = 60

fun main() = application {
    configure {
        title = "Snake (Vncero)"

        width = windowWidth
        height = windowHeight
        windowAlwaysOnTop = true
        fullscreen = Fullscreen.SET_DISPLAY_MODE
    }

    program {
        val square = Square(Point(5, 5))
        extend(Olive<Program>())
        extend {
            val renderTimeInNs = measureNanoTime { // execution in ns -> s, should work out
                drawer.render(square, ColorRGBa.GREEN)
                keyboard.keyDown.listen {
                    square.direction = when (it.key) {
                        KEY_ARROW_UP -> Direction.UP
                        KEY_ARROW_DOWN -> Direction.DOWN
                        KEY_ARROW_LEFT -> Direction.LEFT
                        KEY_ARROW_RIGHT -> Direction.RIGHT
                        else -> square.direction
                    }
                }
                println(square.direction)
                square.update()
            }.toDouble()
            timeOut(
                (1 / FPS) - (renderTimeInNs / 1000000000)
            ) {}
        }
    }
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;
}

fun Drawer.render(square: Square, color: ColorRGBa) {
    this.fill = color
    this.rectangle(
        square.location.coordX * widthPerSquare,
        square.location.coordY * heightPerSquare,
        widthPerSquare,
        heightPerSquare
    )
    if (square.leader == null && square.direction != null) {
        this.fill = ColorRGBa.BLUE
        this.points(
            listOf(
                Vector2(
                    square.location.coordX + abs(square.location.coordX - widthPerSquare) / 3,
                    square.location.coordY + abs(square.location.coordY - widthPerSquare) / 3
                ),
                Vector2(
                    square.location.coordX + 2 * abs(square.location.coordX - widthPerSquare) / 3,
                    square.location.coordY + 2 * abs(square.location.coordY - widthPerSquare) / 3
                )
            )
        )
    }
}