fun pad2(n: Int): String {
    val s = "" + n
    return if (s.length < 2) " $s" else s
}

interface ICell {
    fun draw(): String
}

interface IDown {
    val down: Int
}

interface IAcross {
    val across: Int
}

// singleton
object EmptyCell : ICell {
    override fun toString() = "EmptyCell"

    override fun draw() = "   -----  "
}

data class DownCell(override val down: Int) : ICell, IDown {
    override fun draw() = "   " + pad2(down) + "\\--  "
}

data class AcrossCell(override val across: Int) : ICell, IAcross {
    override fun draw() = "   --\\" + pad2(across) + "  "
}

data class DownAcrossCell(override val down: Int, override val across: Int) : ICell, IDown, IAcross {
    override fun draw() = "   " + pad2(down) + "\\" + pad2(across) + "  "
}

data class ValueCell(val values: Set<Int>) : ICell {
    override fun draw(): String {
        return if (1 == values.size) {
            "     " + values.first() + "    "
        } else {
            (1..9)
                .map { drawOneValue(it) }
                .joinToString(separator = "", prefix = " ")
        }
    }

    private fun drawOneValue(it: Int) = if (it in values) "" + it else "."
}

fun da(d: Int, a: Int) = DownAcrossCell(d, a)
fun d(d: Int) = DownCell(d)
fun a(a: Int) = AcrossCell(a)
fun e() = EmptyCell
fun v() = ValueCell(setOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
fun v(vararg args: Int) = ValueCell(args.toSet())
fun v(args: List<Int>) = ValueCell(args.toSet())

data class SimplePair<T>(val first: T, val second: T)

fun drawRow(row: Collection<ICell>): String {
    return row.map { it.draw() }.joinToString(separator = "", postfix = "\n")
}

fun drawGrid(grid: Collection<Collection<ICell>>): String {
    return grid.map { drawRow(it) }.joinToString()
}

fun <T> allDifferent(nums: Collection<T>): Boolean {
    return nums.size == HashSet(nums).size
}

fun <T> conj(items: List<T>, item: T): List<T> {
    return items + listOf(item)
}

fun <T> product(colls: List<Set<T>>): List<List<T>> {
    return when (colls.size) {
        0 -> emptyList()
        1 -> colls[0].map { listOf(it) }
        else -> {
            val head = colls[0]
            val tail = colls.drop(1)
            val tailProd = product(tail)
            return head.flatMap { x -> tailProd.map { listOf(x) + it } }
        }
    }
}

fun permuteAll(vs: List<ValueCell>, target: Int): List<List<Int>> {
    val values = vs.map { it.values }
    return product(values)
        .filter { target == it.sum() }
}

fun isPossible(v: ValueCell, n: Int): Boolean {
    return v.values.contains(n)
}

fun <T> transpose(m: List<List<T>>): List<List<T>> {
    return if (m.isEmpty()) {
        emptyList()
    } else {
        (1..m[0].size)
            .map { m.map { col -> col[it - 1] } }
    }
}

fun <T> partitionBy(f: (T) -> Boolean, coll: List<T>): List<List<T>> {
    return if (coll.isEmpty()) {
        emptyList()
    } else {
        val head = coll[0]
        val fx = f(head)
        val group = coll.takeWhile { fx == f(it) }
        listOf(group) + partitionBy(f, coll.drop(group.size))
    }
}

fun <T> partitionAll(n: Int, step: Int, coll: List<T>): List<List<T>> {
    return if (coll.isEmpty()) {
        emptyList()
    } else {
        listOf(coll.take(n)) + partitionAll(n, step, coll.drop(step))
    }
}

fun <T> partitionN(n: Int, coll: List<T>): List<List<T>> = partitionAll(n, n, coll)

fun solveStep(cells: List<ValueCell>, total: Int): List<ValueCell> {
    val finalIndex = cells.size - 1
    val perms = permuteAll(cells, total)
        .filter { isPossible(cells.last(), it[finalIndex]) }
        .filter { allDifferent(it) }
    return transpose(perms).map { v(it) }
}

fun gatherValues(line: List<ICell>): List<List<ICell>> {
    return partitionBy({ it is ValueCell }, line)
}

fun pairTargetsWithValues(line: List<ICell>): List<SimplePair<List<ICell>>> {
    return partitionN(2, gatherValues(line))
        .map {
            SimplePair(it[0], if (1 == it.size) emptyList() else it[1])
        }
}

fun solvePair(accessor: (ICell) -> Int, pair: SimplePair<List<ICell>>): List<ICell> {
    val notValueCells = pair.first
    return if (pair.second.isEmpty()) {
        notValueCells
    } else {
        val valueCells = pair.second.map { it as ValueCell }
        val total = accessor(notValueCells.last())
        val newValueCells = solveStep(valueCells, total)
        notValueCells + newValueCells
    }
}

fun solveLine(line: List<ICell>, f: (ICell) -> Int): List<ICell> {
    return pairTargetsWithValues(line)
        .map { solvePair(f, it) }
        .flatten()
}

fun solveRow(row: List<ICell>): List<ICell> {
    return solveLine(row, { (it as IAcross).across })
}

fun solveColumn(column: List<ICell>): List<ICell> {
    return solveLine(column, { (it as IDown).down })
}

fun solveGrid(grid: List<List<ICell>>): List<List<ICell>> {
    val rowsDone = grid.map(::solveRow)
    val colsDone = transpose(rowsDone).map(::solveColumn)
    return transpose(colsDone)
}

fun gridEquals(g1: List<List<ICell>>, g2: List<List<ICell>>): Boolean {
    return if (g1.size == g2.size) {
        val limit = g1.size - 1
        (0..limit).all { g1[it] == g2[it] }
    } else {
        false
    }
}

fun solver(grid: List<List<ICell>>): List<List<ICell>> {
    println(drawGrid(grid))
    val g = solveGrid(grid)
    return if (gridEquals(g, grid)) {
        g
    } else {
        solver(g)
    }
}

