class Square (
    var location: Point
    ) {
    var direction: Direction? = null
    var leader: Square? = null

    constructor(location: Point, leader: Square): this(location) {
        this.leader = leader
    }

    init {
        allCoords.add(this.location)
        this.calculateDirection()
    }

    fun update(){
        //direction takes priority, useful for head update
        this.updateCoords { //terrible spaghetti code, but it probably works
            return@updateCoords when (this.direction ?: this.leader) {
                is Direction -> {
                    when (this.direction!!) {
                        Direction.UP -> Point(this.location.coordX, this.location.coordY + 1)
                        Direction.DOWN -> Point(this.location.coordX, this.location.coordY + 1)
                        Direction.LEFT -> Point(this.location.coordX - 1, this.location.coordY)
                        Direction.RIGHT -> Point(this.location.coordX + 1, this.location.coordY)
                    }
                }
                is Square -> {
                    Point(this.leader!!.location.coordX, this.leader!!.location.coordY)
                }
                else -> Point(this.location.coordX, this.location.coordY)
            }
        }
        println("Updated coords: ${this.location.coordX}, ${this.location.coordY}")
        this.calculateDirection() //re-calculate in case
    }

    private fun updateCoords(update: () -> Point) {
        this.location = update()
    }

    fun findXNeighbors(): MutableList<Point> {
        return allCoords.filter {
            it.coordY == this.location.coordY //horizontally aligned
        }.toMutableList()
    }

    fun findYNeighbors(): MutableList<Point> {
        return allCoords.filter {
            it.coordX == this.location.coordX //vertically aligned
        }.toMutableList()
    }

    fun generateFollower(): Square {
        var followX = this.location.coordX
        var followY = this.location.coordY

        val possibleFollowers = mutableListOf<Point>(
            Point(followX + 1, followY),
            Point(followX - 1, followY),
            Point(followX, followY + 1),
            Point(followX, followY - 1)
        )
        possibleFollowers.removeIf{ !it.isLegal() }
        return Square(possibleFollowers.random())
    }

    fun calculateDirection() {
        var possibleDirections = Direction.values().toMutableList()
        this.findXNeighbors().forEach { //horizontal
            if (it.coordX > this.location.coordX) possibleDirections.remove(Direction.RIGHT)
            if (it.coordX < this.location.coordX) possibleDirections.remove(Direction.LEFT)
        }
        this.findYNeighbors().forEach {
            if (it.coordY > this.location.coordY) possibleDirections.remove(Direction.UP)
            if (it.coordY < this.location.coordY) possibleDirections.remove(Direction.DOWN)
        }
        this.direction = possibleDirections.random()
//        println(this.direction)
    }
}
