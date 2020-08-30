import org.junit.Test
import java.util.stream.Collectors.toList
import kotlin.test.assertEquals


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