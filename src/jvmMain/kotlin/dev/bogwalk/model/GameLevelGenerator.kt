package dev.bogwalk.model

import kotlin.random.Random

class GameLevelGenerator : LevelGenerator() {
    override fun getLevelData(level: Int): List<Int> = levelOptions[level].random()

    override fun assignLevelData(data: List<Int>): List<Int> {
        val values = MutableList(25) { 1 }
        val indices = (0..24).toMutableList()
        var available = 25
        for (i in 0..2) {
            val value = (i + 2) % 4
            repeat(data[i]) {
                val index = indices.removeAt(Random.nextInt(available))
                values[index] = value
                available--
            }
        }
        return values
    }
}