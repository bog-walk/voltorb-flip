package model

import dev.bogwalk.model.LevelGenerator

class TestLevelGenerator : LevelGenerator() {
    var subLevel: Int = 0
    var toAssign: List<Int> = emptyList()

    override fun getLevelData(level: Int): List<Int> {
        return levelOptions[level - 1][subLevel]
    }

    override fun assignLevelData(data: List<Int>): List<Int> {
        val values = MutableList(25) { 1 }
        var next = 0
        for (i in 0..2) {
            val value = (i + 2) % 4
            repeat(data[i]) {
                val index = toAssign[next]
                values[index] = value
                next++
            }
        }
        return values
    }
}