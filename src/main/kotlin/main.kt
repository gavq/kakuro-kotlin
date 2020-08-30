fun pad2(n: Int): String {
    var s = "" + n
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

class EmptyCell : ICell {
    override fun toString(): String {
        return "EmptyCell"
    }

    override fun draw(): String {
        return "   -----  "
    }
}

class DownCell(override val down: Int) : ICell, IDown {
    override fun toString(): String {
        return "DownCell[$down]"
    }

    override fun draw(): String {
        return "   " + pad2(down) + "\\--  "
    }
}

class AcrossCell(override val across: Int) : ICell, IAcross {
    override fun toString(): String {
        return "AcrossCell[$across]"
    }

    override fun draw(): String {
        return "   --\\" + pad2(across) + "  "
    }
}

class DownAcrossCell(override val down: Int, override val across: Int) : ICell, IDown, IAcross {
    override fun toString(): String {
        return "DownAcrossCell[${down}, ${across}]"
    }

    override fun draw(): String {
        return "   " + pad2(down) + "\\" + pad2(across) + "  "
    }
}

class ValueCell(var values: IntArray) : ICell {
    override fun toString(): String {
        return "ValueCell[" + values.joinToString(", ") + "]"
    }

    override fun draw(): String {
        if (1 == values.size) {
            return "     " + values[0] + "    "
        } else {
            return " " + intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .map { if (values.contains(it)) "" + it else "." }
                .joinToString("")
        }
    }
}

fun da(d: Int, a: Int) = DownAcrossCell(d, a)
fun d(d: Int) = DownCell(d)
fun a(a: Int) = AcrossCell(a)
fun e() = EmptyCell()
fun v() = ValueCell(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
fun v(vararg args: Int) = ValueCell(args)

fun drawRow(row: Array<ICell>): String {
    return row.map { it.draw() }.joinToString("") + "\n"
}

fun drawGrid(grid: Array<Array<ICell>>): String {
    return grid.map { drawRow(it) }.joinToString()
}

fun main(args: Array<String>) {

}
