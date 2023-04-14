package dev.bogwalk.model

abstract class LevelGenerator {
    // each list represents (num of x2s, num of x3s, num of x0s, max coins)
    // @see <a href="https://bulbapedia.bulbagarden.net/wiki/Voltorb_Flip">Source</a>
    protected val levelOptions = listOf(
        listOf(  // Level 1
            listOf(3, 1, 6, 24), listOf(0, 3, 6, 27), listOf(5, 0, 6, 32),
            listOf(2, 2, 6, 36), listOf(4, 1, 6, 48)
        ),
        listOf(  // Level 2
            listOf(1, 3, 7, 54), listOf(6, 0, 7, 64), listOf(3, 2, 7, 72),
            listOf(0, 4, 7, 81), listOf(5, 1, 7, 96)
        ),
        listOf(  // Level 3
            listOf(2, 3, 8, 108), listOf(7, 0, 8, 128), listOf(4, 2, 8, 144),
            listOf(1, 4, 8, 162), listOf(6, 1, 8, 192)
        ),
        listOf(  // Level 4
            listOf(3, 3, 8, 216), listOf(0, 5, 8, 243), listOf(8, 0, 10, 256),
            listOf(5, 2, 10, 288), listOf(2, 4, 10, 324)
        ),
        listOf(  // Level 5
            listOf(7, 1, 10, 384), listOf(4, 3, 10, 432), listOf(1, 5, 10, 486),
            listOf(9, 0, 10, 512), listOf(6, 2, 10, 576)
        ),
        listOf(  // Level 6
            listOf(3, 4, 10, 648), listOf(0, 6, 10, 729), listOf(8, 1, 10, 768),
            listOf(5, 3, 10, 864), listOf(2, 5, 10, 972)
        ),
        listOf(  // Level 7
            listOf(7, 2, 10, 1152), listOf(4, 4, 10, 1296), listOf(1, 6, 13, 1458),
            listOf(9, 1, 13, 1536), listOf(6, 3, 10, 1728)
        ),
        listOf(  // Level 8
            listOf(0, 7, 10, 2187), listOf(8, 2, 10, 2304), listOf(5, 4, 10, 2592),
            listOf(2, 6, 10, 2916), listOf(7, 3, 10, 3456)
        )
    )

    /**
     * @param level Current non-zero indexed level value.
     * @return List from [levelOptions] representing (x2s, x3s, x0s, max coins).
     */
    abstract fun getLevelData(level: Int): List<Int>

    /**
     * @return List representing the values of the 25 tiles in a game grid, row by row.
     */
    abstract fun assignLevelData(data: List<Int>): List<Int>
}