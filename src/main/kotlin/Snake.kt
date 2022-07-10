import org.openrndr.color.ColorRGBa
import kotlin.random.Random

import org.openrndr.draw.Drawer

class Snake(var length: Int) {
    var head: Square
    var body: MutableList<Square>
    var tail: Square

    init {
        val headX = Random.nextInt(0, nSquares.toInt())
        val headY = Random.nextInt(0, nSquares.toInt())
        head = Square(Point(headX, headY))
        body = mutableListOf()

        var curr = head
        for (i in 0 until length - 1) {
            curr = curr.generateFollower()
            body.add(curr)
        }

//        this.body.forEach {
//            println(listOf(it.coordX, it.coordY))
//        }
//        println(listOf(head.coordX, head.coordY))

        //at this point head is not in the body list, avoiding the error
//        head.calculateDirection(body)
        body.add(0, head)
        tail = body[body.lastIndex]
    }

    fun update() = this.body.forEach { it.update() }
}