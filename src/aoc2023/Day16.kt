package aoc2023

import readInput

fun main() {
     data class BeamState(val direction: Char, val x: Int, val y: Int)

     fun right(beamState: BeamState, cave: Array<Array<Char>>): BeamState? {
          val newY = beamState.y + 1
          if (newY >= cave[0].size) {
               return null
          }
          return BeamState('R', beamState.x, newY)
     }

     fun left(beamState: BeamState, cave: Array<Array<Char>>): BeamState? {
          val newY = beamState.y - 1
          if (newY < 0) {
               return null
          }
          return BeamState('L', beamState.x, newY)
     }

     fun up(beamState: BeamState, cave: Array<Array<Char>>): BeamState? {
          val newX = beamState.x - 1
          if (newX < 0) {
               return null
          }
          return BeamState('U', newX, beamState.y)
     }

     fun down(beamState: BeamState, cave: Array<Array<Char>>): BeamState? {
          val newX = beamState.x + 1
          if (newX >= cave.size) {
               return null
          }
          return BeamState('D', newX, beamState.y)
     }

     fun nextPosition(beamState: BeamState, cave: Array<Array<Char>>): List<BeamState> {
          val elem = cave[beamState.x][beamState.y]
          val result = mutableListOf<BeamState>()

          if (elem == '.') {
               if (beamState.direction == 'R') {
                    val next = right(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               } else if (beamState.direction == 'L') {
                    val next = left(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               } else if (beamState.direction == 'U') {
                    val next = up(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               } else if (beamState.direction == 'D') {
                    val next = down(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               }
          } else if (elem == '/') {
               if (beamState.direction == 'R') {
                    val next = up(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               } else if (beamState.direction == 'L') {
                    val next = down(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               } else if (beamState.direction == 'U') {
                    val next = right(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               } else if (beamState.direction == 'D') {
                    val next = left(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               }
          } else if (elem == '\\') {
               if (beamState.direction == 'R') {
                    val next = down(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               } else if (beamState.direction == 'L') {
                    val next = up(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               } else if (beamState.direction == 'U') {
                    val next = left(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               } else if (beamState.direction == 'D') {
                    val next = right(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               }
          } else if (elem == '-') {
               if (beamState.direction == 'R') {
                    val next = right(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               } else if (beamState.direction == 'L') {
                    val next = left(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               } else if (beamState.direction == 'U' || beamState.direction == 'D') {
                    val next = left(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
                    val next2 = right(beamState, cave)
                    if (next2 != null) {
                         result.add(next2)
                    }
               }
          } else if (elem == '|') {
               if (beamState.direction == 'R' || beamState.direction == 'L') {
                    val next = up(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
                    val next2 = down(beamState, cave)
                    if (next2 != null) {
                         result.add(next2)
                    }
               } else if (beamState.direction == 'U') {
                    val next = up(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               } else if (beamState.direction == 'D') {
                    val next = down(beamState, cave)
                    if (next != null) {
                         result.add(next)
                    }
               }
          }

          return result
     }

     data class Point(val x: Int, val y: Int)

     fun visualize(visited: Set<BeamState>, width: Int, height: Int) {
          val points = visited.map { Point(it.x, it.y) }
          for (x in 0 .. width) {
               for (y in 0 .. height) {
                    if (points.contains(Point(x, y))) {
                         print('#')
                    } else {
                         print('.')
                    }
               }
               println()
          }
     }

     fun energized(startPosition: BeamState, cave: Array<Array<Char>>): Int {
          val visited = mutableSetOf<BeamState>()
          val toVisit = mutableListOf<BeamState>()
          toVisit.add(startPosition)
          while (toVisit.isNotEmpty()) {
               val visiting = toVisit.removeAt(0)
               visited.add(visiting)
               val next = nextPosition(visiting, cave)
               for (elem in next) {
                    if (!visited.contains(elem)) {
                         toVisit.add(elem)
                    }
               }
          }

          return visited.map { Point(it.x, it.y) }.toSet().size
     }

     fun part1(lines: List<String>): Int {
          val cave = Array(lines.size) { Array(lines[0].length) { '.' } }
          for (i in lines.indices) {
               cave[i] = lines[i].toCharArray().toTypedArray()
          }

          return energized(BeamState('R', 0,0), cave)
     }

     fun part2(lines: List<String>): Int {
          val cave = Array(lines.size) { Array(lines[0].length) { '.' } }
          for (i in lines.indices) {
               cave[i] = lines[i].toCharArray().toTypedArray()
          }

          var max = 0
          for (x in cave[0].indices) {
               val e1 = energized(BeamState('R', x, 0), cave)
               if (e1 > max) {
                    max = e1
               }
               val e2 = energized(BeamState('L', x, cave[0].size - 1), cave)
               if (e2 > max) {
                    max = e2
               }
          }
          for (y in cave.indices) {
               val e1 = energized(BeamState('D', 0, y), cave)
               if (e1 > max) {
                    max = e1
               }
               val e2 = energized(BeamState('U', cave.size - 1, y), cave)
               if (e2 > max) {
                    max = e2
               }
          }
          return max
     }

     println(part1(readInput("aoc2023/Day16_test")))
     println(part1(readInput("aoc2023/Day16")))
     println(part2(readInput("aoc2023/Day16_test")))
     println(part2(readInput("aoc2023/Day16")))
}