package common

data class Point(var x: Int, var y: Int) {
    fun clone(): Point {
        return Point(x, y)
    }

    fun travel(dir: Dir, count: Int = 1): Point {
        return dir.continuePoint(this)
    }
}

enum class Dir {
    Up {
        override fun opposite(): Dir { return Dir.Down }
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Left, Dir.Up, Dir.Right) }
        override fun continuePoint(pt: Point, count: Int): Point { return Point(pt.x, pt.y-count) }
    },
    Right {
        override fun opposite(): Dir { return Dir.Left }
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Up, Dir.Right, Dir.Down) }
        override fun continuePoint(pt: Point, count: Int): Point { return Point(pt.x + count, pt.y) }
    },
    Down {
        override fun opposite(): Dir { return Dir.Up }
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Right, Dir.Down, Dir.Left) }
        override fun continuePoint(pt: Point, count: Int): Point { return Point(pt.x, pt.y+count) }
    },
    Left {
        override fun opposite(): Dir { return Dir.Right }
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Down, Dir.Left, Dir.Up) }
        override fun continuePoint(pt: Point, count: Int): Point { return Point(pt.x-count, pt.y) }
    };

    abstract fun opposite(): Dir
    abstract fun continuePoint(pt: Point, count: Int=1): Point
    abstract fun dirsWithin90(): List<Dir>
}

