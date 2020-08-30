import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


internal class MainKtTest {

    @Test
    fun testDrawEmpty() {
        val result = e().draw()
        assertEquals("   -----  ", result)
    }

    @Test
    fun testDrawAcross() {
        val result = a(5).draw()
        assertEquals("   --\\ 5  ", result)
    }

    @Test
    fun testDrawDown() {
        val result = d(4).draw()
        assertEquals("    4\\--  ", result)
    }

    @Test
    fun testDrawDownAcross() {
        val result = da(3, 4).draw()
        assertEquals("    3\\ 4  ", result)
    }

    @Test
    fun testDrawValues() {
        val result = v().draw()
        assertEquals(" 123456789", result)
        val result12 = v(1, 2).draw()
        assertEquals(" 12.......", result12)
    }

    @Test
    fun drawRow() {
        val row = arrayOf(da(3, 4), v(), v(1, 2), d(4), e(), a(5), v(4), v(1))
        val result = drawRow(row)
        println(result)
        val expected = "    3\\ 4   123456789 12.......    4\\--     -----     --\\ 5       4         1    \n"
        assertEquals(expected, result)
    }

    @Test
    fun testProduct() {
        val data = listOf(setOf(1, 2), setOf(10), setOf(100, 200, 300))
        val expected = listOf(
            listOf(1, 10, 100),
            listOf(1, 10, 200),
            listOf(1, 10, 300),
            listOf(2, 10, 100),
            listOf(2, 10, 200),
            listOf(2, 10, 300)
        )
        assertEquals(expected, product(data))
    }

    @Test
    fun testPermute() {
        val vs = listOf(v(), v(), v())
        val results = permuteAll(vs, 6)
        assertEquals(10, results.size)
        val diff = results
            .filter(::allDifferent)
            .toList()
        assertEquals(6, diff.size)
    }

    @Test
    fun testTranspose() {
        val ints = (0..2)
            .map { i: Int -> (0..3).toList() }
            .toList()
        val tr = transpose(ints)
        assertEquals(ints.size, tr[0].size)
        assertEquals(ints[0].size, tr.size)
    }

    @Test
    fun testValueEquality() {
        assertEquals(v(), v())
        assertEquals(v(1, 2), v(1, 2))
    }

    @Test
    fun testIsPoss() {
        val vc = v(1, 2, 3)
        assertTrue(isPossible(vc, 2))
        assertFalse(isPossible(vc, 4))
    }

    @Test
    fun testTakeWhile() {
        val result = (0..9).takeWhile{ n -> n < 4 }
        assertEquals(4, result.size)
    }

    @Test
    fun testTakeWhile2() {
        val result = (0..9).takeWhile{ n -> n < 4 || n > 6 }
        assertEquals(4, result.size)
    }

    @Test
    fun testConcat() {
        val a = listOf(1, 2, 3)
        val b = listOf(4, 5, 6, 1, 2, 3)
        val result = concatLists(a, b)
        assertEquals(9, result.size)
    }

    @Test
    fun testDrop() {
        val a = listOf(1, 2, 3, 4, 5, 6)
        val result = a.drop(4)
        assertEquals(2, result.size)
    }

    @Test
    fun testTake() {
        val a: List<Int> = listOf(1, 2, 3, 4, 5, 6)
        val result: List<Int> = a.take(4)
        assertEquals(4, result.size)
    }

    @Test
    fun testPartBy() {
        val data = listOf(1, 2, 2, 2, 3, 4, 5, 5, 6, 7, 7, 8, 9)
        val result = partitionBy({ n -> 0 == n % 2 }, data)
        assertEquals(9, result.size)
    }

    @Test
    fun testPartAll() {
        val data = listOf(1, 2, 2, 2, 3, 4, 5, 5, 6, 7, 7, 8, 9)
        val result = partitionAll(5, 3, data)
        assertEquals(5, result.size)
    }

    @Test
    fun testPartN() {
        val data = listOf(1, 2, 2, 2, 3, 4, 5, 5, 6, 7, 7, 8, 9)
        val result = partitionN(5, data)
        assertEquals(3, result!!.size)
    }

    @Test
    fun testSolveStep() {
        val result = solveStep(listOf(v(1, 2), v()), 5)
        assertEquals(v(1, 2), result[0])
        assertEquals(v(3, 4), result[1])
    }
//    @Test
//    fun drawGrid() {
//        var grid = arrayOf(
//            arrayOf(e(), d(4), d(22), e(), d(16), d(3)),
//            arrayOf(a(3), v(), v(), da(16, 6), v(), v()),
//            arrayOf(a(18), v(), v(), v(), v(), v()),
//            arrayOf(e(), da(17, 23), v(), v(), v(), d(14)),
//            arrayOf(a(9), v(), v(), a(6), v(), v()),
//            arrayOf(a(15), v(), v(), a(12), v(), v())
//        )
//        val result = drawGrid(grid)
//        println(result)
//        val expected = "   --\\ 3       1         2       16\\ 6       4         2    \n" +
//                       "   --\\18       3         5         7         2         1    \n" +
//                       "   -----     17\\23       8         9         6       14\\--  \n" +
//                       "   --\\ 9       8         1       --\\ 6       1         5    \n" +
//                       "   --\\15       9         6       --\\12       3         9    \n"
//        assertEquals(expected, result)
//    }

}