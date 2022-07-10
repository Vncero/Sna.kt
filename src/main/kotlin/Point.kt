class Point (var coordX: Int, val coordY: Int) {
    private fun isUnique(): Boolean {
        allCoords.forEach {
            //check for the same coordinates
            if (this.coordX == it.coordX
                && this.coordY == it.coordY)
                return false
        }
        return true
    }

    private fun inBounds(): Boolean =
        this.coordX.toDouble() in 0.0..nSquares
        && this.coordY.toDouble() in 0.0..nSquares

    fun isLegal(): Boolean =
        this.isUnique()
        && this.inBounds()
}