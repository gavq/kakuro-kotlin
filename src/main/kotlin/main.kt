fun pad2(n: Int): String {
    var s = "" + n
    if (s.length < 2) {
        return " $s"
    } else {
        return s
    }
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

fun main(args: Array<String>) {
    println("Hello World!")
}