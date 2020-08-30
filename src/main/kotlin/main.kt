
import java.util.stream.Collectors.toList


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
    override fun toString(): String {
        return "EmptyCell"
    }

    override fun draw(): String {
        return "   -----  "
    }
}

data class DownCell(override val down: Int) : ICell, IDown {
    override fun draw(): String {
        return "   " + pad2(down) + "\\--  "
    }
}

data class AcrossCell(override val across: Int) : ICell, IAcross {
    override fun draw(): String {
        return "   --\\" + pad2(across) + "  "
    }
}

data class DownAcrossCell(override val down: Int, override val across: Int) : ICell, IDown, IAcross {
    override fun draw(): String {
        return "   " + pad2(down) + "\\" + pad2(across) + "  "
    }
}

data class ValueCell(val values: Set<Int>) : ICell {
    override fun draw(): String {
        if (1 == values.size) {
            return "     " + values.first() + "    "
        } else {
            return " " + (1..9)
                .map { if (values.contains(it)) "" + it else "." }
                .joinToString("")
        }
    }
}

fun da(d: Int, a: Int) = DownAcrossCell(d, a)
fun d(d: Int) = DownCell(d)
fun a(a: Int) = AcrossCell(a)
fun e() = EmptyCell
fun v() = ValueCell(setOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
fun v(vararg args: Int) = ValueCell(args.toSet())
fun v(args: List<Int>) = ValueCell(args.toSet())

fun drawRow(row: Array<ICell>): String {
    return row.map { it.draw() }.joinToString("") + "\n"
}

fun drawGrid(grid: Array<Array<ICell>>): String {
    return grid.map { drawRow(it) }.joinToString()
}

fun <T> allDifferent(nums: Collection<T>): Boolean {
    return nums.size == HashSet(nums).size
}

fun <T> conj(items: List<T>, item: T): List<T> {
    val result = ArrayList(items)
    result.add(item)
    return result
}

fun <T> concatLists(a: List<T>, b: List<T>): List<T> {
    return listOf(a, b).flatten()
}

fun <T> product(colls: List<Set<T>>): List<List<T>> {
    return when (colls.size) {
        0 -> emptyList()
        1 -> colls[0].map { listOf(it) }.toList()
        else -> {
            val head = colls[0]
            val tail = colls.drop(1)
            val tailProd = product(tail)
            return head.flatMap { x ->
                tailProd.map { ys -> concatLists(listOf(x), ys) }
            }.toList()
        }
    }
}

fun permuteAll(vs: List<ValueCell>, target: Int): List<List<Int>> {
    val values = vs.map { it.values }.toList()
    return product(values)
        .filter { x -> target == x.sum() }
        .toList()
}

fun isPossible(v: ValueCell, n: Int): Boolean {
    return v.values.contains(n)
}

fun <T> transpose(m: List<List<T>>): List<List<T>> {
    return if (m.isEmpty()) {
        emptyList()
    } else {
        (1..m[0].size)
            .map { i -> m.map { col -> col[i - 1] }.toList() }
            .toList()
    }
}

fun <T> partitionBy(f: (T) -> Boolean, coll: List<T>): List<List<T>> {
    return if (coll.isEmpty()) {
        emptyList()
    } else {
        val head = coll[0]
        val fx = f(head)
        val group = coll.takeWhile { fx == f(it) }
        concatLists(listOf(group), partitionBy(f, coll.drop(group.size)))
    }
}

fun <T> partitionAll(n: Int, step: Int, coll: List<T>): List<List<T>> {
    return if (coll.isEmpty()) {
        emptyList()
    } else {
        concatLists(listOf(coll.take(n)), partitionAll(n, step, coll.drop(step)))
    }
}

fun <T> partitionN(n: Int, coll: List<T>): List<List<T>> {
    return partitionAll(n, n, coll)
}

fun solveStep(cells: List<ValueCell>, total: Int): List<ValueCell> {
    val finalIndex = cells.size - 1
    val perms = permuteAll(cells, total)
        .filter { v -> isPossible(cells.last(), v[finalIndex]) }
        .filter { allDifferent(it) }
        .toList()
    return transpose(perms)
        .map { v(it) }
        .toList()
}

fun gatherValues(line: List<ICell>): List<List<ICell>> {
    return partitionBy({ v -> v is ValueCell }, line)
}

fun pairTargetsWithValues(line: List<ICell>): List<Pair<List<ICell>, List<ICell>>> {
    return partitionN(2, gatherValues(line))
        .map { part ->
            Pair(part[0], if (1 == part.size) emptyList() else part[1])
        }
        .toList()
}

fun solvePair(f: (ICell) -> Int, pair: Pair<List<ICell>,List<ICell>>): List<ICell> {
    val notValueCells = pair.first
    return if (pair.second.isEmpty()) {
        notValueCells
    } else {
        val valueCells = pair.second.map { v -> v as ValueCell}
        val newValueCells = solveStep(valueCells, f(notValueCells.last()))
        concatLists(notValueCells, newValueCells)
    }
}

fun main(args: Array<String>) {

}
