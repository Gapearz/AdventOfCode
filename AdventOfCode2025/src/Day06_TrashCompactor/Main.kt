package Day06_TrashCompactor

import Tools.FileReaderAdvent

fun main(){
    val fileReaderAdvent = FileReaderAdvent("src/Day06_TrashCompactor/input.txt")
    val input = fileReaderAdvent.readFileAsMatrix(" ")
    val inputPart2 = fileReaderAdvent.readFileAsMatrix()

    println("[part 1] Grand total: ${part1(input)}")
    println("[part 2] Grand total: ${part2(inputPart2)}")
}

fun part1(input: List<List<String>>): Long {
    var total = 0L

    val cleanedInput = input.map { row ->
        row.filter {
            it.isNotBlank()
        }
    }

    val transposed = cleanedInput[0].indices.map { col ->
        cleanedInput.map { row -> row[col] }
    }

    for (x in transposed.indices) {
        var rowsResult = 0L
        val rowsOperation = transposed[x].drop(transposed[x].size - 1)[0]
        for (y in 0 .. transposed[x].size - 2) {
            when (rowsOperation) {
                "+" -> rowsResult += transposed[x][y].toLong()
                "*" -> {
                    if (rowsResult == 0L) {
                        rowsResult = 1L
                    }
                    rowsResult *= transposed[x][y].toLong()
                }
            }
        }
        total += rowsResult
    }

    return total
}

fun part2(input: List<List<String>>): Long {
    var total = 0L

    val paddedInput = padRows(input)

    val transposed = paddedInput[0].indices.map { col ->
        paddedInput.map { row -> row[col] }
    }

    var newBlock = true
    var operation = ""
    var rowResult = 0L
    for (row in transposed){

        if(!row.any {it.isNotBlank()}){
            newBlock = true
            continue
        }

        if(newBlock){
            newBlock = false
            total += rowResult
            rowResult = 0L
            operation = row.takeLast(1)[0]
        }

        when (operation) {
            "+" -> rowResult += row.dropLast(1).filter { it.isNotBlank() }.joinToString("").toLong()
            "*" -> {
                if (rowResult == 0L) {
                    rowResult = 1L
                }
                rowResult *= row.dropLast(1).filter { it.isNotBlank() }.joinToString("").toLong()
            }
        }

    }
    total += rowResult

    return total
}

fun padRows(input: List<List<String>>): List<List<String>> {
    val maxLength = input.maxOf { it.size }
    return input.map { row ->
        if(row.size < maxLength){
            row + List(maxLength - row.size) { "" }
        } else {
            row
        }
    }
}