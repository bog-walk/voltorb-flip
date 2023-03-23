package dev.bogwalk.model

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LevelTest {
    @Test
    fun `generates correct tile data at level 1`() {
        val generator = TestLevelGenerator()
        generator.toAssign = listOf(0, 3, 9, 11, 12, 15, 17, 21, 22, 24)
        val data = generator.getLevelData(0)
        val grid = generator.generateLevelTiles(data)
        val infoGrid = generator.getTilesSummary(grid)
        val expectedInfo = listOf(
            7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3,
            5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1
        )

        assertEquals(24, data[3])

        assertEquals(5, grid.size)
        assertEquals(5, grid[0].size)
        assertTrue { grid.flatten().all { !it.isFlipped } }

        assertEquals(2, grid[0][0].value)
        assertEquals(2, grid[0][3].value)
        assertEquals(2, grid[1][4].value)
        assertEquals(3, grid[2][1].value)
        assertEquals(0, grid[2][2].value)
        assertEquals(0, grid[3][0].value)
        assertEquals(0, grid[3][2].value)
        assertEquals(0, grid[4][1].value)
        assertEquals(0, grid[4][2].value)
        assertEquals(0, grid[4][4].value)

        assertContentEquals(expectedInfo, infoGrid)
    }

    @Test
    fun `generates correct tile data at level 8`() {
        val generator = TestLevelGenerator()
        generator.toAssign = listOf(0, 1, 2, 3, 4, 5, 6,
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19)
        val data = generator.getLevelData(7)
        val grid = generator.generateLevelTiles(data)
        val infoGrid = generator.getTilesSummary(grid)
        val expectedInfo = listOf(
            15 to 0, 9 to 0, 0 to 5, 0 to 5, 5 to 0,
            7 to 2, 7 to 2, 5 to 2, 5 to 2, 5 to 2
        )

        assertEquals(2187, data[3])

        assertEquals(5, grid.size)
        assertEquals(5, grid[0].size)
        assertTrue { grid.flatten().all { !it.isFlipped } }

        for ((i, row) in grid.withIndex()) {
            when (i) {
                0 -> assertTrue { row.all { it.value == 3 } }
                1 -> {
                    assertTrue { row.slice(0..1).all { it.value == 3 } }
                    assertTrue { row.slice(2..4).all { it.value == 1 } }
                }
                2, 3 -> assertTrue { row.all { it.value == 0 } }
                4 -> assertTrue { row.all { it.value == 1 } }
            }
        }

        assertContentEquals(expectedInfo, infoGrid)
    }
}