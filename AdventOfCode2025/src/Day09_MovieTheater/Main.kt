package Day09_MovieTheater

import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main(){
    val fileReaderAdvent = Tools.FileReaderAdvent("src/Day09_MovieTheater/input.txt")
    val input = fileReaderAdvent.readFileAsMatrix(",")

    val positions = input.map {
        Pair(it[0].toInt(), it[1].toInt())
    }

    val time1 = measureTimeMillis {
        val result = part1(positions)
        println("[part 1] Max area: $result")
    }
    println("Part 1 took: $time1 ms")

    val time2 = measureTimeMillis {
        val result = part2(positions)
        println("[part 2] Max area: $result")
    }
    println("Part 2 took: $time2 ms")
}

fun part1(positions: List<Pair<Int, Int>>): Long{
    var maxArea = 0L

    for (i in 0 until positions.size - 1) {
        for (j in i + 1 until positions.size) {
            val area = calculateArea(positions[i], positions[j])
            if(area > maxArea) {
                maxArea = area
            }
        }
    }

    return maxArea
}

fun part2(positions: List<Pair<Int, Int>>): Long{
    var maxArea = 0L

    for (i in 0 until positions.size - 1) {
        for (j in i + 1 until positions.size) {
            val area = calculateArea(positions[i], positions[j])
            if(area > maxArea && isValidRectangle(positions, positions[i], positions[j])) {
                maxArea = area
            }
        }
    }

    return maxArea
}

fun calculateArea(a: Pair<Int, Int>, b: Pair<Int, Int>): Long{
    return ((abs(a.first - b.first) + 1).toLong() * (abs(a.second - b.second) + 1).toLong())
}

fun isValidRectangle(positions: List<Pair<Int, Int>>, a: Pair<Int, Int>, b: Pair<Int, Int>): Boolean {
    val minX = minOf(a.first, b.first)
    val maxX = maxOf(a.first, b.first)
    val minY = minOf(a.second, b.second)
    val maxY = maxOf(a.second, b.second)

    val corners = listOf(
        Pair(minX, minY),
        Pair(minX, maxY),
        Pair(maxX, minY),
        Pair(maxX, maxY)
    )

    for (corner in corners) {
        if (!isPointInsideOrOnPolygon(corner, positions)) {
            return false
        }
    }

    for (i in 0 until positions.size) {
        val p1 = positions[i]
        val p2 = positions[(i + 1) % positions.size]

        if (edgeIntersectsRectangleInterior(p1, p2, minX, maxX, minY, maxY)) {
            return false
        }
    }

    return true
}

fun edgeIntersectsRectangleInterior(p1: Pair<Int, Int>, p2: Pair<Int, Int>, minX: Int, maxX: Int, minY: Int, maxY: Int): Boolean {

    if (p1.first == p2.first) {
        val x = p1.first
        val edgeMinY = minOf(p1.second, p2.second)
        val edgeMaxY = maxOf(p1.second, p2.second)

        if (x in (minX + 1)..<maxX && edgeMaxY > minY && edgeMinY < maxY) {
            return true
        }
    } else if (p1.second == p2.second) {
        val y = p1.second
        val edgeMinX = minOf(p1.first, p2.first)
        val edgeMaxX = maxOf(p1.first, p2.first)

        if (y in (minY + 1)..<maxY && edgeMaxX > minX && edgeMinX < maxX) {
            return true
        }
    }

    return false
}

fun isPointInsideOrOnPolygon(point: Pair<Int, Int>, polygon: List<Pair<Int, Int>>): Boolean {
    for (i in 0 until polygon.size) {
        val p1 = polygon[i]
        val p2 = polygon[(i + 1) % polygon.size]

        if (isPointOnSegment(point, p1, p2)) {
            return true
        }
    }

    var inside = false
    var j = polygon.size - 1

    for (i in polygon.indices) {
        val xi = polygon[i].first
        val yi = polygon[i].second
        val yj = polygon[j].second

        val minY = minOf(yi, yj)
        val maxY = maxOf(yi, yj)

        if (point.second in minY ..< maxY && xi > point.first) {
            inside = !inside
        }
        j = i
    }

    return inside
}

fun isPointOnSegment(point: Pair<Int, Int>, p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
    if (p1.first == p2.first && point.first == p1.first) {
        return point.second >= minOf(p1.second, p2.second) &&
                point.second <= maxOf(p1.second, p2.second)
    }
    if (p1.second == p2.second && point.second == p1.second) {
        return point.first >= minOf(p1.first, p2.first) &&
                point.first <= maxOf(p1.first, p2.first)
    }
    return false
}