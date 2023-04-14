package dev.bogwalk.model

import androidx.compose.runtime.Immutable

@Immutable
data class GameTile(
    val position: Pair<Int, Int>,
    val value: Int,
    val isFlipped: Boolean = false,
    val memoData: List<Boolean> = List(4) { false }
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GameTile
        if (position != other.position) return false
        if (value != other.value) return false
        if (isFlipped != other.isFlipped) return false
        if (memoData != other.memoData) return false
        return true
    }

    override fun hashCode(): Int = position.hashCode()
}