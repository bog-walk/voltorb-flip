package model

import dev.bogwalk.model.GameGrid
import org.junit.BeforeClass
import kotlin.test.*

class GameGridTest {
    companion object {
        private const val LEVEL = 3
        private val indices = listOf(11, 2, 12, 15, 23, 0, 4, 6, 8, 9, 18, 21, 22)
        private lateinit var tester: TestLevelGenerator

        @BeforeClass
        @JvmStatic
        fun setUp() {
            tester = TestLevelGenerator().apply {
                subLevel = 3  // listOf(1, 4, 8, 162) from Level 3
                toAssign = indices
            }
        }
    }
    @Test
    fun `GameGrid initialises with an empty board`() {
        val grid = GameGrid()

        assertEquals(25, grid.tiles.size)
        assertTrue { grid.tiles.all { it.value == 1 } }
        assertTrue { grid.getSummary(isEmpty = true).all { it == 0 to 0 } }
        assertEquals(0, grid.numOfFlippedTiles)
    }

    @Test
    fun `GameGrid resets board correctly in level 3`() {
        val expectedInfo = listOf(5 to 2, 2 to 3, 8 to 0, 6 to 1, 5 to 2,
            6 to 1, 4 to 2, 8 to 1, 5 to 2, 3 to 2)

        val grid = GameGrid(tester)
        var actualMaxCoins = grid.reset(LEVEL)
        var actualInfo = grid.getSummary()

        assertEquals(0, grid.numOfFlippedTiles)
        assertEquals(162, actualMaxCoins)
        assertContentEquals(expectedInfo, actualInfo)
        assertEquals(2, grid.tiles[indices[0]].value)
        assertTrue { indices.subList(1, 5).map { grid.tiles[it] }.all { it.value == 3 } }
        assertTrue { indices.takeLast(8).map { grid.tiles[it] }.all { it.value == 0 } }

        actualMaxCoins = grid.reset(3, toEmpty = true)
        actualInfo = grid.getSummary(isEmpty = true)

        assertEquals(0, grid.numOfFlippedTiles)
        assertEquals(0, actualMaxCoins)
        assertTrue { actualInfo.all { it == 0 to 0 } }
        assertTrue { grid.tiles.all { it.value == 1 } }
    }

    @Test
    fun `GameGrid changes state of selected tile`() {
        val grid = GameGrid(tester)
        grid.reset(LEVEL)

        assertTrue { grid.tiles.all { !it.isFlipped } }

        val position = 3 to 0
        val expectedIndex = position.first * 5 + position.second
        val actualValue = grid.select(position)

        assertEquals(1, grid.numOfFlippedTiles)
        assertEquals(3, actualValue)
        assertEquals(1, grid.tiles.count { it.isFlipped })
        assertEquals(expectedIndex, grid.tiles.indexOfFirst { it.isFlipped })
    }

    @Test
    fun `GameGrid changes stored memo data of selected tile`() {
        val grid = GameGrid(tester)
        grid.reset(LEVEL)

        assertTrue { grid.tiles.all { tile -> tile.memoData.all { !it } } }

        val position = 3 to 0
        val expectedIndex = position.first * 5 + position.second
        grid.edit(position, 0)
        grid.edit(position, 1)
        grid.edit(position, 3)
        val expectedMemo = listOf(true, true, false, true)

        assertFalse { grid.tiles.all { tile -> tile.memoData.all { !it } } }
        assertContentEquals(expectedMemo, grid.tiles[expectedIndex].memoData)
    }

    @Test
    fun `GameGrid flips all unflipped tiles when revealed`() {
        val grid = GameGrid(tester)
        grid.reset(LEVEL)

        assertTrue { grid.tiles.all { !it.isFlipped } }

        val position = 3 to 0
        grid.select(position)

        assertEquals(1, grid.numOfFlippedTiles)
        assertEquals(1, grid.tiles.count { it.isFlipped })

        grid.reveal()

        assertEquals(1, grid.numOfFlippedTiles)
        assertTrue { grid.tiles.all { it.isFlipped } }
    }
}