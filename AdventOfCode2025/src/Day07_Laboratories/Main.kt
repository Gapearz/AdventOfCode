package Day07_Laboratories

import kotlin.system.measureTimeMillis

fun main(){
    val fileReaderAdvent = Tools.FileReaderAdvent("src/Day07_Laboratories/input.txt")
    val input = fileReaderAdvent.readFileAsMatrix().map { it.toMutableList() }.toMutableList()
    val inputCopy = input.map { it.toMutableList() }.toMutableList()

    val time1 = measureTimeMillis {
        val result = part1(input)
        println("[part 1] Beam was split: $result times")
    }
    println("Part 1 took: $time1 ms")

    val time2 = measureTimeMillis {
        val result = part2(inputCopy, input)
        println("[part 2] Different timelines: $result")
    }
    println("Part 2 took: $time2 ms")
}

fun part1(input: MutableList<MutableList<String>>): Int{
    var splits = 0

    for(x in 0 ..< input.size - 1){
        for(y in 0 ..< input[x].size){
            if(input[x][y] == "S" || input[x][y] == "|"){
                if(input[x + 1][y] == "."){
                    input[x + 1][y] = "|"
                } else if(input[x + 1][y] == "^"){
                    input[x + 1][y - 1] = "|"
                    input[x + 1][y + 1] = "|"
                    splits++
                }
            }
        }
    }

    return splits
}

fun part2(input: MutableList<MutableList<String>>, modifiedInput: MutableList<MutableList<String>>): Long {
    input.removeLast()
    input.add(modifiedInput.last())

    val numRows = input.size
    val numCols = input[0].size
    val memo = mutableMapOf<Pair<Int, Int>, Long>()

    fun search(row: Int, col: Int): Long {
        val key = row to col
        memo[key]?.let { return it }

        if (row == 0) {
            return if (input[row][col] == "S") 1L else 0L
        }

        val left = if (col > 0) input[row][col - 1] else null
        val right = if (col < numCols - 1) input[row][col + 1] else null
        val me = input[row][col]
        var count = 0L

        if (left == "^") {
            count += search(row - 1, col - 1)
        }
        if (right == "^") {
            count += search(row - 1, col + 1)
        }
        if (me != "^") {
            count += search(row - 1, col)
        }

        memo[key] = count
        return count
    }

    return (0 until numCols).sumOf { col ->
        if (input[numRows - 1][col] == "|") {
            search(numRows - 1, col)
        } else {
            0L
        }
    }
}