package common

data class Point3(var x: Long, var y: Long, var z: Long) {
    fun clone(): Point3 {
        return Point3(x, y, z)
    }
}

data class Point3f(var x: Double, var y: Double, var z: Double) {
    fun clone(): Point3f {
        return Point3f(x, y, z)
    }
}

data class Point2(var x: Int, var y: Int) {
    fun clone(): Point2 {
        return Point2(x, y)
    }

    fun travel(dir: Dir, count: Int = 1): Point2 {
        return dir.continuePoint(this)
    }
}

enum class Dir {
    Up {
        override fun opposite(): Dir { return Dir.Down }
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Left, Dir.Up, Dir.Right) }
        override fun continuePoint(pt: Point2, count: Int): Point2 { return Point2(pt.x, pt.y-count) }
    },
    Right {
        override fun opposite(): Dir { return Dir.Left }
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Up, Dir.Right, Dir.Down) }
        override fun continuePoint(pt: Point2, count: Int): Point2 { return Point2(pt.x + count, pt.y) }
    },
    Down {
        override fun opposite(): Dir { return Dir.Up }
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Right, Dir.Down, Dir.Left) }
        override fun continuePoint(pt: Point2, count: Int): Point2 { return Point2(pt.x, pt.y+count) }
    },
    Left {
        override fun opposite(): Dir { return Dir.Right }
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Down, Dir.Left, Dir.Up) }
        override fun continuePoint(pt: Point2, count: Int): Point2 { return Point2(pt.x-count, pt.y) }
    };

    abstract fun opposite(): Dir
    abstract fun continuePoint(pt: Point2, count: Int=1): Point2
    abstract fun dirsWithin90(): List<Dir>
}

