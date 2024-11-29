package model

import dev.bogwalk.model.GameLevelGenerator
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class LevelTest {
    @Test
    fun `GameLevelGenerator assigns correct level data`() {
        val generator = GameLevelGenerator()
        val data = listOf(3, 1, 6, 24)
        val assignedData = generator.assignLevelData(data)

        assertEquals(data[0], assignedData.count { it == 2 })
        assertEquals(data[1], assignedData.count { it == 3 })
        assertEquals(data[2], assignedData.count { it == 0 })
    }

    @Test
    fun `TestLevelGenerator assigns correct data at level 8`() {
        val level = 8
        val expectedData =  listOf(0, 7, 10, 2187)
        val expectedAssigned = List(25) { i ->
            when {
                i < 7 -> 3
                i in 10..19 -> 0
                else -> 1
            }
        }

        val generator = TestLevelGenerator().apply {
            toAssign = listOf(0, 1, 2, 3, 4, 5, 6, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19)
        }
        val actualData = generator.getLevelData(level)
        val actualAssigned = generator.assignLevelData(actualData)

        assertContentEquals(expectedData, actualData)
        assertContentEquals(expectedAssigned, actualAssigned)
    }
}