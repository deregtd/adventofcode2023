package day13

data class Sample(val input: String, val answer: Long)

val inputs1 = listOf(
    Sample("""
#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#
""".trimIndent(), 405),
)

val inputs2 = listOf(
    Sample("""
#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#
""".trimIndent(), 400),
)

val inputMine = """
#.##....##.#.
#.##....##.#.
.#.#....#.#.#
.###....###..
#.#.####.#.#.
.####...###.#
#..######..#.
..#..##..#..#
.#........#.#

###......####
..##....##...
.#.######.#..
#.###..###.##
#.##.....#.##
.#.##..##.#..
.##......##..

...#.#.##.#.#....
..###..##..###...
#..##.#..#.##..##
#.###.####.###.#.
####..#..#..####.
####.##..##.#####
#..#.##..##.#..#.
..####.#######..#
......####......#
...#........#...#
...#........#...#

#..###..###
#..#.#..#.#
..###.##.##
....#....#.
.....####..
###.#.##.#.
#.#####.###
##.###..###
#...#....#.
#..#.####.#
#..#.####.#

##..######..#
###.##..##.##
###..####..##
...#.#..#.#..
..##.#..#.##.
###.#.##.#.##
##....##....#
####.####.###
...########..
..#.#....#.#.
###..####..##
..##......##.
###.#.##.#.##
####..##..###
#..#......#..
...##.##.##..
###.##..##.##

####....#...#
###.....#...#
.###.#.###.##
##.#...##.#.#
#.##.#.#.###.
#.#.#..##..#.
##..##..#...#
###...###.#..
###...###.#..
##..##..#...#
#.#.#..##..#.

.####..####..####
.#.######.#..#.##
##...##...####...
#.#.#..#.#.##.#.#
..#.####.#....#.#
#.#.####.#.##.#.#
.##..##..##..##..
##..#..#..####..#
...#.##.#......#.
##.#....#.####.#.
..###..###....###
.##########..####
..###..###....###
#.#.#..#.#.##.#.#
#....##....##....
.#...###..#..#..#
#..#....#..##..#.

.##..#..#........
.##..##.......#.#
#########.###....
..#..#..#....#.##
..#..#........#.#
#......##..###...
.######.#....####
#......###.######
..#..#..#..##....
..#..#..#..##....
#......###.######
.######.#....####
#......##..###...
..#..#........#.#
..#..#..#....#.##
#########.###....
.##..##.......#.#

..####.
.##..##
##.##.#
##.##.#
.##..##
..####.
.######
#......
###...#

##..##.
##...#.
##.#..#
##...#.
..#..#.
.#..#.#
.#...#.
.###.#.
.###.#.

#..####..######..
..##..##..####..#
..#....#...##...#
..#.##.#..#..#..#
.#.####.#..##..#.
.##....##..##..##
..........#..#...
..#....#.#.##.#.#
.##....##.####.##
.########......##
##.#..#.##....##.

.##.#..#.##.#....
#..#......###.#..
#..###.###.##....
.##.#...#....#...
.##...###...###..
#..####....##.#..
.##.####.########
#####.####.##..##
#..####.#####.###
###..###.#.##.###
.##....#.#.#...##
.......#.#.....##
#..#...#..##.####
.##.#..##.#.##.##
#..#.#.##.#.#....
#######.##..#.###
#####.##....#.###

..#..#..#..
#......#..#
.######.#..
#..##..#.#.
#..##..#.#.
.######.#..
#......#..#
..#..#..#..
..#..#....#
##....#####
##.##.#.#.#
#.####.#..#
..#..#..##.
..####.....
...##...###

.####.#.##.#.
#....###..###
#.##.#.#..#.#
......#....#.
.#..#.#......
.####.#.##.#.
......##..##.
.......#..#..
.#..#..####..

...##.#.##..#
#.#..######.#
#...##..###..
##...#.....##
..#...#.##.##
..#...#.##.##
##...#.....##
#...##..###..
#.#..######.#
...##.#.##..#
#..#..#.#....
#..#..#.#....
...##.####..#

#.#...#.##.#..#
##..##.####.##.
.#######..#####
#.#.##..##..##.
.#.##.##..##.##
#.#.#..#..#..#.
.....#.#..#.#..
..#..##.##.##..
..#..##.##.##..

.#..##.#..#..
#..#.####.##.
###.#.##.#.##
###.#.##.#.##
#..#.####.##.
.#..##.#..#..
.#...#.#.###.
##...##..##.#
.#...#.....##
.###..#..##.#
.###..#..##.#
.#...#.....##
##...##..##.#
.#...#.#.###.
.#..##.#..#..
#..#.####.##.
###.#.##...##

##..#..##.##.###.
#....#.#.##...#.#
#....#.#.##...#.#
##..#..##.##.###.
.##....##....#..#
.##..#..#.###....
..###...######.##
#.##.###..#..####
....#.#.###.###..
.##....#####.###.
..##.##..##.#..##
..##.##....#.....
..##.##....##....

###.###..###.
###.###..###.
.#...#.##.#..
.#.#..####..#
.#...##..###.
#.###########
#####......##

##..#.##.
##..#.##.
###.#####
.#.#..##.
####..##.
#....####
.########
..#.##..#
.#.####.#
#.###....
.##.#.##.
###.#####
#####....

#.#..##
...#.##
..#..#.
...#.##
###.##.
#.....#
##....#
#####..
#####..
##....#
#.....#

.##....##..
..#..#.##.#
..#.##.##.#
.##....##..
.....#....#
#..#...##..
..#..##..##
.###..#..#.
##.##......
#.##..#..#.
...#.......
.##........
.##..######

#.####.#.##.####.
#......##.#.#..#.
...##.....##....#
#.####.###.#.##.#
#.#..#.#.##..##..
.##..##.###.###..
...##.....#..##..
.#.##.#.##.......
#.####.#..#......

..##..#..#.#...
..#####....####
...##..##.#..##
...##..##.#..##
..#####....####
..##..#..#.#...
#..#....##.....
#.##.##.##...##
..##.......#.##
.#.##.#...#####
##....###.##..#
#.####.###...##
.#...##.##.....
###.#.#.#.##...
##....#....####
....#.##..##.##
#...######.####

#..#.##.#..#..#
#..#.##.#..#.##
..########...#.
.####..####....
##.##..##.##.#.
#..........###.
#####..######..
....####......#
#.#..##..#.##..
.#.#.##.#.#.#.#
..###..###..##.
#.###..###.###.
###.####.####..
##.#.##.#.####.
####.##.#####..

#.#..#.#.#.#..###
#.####.###.#.##.#
..####...##..#..#
##....##.#.###.##
..............#..
.#....#...#..###.
#.#..#.#######...
#.#..#.#.....#...
.#....#...######.
########.....###.
.##..##.#.#.....#
##....##..#..##.#
.#.##.#...#..##.#
.#....#..##...#..
#.#..#.#.#.#.#..#
#.#..#.##.##.###.
#.#..#.##.##..##.

#.#....##....#.##
#.#####..#####.##
#.....####.....##
.#.#.#....#.#.#..
...#.#.##.#.#....
.##.#......#.##..
#.#...#..#...#.##
###.########.####
.#.#..#..#..#.###
.#.###.##.###.#..
###..#.##.#..####
#.#.##.##.##.#.##
#.#..........#.##
.###.#....#.###..
####.#.##.#.#####
#.#.#.####.#.#.##
.#.###.##.###.#..

......#..
#.#.#..##
..#..#..#
..#..#..#
#.#.#..##
......#..
##.....#.
#.#.#.#..
##.#.#.##
....###.#
#..###.##
#..###.##
...####.#
##.#.#.##
#.#.#.#..

..##..##..#.#
###.##.#..#..
##.######.###
##....#.#.##.
##....#.#.##.
##.######.###
###.##.#..#..
..##..##..#.#
#...#...##...
#...#...##...
..##.###..#.#

#.###.##..#..##.#
##.##....#....##.
##.##....##...##.
#.###.##..#..##.#
#.#..#.#.#..#.###
#...#.###..#...##
.####..#.#.#.....
..#####...#####.#
....####..#....#.
.#.###..##.###.##
.#.###..##.###.##
....####..#....#.
..#####...#####.#
.####..#.#.#.....
#...#.###..#...##

..##.##...#
##########.
.#.##..#..#
..##..##.##
......#.#.#
......####.
###.#####..
###.#####..
......####.

##...#.#..#.#..
##.#.#..#.##.##
#.####.##..#.##
#.#######..#.##
##.#.#..#.##.##
##...#.#..#.#..
.#.#.###...##..
...##..###.##..
.#.######...###
..##.#..#.##.##
###..#...##.###
######.#.#.####
#..#..####.####

#......###.#..#.#
.##..##.##..##..#
########....##...
.........#..##..#
###..####..#..#..
##.##.##..##..##.
..#..#..##.#..#.#
###..####...##...
.#....###.##..##.
#......#.#......#
##.##.###........
...##.....#....#.
#.####.###.####.#

###..#.#.#...
#.#...#.#..##
..##......#..
..#.##..##..#
.##.##.##.#..
.#.###.###..#
.#.###.###..#
.##.#####.#..
..#.##..##..#
..##......#..
#.#...#.#..##
###..#.#.#...
#####...#....
.###.####.##.
.###.####.##.

#..##.#..#.
...##..##..
...##..##..
#...#.#..#.
.##.#.#..#.
.##.#.####.
#.#..#.##.#
#...#.####.
..#########

.....#.####.#..
.##.###.#..###.
....#.######.#.
....#..#..#..#.
.##.#........#.
#..#..##..##..#
####..........#
####.########.#
.##.####..####.
#..#..##..##..#
#..###.#..#.###
#..###.#..#.###
.##..#.####.#..
#..###.#..#.###
#..#.########.#

....#....#..#
.#.##.##.#.#.
###.###.#...#
###.###.#...#
.#.##.##.#.#.
....#....#..#
..###..###.#.
..#.#.###..#.
####..###.##.
####..###.##.
..#.#.###..#.
..###..#.#.#.
....#....#..#
.#.##.##.#.#.
###.###.#...#

..######.#..#.#
.###..#.#..#.##
.###..#....#.##
##.###..#.#..##
......###.#.###
##..##....##...
##.###.##.#.##.
####..##..#.#..
####..##..#.#..
##.###.##.#.##.
##..##....##...
......###.#.###
##.###..#.#..##
.###..#....#.##
.###..#.#..#.##

..#.#...##..#
.##.#...##..#
..#.#######.#
##.#.####..##
.#.#.#...##..
##....#..##.#
.#......##.##
..##.##..##.#
.....#####.##
###.....####.
##.#..#....##
##.#..#....##
###.....####.

...#.##....##.#
###.#.#.##...#.
###.#.#.##...#.
...#.##....##.#
...#.###.#.###.
#..##..##.....#
##.#....##.###.
###..#######.##
##..#..#....##.
##..##.###..###
..#..#.....#...
.#####.#..###..
####.#......#.#
####.#......#.#
.#####.#..###..
.##..#.....#...
##..##.###..###

##.#..##.#.##.#.#
#.##.#.####..####
.##.......####...
.#########....###
.#....#####..####
.#....#..#.##.#..
.##...#..##..##..
##.#.....#....#..
##.###...######..

#.##.#..###
##..###..##
.#..#.#.###
.......####
.####.#####
.####....##
#.##.##.##.
......#.#..
..##..##.##

#.......#
#.......#
##....###
..#.#..##
#..####..
...##..#.
###.#.##.
..###..#.
.....#.##
###.#.#..
.##.##...
#.##.##..
#.##.##..
.##.##...
###.#.#..
.....#.##
...##..#.

.#.#.###.#..#
.##....######
.##.#.#.#####
.##.#.#.#####
.##....######
.#.#.###.#..#
#.#.#.####..#
#.#...#...##.
#..#.###.....
..#.#.#.##..#
.######..###.
#...#.#.#####
.#..#.##.....
..###.#.#####
#.#######....

###...###.#####.#
#....#.##..##.#..
.#.#.#.#.#.......
###...#.....#####
.##...#.....#####
###.#.....#...##.
###.#.....#...##.
.##...#.....#####
###...#.....#####

..#..#..###
##.##.###..
#.####.####
#########..
###..######
..####.....
#..##..#.##
..#..#.....
..#..#.....
.#....#.#..
...##..####
########...
########.##

.#..#.##.####.#
######.########
.#..#..........
.#..#....####.#
.#..#..###..###
......#...##...
.#..#.#########
.#..#...######.
######.........

..##...#.
####.###.
#.##.#.#.
....#....
....#....
#.#..#.#.
####.###.
..##...#.
#.##....#
...##.#..
.###.....
.###.....
...##.#..
#.##....#
..##...#.

##....###.#.#.#
#.####.#.#..#..
.#....#..###..#
..####..###...#
..####...##.#..
##....###.#...#
.#.##.#.#.##.##
.#.##.#.#..####
.........#.#..#
#.#..#.#..#.#.#
.######...##.##
.######...##.##
#.#..#.#..#.###

#..#....#..#....#
#..#.#..#####.#.#
.##...###..##.###
####.#.#.#.#..###
####.#.#.#..#..#.
#..##..#.###.#...
#..#.#.###..##...
.##.....###.....#
.##...#..##.#.#..
.##...#..##.#.#..
.##.....###....##

#.##.##...#.###
####.##.#.##.#.
...######.#..##
..#..#.....##..
.####.#.####.#.
#.####....##.#.
.####.#.##.##.#
#.#...#.#.#....
#.#...#.#.#....
.######.##.##.#
#.####....##.#.
#.####....##.#.
.######.##.##.#
#.#...#.#.#....
#.#...#.#.#....
.####.#.##.##.#
#.####....##.#.

#...#..##.#.##..#
#..#.#...###.#..#
#..#.#.#.###.#..#
#...#..##.#.##..#
#...#.......##..#
.#..#.#.#.###.##.
..###....#.......

.####...##..#
.####....#..#
####.#...####
...#.#......#
#.....##.#.##
#...#..#.##..
#...#..#.##..

.#..########..#
#.###.#..#.###.
...#.#.##.#.#..
#.############.
.#####....#####
.......##......
#....#....#....
...##.#..#.##..
#.####....####.
##..##.##.##..#
##..##.##.##..#
..####....####.
...##.#..#.##..
#....#....#....
.......##......
.#####....#####
#.############.

......##.
.#..##..#
..##.##..
###.##..#
#....####
#.##.##.#
#.##.##.#
#....####
###..#..#
###..#..#
#....####
#.##.##.#
#.##.##.#
#....####
###.##..#

.#.###.###..###
....#.#..#..#..
...#..#.#....#.
#....####....##
.#....##......#
##.#.#.##....##
#.#..##########
#.##.#.#.####.#
#.##.#.#.####.#
#.#..##########
.#.#.#.##....##
.#....##......#
#....####....##

###.####.
##...#.#.
..###.###
##..###..
##..###..
..###.###
##...#.##

.#....#.#..##
#.####.####.#
###..####.###
###..####.###
#.####.####.#
.#....#.#..##
#.####.##....
.........###.
##.##.##....#
#.#..#.#...##
.#.##....#.##
###..###..#.#
.#.##.#..#..#
.#....#.#....
#......##..##
..####...#.##
.#.##.#.##..#

.###.###.#.
#.#..###..#
...###.#..#
...##.###..
...##.##...
...###.#..#
#.#..###..#
.###.###.#.
###..##....
###..##....
.###.###.#.
#.#..###..#
...###.#..#

.#.....
###.#..
#.....#
##.###.
.#....#
.#....#
##.###.
#.....#
.##.#..
.##.#..
#.....#
##.###.
.#....#
.#....#
##.###.
#.....#
###.#..

##...#..#...#
..##..##..##.
...#.#..#.#..
##.#.#..#.#.#
##..#####...#
###..####..##
..#...##...#.
....#....#...
....#....#...
###...##...##
###........##

#...##...##..##
.#......#.#....
..#....#...##..
####..######.##
###....###..#..
#.##..##.######
#........###...
##......##.##..
.########....##
..........###..
..#...##.....##
..##..##..#.###
..#....#....###

..#.####.#..#
..#.####.#..#
...#....#....
##........##.
#.#.####.#.#.
#.#.#..#.#.#.
#.##....##.#.
#.#.####.#.#.
..########..#
.####.#####.#
.##.#..#.##..

#.#....#.####
..####.##....
..#...#######
..#....######
..####.##....
#.#....#.####
#...###.#....
#.##.##..####
#..#..#......
.#..##...#..#
######.#..##.
##..##..#....
#..###.#.....
...###.#.####
#.#..#.##....
##..##.##.##.
.##.#.#..####

.###...
...#...
...#...
.###...
...##..
##..#..
#.#.###
....#..
##.#...
....#..
...#.#.
.##.#..
##..#..
###.###
####...

######...#..##.
##..#######..#.
.######.#####.#
#.##.###..##..#
##..##.###.#..#
.#..#.###...###
#....##.....#..
.........##.###
..##..###..#...
........#####..
..##..#...#.##.
......#.####...
..........###.#
.......##.#####
##..##.########
##..##..###...#
##..##..###...#

.##..#.#..#.#..
.##.##..##..##.
....###....###.
.##.#.#....#.#.
.#..#..####..#.
#####.##..##.##
....#.#.##.#.#.
.##...#.##.#...
#..#..#....#..#
#####.##..##.##
.......#..#....

#.####...
###.###..
....#..##
##.......
##..#....
##..#.#..
###.#...#
.##....##
..##.....
#........
#..##....
#..##....
#........

#.##..###...##.
...##..#.###..#
#.#.##...#..##.
.##...##....##.
#..#....#..####
#..#....#..####
.##...##....##.
#.#.##...#..##.
.#.##..#.###..#
#.##..###...##.
##.....###..##.

.##.###..###.
.##.###..###.
...##.#...#..
.##...##..###
######...#.#.
.##..##.#.###
.##.##..#....
####.#.#.#...
.....#...##..
.##.####..#..
.##.....#...#
.......#.###.
#####..#..##.
########.##.#
.##....#..#.#
.......##.#..
#..#.###..###

###.#.###.#
###.###..#.
#..###.##.#
#....##.#..
#....##.#..
#..######.#
###.###..#.
###.#.###.#
#..########
##.#.######
....#..#.##
.#.##...###
######.#.#.
..##.###..#
..##.###..#
######.#.#.
.#.##...###

......#.#
.##.#....
....#..##
....#.#..
#..###...
#..###...
....###..
....#..##
.##.#....
......#.#
#..##..##

...#.##.#..#.##.#
.#...##..##..##..
###.####.##.####.
..###..##..##..##
..##.....##.....#
..#.#..#....#..#.
.###.##.####.##.#
.##......##......
.################
..#..............
.#...##......##..
##..####.##.####.
#..##..######..##
..##....####....#
#................

#...#.#######
#..###.......
###.##..#..#.
.#..#########
..##.##.####.
#...##.......
.#.#..##.##.#
#######.#..#.
#.#......##.#
#.####.#....#
....###.#..#.
##.#.###....#
..#....######
.#..#..#.##.#
.#..#..#.##.#

###...#..
#.####.#.
#.####.#.
###...#..
#..#..#.#
.##.##...
.##..#...

#..#....#..##..
#.#.####.#.##.#
.####..####..##
..##..#.##....#
..########....#
..########....#
#.#..##..#.##.#
.##......##..##
##........####.

####.#.#.###.
##.####....##
...#.#...##..
...#.#...##..
##.####....##
####.#.#.###.
..#..###.####
..#.#....#.##
##.#.#.##..#.
...#...#....#
.##..#.#...##
....###..#.##
##...#.#.####

###....####
.#.#..#.#..
##.#..#.###
.##.##.##..
###.##.####
..##..##...
#...##...##
...#..#....
...####....
##......###
.##.##.##..
#.##..#####
..#....#...

#.#.#.##..##.
#.#.#.##..##.
#####.#.##.#.
#..#...#..###
##.....#.##..
##.##.#..#.#.
.#.#...###.##
....####....#
#.....###....
#....#.#...#.
#....#..##.#.
.###.#.###...
##.###...####
##.###...####
.###.#.###...
#....#..##.#.
#......#...#.

#.##.#.###.
.####.#....
.####.#....
#.##.#.#.#.
######..#..
#.##.##...#
#....#..#..
.#..#..##..
..##..##.##
......####.
.#..#.#..#.

##.#..##..#.###
.#..........#..
.#...#..#...#..
...##....##.#..
###........####
#..##....##..##
##...#..#...###

##...#.##.##.##
##.#..##..##..#
#.#..#.#......#
#.#...##....#.#
.#...###.#..#.#
.##.####..##..#
###..##.#....#.
#.#.#.....##...
.....#....##...
....#.###....##
....#.###....##

###.#.###..
....#.###..
....###....
##.##.#.###
##..####.##
####.##.###
##..###....
###..#...##
......###..
.....###...
..##..#.#..
##...###.##
...#......#
##.####.###
..##..###..
..##.##.###
........#..

#.###.....#####
#.###.....#####
....#..#.##.#..
##..#.##.#.....
#..#...#....##.
#...####.#.#.##
.#..#.#.###..#.
##.#.#.#....##.
.###...####.#.#
.###...####.#.#
##.#.#.#....##.
.#..#.#.###..#.
#...####.#.#.##
#..#...#....###
##..#.##.#.....

#..##..###.
.######.##.
########.##
.##..##..##
#########..
#..##..###.
#..##..#...
#..##..##..
.##..##.#.#
.##..##.#..
........###
#..##..#..#
.##..##.#.#

....#..###.##.#
.#..#..###....#
.#..#..###....#
....#..###.##.#
#..###.##......
#..##......###.
.#.##.#.##....#
.##.#.#####..##
.#.#.#..###..##
.#..##.###.##.#
.#...#.##......

..#.###
###..##
#.#.#..
####...
.#.#.##
#.##.##
###.#..
.#.####
##...##
##...##
.##....
.##....
##...##
##...##
.#.#.##
###.#..
#.##.##

.....##..
..#######
##.######
##...##..
..#######
.#.......
#..#....#

...#..#....
##.####.##.
#...##...##
.#......#..
##..##...#.
...####...#
..#....#...
####..#####
###.##.###.
#...##...##
##......##.
##......##.
#...##...##
###.##.###.
####..#####
..#....#...
...####...#

..##.####
.#.#.#...
.#.#.##..
####.#.#.
##.####..
.##.##..#
.#...#...
.####.##.
.##.#.##.
.##.#.##.
.####.##.

###.###
...#..#
##...##
..#..##
...####
##..##.
##..##.
...####
..#..#.
##...##
...#..#
###.###
..#....
##.....
###..#.

....#...#.#..
.##..#..##..#
....#.#......
######..#.###
.##.##..#..#.
####.##..####
.##..##..###.
#..#..###....
####.....##..
#..###..#..##
#..###.....##
####.....##..
#..#..###....
.##..##..###.
####.##..####
.##.##..#..#.
######..#.###

..#.###.###..
###...##...#.
....#.##.....
....#.##.....
###...##...#.
..#.###.###..
...######.##.
#...###.#...#
...#.###..#..
##.###.##.##.
..####.#.....
...#.####.#..
####.#..##...
....#..#.....
..#.#.#.#..#.

#..#.##..###.#.
.#.####.#.#..##
..#.##.#..##..#
..##..##..#....
#...##...##.#..
.########.....#
###....#####...
##..##..##.....
.#.#..#.#.#..##
##......##.####
...#..#...#....
.#.####.#..###.
.#.####.#..###.

#.#.#....#..#..##
...##..##.....##.
....#..##.....##.
#.#.#....#..#..##
.##..#.#..#.....#
..#.#.#..###...##
####..#.###....##
.##.###.###.#.##.
..##.####......##
#..###.##...#..#.
###..#.#####.###.
#..#.##...#####..
.#.#...#.#.##...#
.#.#...#.#.##...#
#..#.##...#####..

.....#..#..
.#..#.##.#.
###........
###..#..#..
.###......#
.#.###..###
..#.######.
..##......#
.#.........
..#########
##.#.#..#.#
##.#......#
.#.########
....#.##.#.
..#.######.
#...######.
##..######.

#.##.#.#.#..#####
...#.##.#..#.....
#..#..####.......
#.###.#..#...####
.###....#.#######
#..##.##.#.......
.###...###.##....
#...##.##..######
#....######..#..#
###.##.###.######
#.########..#....
.##..##....######
...#.###..##.....
.#.#####.#.......
..#..##..#...####

..##..#.#..
#.##.##..##
.#..#.#.###
..##....#..
#.##.######
#....###.##
##..##.#.##
..##..#....
######.#...
.####.#..##
.####...#..
..##..##.##
.####.##...
#######.###
.####.#.#..
..##..#..##
.#..###..##

..##.#...##..
#.####..#..#.
...#..#####..
..##.##..#...
..##.##......
##....#.#.##.
.##.#..#.#.##
##..######.#.
#...#..#.#.##
#...#.#...#.#
#...#.#...#.#
#...#..#.#.##
##..######.#.
.##.#..#.#.##
##....#.#.##.
..##.##......
..##.##..#...

#.##.#.##.#.#
.#..#......#.
.####.####.##
.#..#..##..#.
##..########.
.#..#.####.#.
.####.#..#.##
######.##.###
##..##....##.
.........#...
.#..#..##..#.
""".trimIndent()