package dev.bogwalk.model

data class GameTile(
    val position: Pair<Int, Int>,
    val value: Int,
    var isFlipped: Boolean = false,
    val memoData: BooleanArray = BooleanArray(4)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GameTile
        if (position != other.position) return false
        return true
    }

    override fun hashCode(): Int = position.hashCode()
}