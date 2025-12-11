package Day10_Factory

import kotlin.system.measureTimeMillis
import kotlin.text.removePrefix

fun main(){
    val fileReaderAdvent = Tools.FileReaderAdvent("src/Day10_Factory/input.txt")
    val input = fileReaderAdvent.readFileAsMatrix(" ")

    val parsed = input.map { line ->
        val requiredState = line.first()
            .removePrefix("[").removeSuffix("]").map { it.toString() }
        val buttons = line.drop(1).dropLast(1)
            .map { it.removePrefix("(").removeSuffix(")").split(",").map(String::trim) }
        val joltageRequirement = line.last()
            .removePrefix("{").removeSuffix("}").split(",").map { it.trim().toInt() }
        Triple(requiredState, buttons, joltageRequirement)
    }

    val time1 = measureTimeMillis {
        val result = part1(parsed)
        println("[part 1] Fewest buttons required for lights: $result")
    }
    println("Part 1 took: $time1 ms")
    val time2 = measureTimeMillis {
        val result = part2(parsed)
        println("[part 2] Fewest buttons required for joltage: $result")
    }
    println("Part 2 took: $time2 ms")
}

fun part1(input: List<Triple<List<String>, List<List<String>>, List<Int>>>): Long {
    var totalClicks = 0L

    for (line in input) {
        val requiredState = line.first.map { if (it == "#") 1 else 0 }
        val buttons = line.second.map { button ->
            button.map { it.toInt() }
        }

        val n = requiredState.size
        val m = buttons.size

        val matrix = Array(n) { IntArray(m + 1) }

        for (x in 0 until n) {
            for (btn in buttons.indices) {
                if (x in buttons[btn]) {
                    matrix[x][btn] = 1
                }
            }
            matrix[x][m] = requiredState[x]
        }

        val solution = solveGF2(matrix, n, m)

        if (solution != null) {
            totalClicks += solution.count { it == 1 }
        }
    }

    return totalClicks
}

fun part2(input: List<Triple<List<String>, List<List<String>>, List<Int>>>): Long {
    var totalClicks = 0L

    for(line in input){
        val buttons = line.second.map { button ->
            button.map { it.toInt() }
        }
        val joltageValues = line.third

        val n = joltageValues.size
        val m = buttons.size

        val matrix = Array(n) { IntArray(m + 1) }

        for(x in 0 until n){
            for(btn in buttons.indices){
                if(x in buttons[btn]){
                    matrix[x][btn] = 1
                }
            }
            matrix[x][m] = joltageValues[x]
        }

        var solution = solveLinearSystem(matrix, n, m)
        if (solution != null) {
            totalClicks += solution.sum()
        }

    }

    return totalClicks
}

fun solveGF2(matrix: Array<IntArray>, rows: Int, cols: Int): IntArray? {
    val augmented = matrix.map { it.copyOf() }.toTypedArray()

    var pivotRow = 0
    val pivotCols = mutableListOf<Int>()

    for (col in 0 until cols) {
        val pivot = (pivotRow until rows).find { augmented[it][col] == 1 } ?: continue
        pivotCols.add(col)

        if (pivot != pivotRow) {
            val temp = augmented[pivotRow]
            augmented[pivotRow] = augmented[pivot]
            augmented[pivot] = temp
        }

        for (row in 0 until rows) {
            if (row != pivotRow && augmented[row][col] == 1) {
                for (c in 0..cols) {
                    augmented[row][c] = augmented[row][c] xor augmented[pivotRow][c]
                }
            }
        }
        pivotRow++
    }

    val freeVars = (0 until cols).filter { it !in pivotCols }

    var minSolution: IntArray? = null
    var minClicks = Int.MAX_VALUE

    for (mask in 0 until (1 shl freeVars.size)) {
        val solution = IntArray(cols)

        for (i in freeVars.indices) {
            solution[freeVars[i]] = (mask shr i) and 1
        }

        for (row in pivotRow - 1 downTo 0) {
            val pivotCol = pivotCols[row]
            var sum = augmented[row][cols]
            for (col in pivotCol + 1 until cols) {
                sum = sum xor (augmented[row][col] and solution[col])
            }
            solution[pivotCol] = sum
        }

        val clicks = solution.count { it == 1 }
        if (clicks < minClicks) {
            minClicks = clicks
            minSolution = solution
        }
    }

    return minSolution
}

fun solveLinearSystem(matrix: Array<IntArray>, rows: Int, cols: Int): IntArray? {
    val augmented = matrix.map { it.copyOf() }.toTypedArray()

    var pivotRow = 0
    val pivotCols = mutableListOf<Int>()

    for (col in 0 until cols) {
        val pivot = (pivotRow until rows).find { augmented[it][col] != 0 } ?: continue
        pivotCols.add(col)

        if (pivot != pivotRow) {
            val temp = augmented[pivotRow]
            augmented[pivotRow] = augmented[pivot]
            augmented[pivot] = temp
        }

        val pivotValue = augmented[pivotRow][col]
        for (row in 0 until rows) {
            if (row != pivotRow && augmented[row][col] != 0) {
                val factor = augmented[row][col]
                for (c in 0..cols) {
                    augmented[row][c] = augmented[row][c] * pivotValue - augmented[pivotRow][c] * factor
                }
            }
        }
        pivotRow++
    }

    val freeVars = (0 until cols).filter { it !in pivotCols }

    var minSolution: IntArray? = null
    var minClicks = Int.MAX_VALUE

    val bitDepths = listOf(3, 5, 7, 9, 11)

    for (bitsPerVar in bitDepths) {
        val maxIterations = 1 shl (freeVars.size * bitsPerVar)
        val iterationLimit = minOf(maxIterations, 10_000_000)

        for (mask in 0 until iterationLimit) {
            val solution = IntArray(cols)

            for (i in freeVars.indices) {
                solution[freeVars[i]] = (mask shr (i * bitsPerVar)) and ((1 shl bitsPerVar) - 1)
            }

            var valid = true
            for (row in pivotRow - 1 downTo 0) {
                val pivotCol = pivotCols[row]
                var sum = augmented[row][cols]
                for (col in pivotCol + 1 until cols) {
                    sum -= augmented[row][col] * solution[col]
                }

                val pivotCoeff = augmented[row][pivotCol]
                if (sum % pivotCoeff != 0) {
                    valid = false
                    break
                }

                val value = sum / pivotCoeff
                if (value !in 0..3000) {
                    valid = false
                    break
                }

                solution[pivotCol] = value
            }

            if (valid) {
                val clicks = solution.sum()
                if (clicks < minClicks) {
                    minClicks = clicks
                    minSolution = solution
                }
            }
        }
    }

    return minSolution
}