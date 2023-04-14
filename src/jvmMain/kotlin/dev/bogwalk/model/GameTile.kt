package dev.bogwalk.model

import androidx.compose.runtime.Immutable

@Immutable
data class GameTile(
    val position: Pair<Int, Int>,
    val value: Int,
    val isFlipped: Boolean = false,
    val memoData: BooleanArray = BooleanArray(4)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GameTile
        if (position != other.position) return false
        if (value != other.value) return false
        if (isFlipped != other.isFlipped) return false
        if (!memoData.contentEquals(other.memoData)) return false
        return true
    }

    override fun hashCode(): Int = position.hashCode()
}