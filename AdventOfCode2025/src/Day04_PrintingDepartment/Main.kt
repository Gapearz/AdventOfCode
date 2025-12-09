package Day04_PrintingDepartment

import kotlin.system.measureTimeMillis

fun main(){
    val fileReaderAdvent = Tools.FileReaderAdvent("src/Day04_PrintingDepartment/input.txt")
    val input = fileReaderAdvent.readFileAsMatrix()

    val time1 = measureTimeMillis {
        val result = part1(input)
        println("[part 1] Accessible Paper Rolls: $result")
    }
    println("Part 1 took: $time1 ms")

    val time2 = measureTimeMillis {
        val result = part2(input.map { it.toMutableList() }.toMutableList())
        println("[part 2] Accessible Paper Rolls: $result")
    }
    println("Part 2 took: $time2 ms")
}

fun part1(input: List<List<String>>): Int{
    var accessiblePaperRolls = 0

    for (i in 0..<input.size){
        for(j in 0..<input[i].size){
            if(input[i][j] == "@"){
                var nearbyRolls = 0
                if(i > 0 && j > 0 && input[i - 1][j - 1] == "@") nearbyRolls++ // top-left
                if(i > 0 && input[i - 1][j] == "@") nearbyRolls++ // top
                if(i > 0 && j < input[i].size - 1 && input[i - 1][j + 1] == "@") nearbyRolls++ // top-right
                if(j > 0 && input[i][j - 1] == "@") nearbyRolls++ // left
                if(j < input[i].size - 1 && input[i][j + 1] == "@") nearbyRolls++ // right
                if(i < input.size - 1 && j > 0 && input[i + 1][j - 1] == "@") nearbyRolls++ // bottom-left
                if(i < input.size - 1 && input[i + 1][j] == "@") nearbyRolls++ // bottom
                if(i < input.size - 1 && j < input[i].size - 1 && input[i + 1][j + 1] == "@") nearbyRolls++ // bottom-right

                if(nearbyRolls < 4){
                    accessiblePaperRolls++
                }
            }
        }
    }

    return accessiblePaperRolls
}

fun part2(input: MutableList<MutableList<String>>): Int {
    var accessiblePaperRolls = 0
    var changesMade: Boolean
    do {
        changesMade = false
        val toReplace = mutableListOf<Pair<Int, Int>>()

        for (i in 0..<input.size){
            for(j in 0..<input[i].size){
                if (input[i][j] == "@") {
                    var nearbyRolls = 0
                    if(i > 0 && j > 0 && input[i - 1][j - 1] == "@") nearbyRolls++ // top-left
                    if(i > 0 && input[i - 1][j] == "@") nearbyRolls++ // top
                    if(i > 0 && j < input[i].size - 1 && input[i - 1][j + 1] == "@") nearbyRolls++ // top-right
                    if(j > 0 && input[i][j - 1] == "@") nearbyRolls++ // left
                    if(j < input[i].size - 1 && input[i][j + 1] == "@") nearbyRolls++ // right
                    if(i < input.size - 1 && j > 0 && input[i + 1][j - 1] == "@") nearbyRolls++ // bottom-left
                    if(i < input.size - 1 && input[i + 1][j] == "@") nearbyRolls++ // bottom
                    if(i < input.size - 1 && j < input[i].size - 1 && input[i + 1][j + 1] == "@") nearbyRolls++ // bottom-right

                    if (nearbyRolls < 4) {
                        accessiblePaperRolls++
                        toReplace.add(i to j)
                    }
                }
            }
        }

        if (toReplace.isNotEmpty()) {
            changesMade = true
            for ((i, j) in toReplace) {
                input[i][j] = "."
            }
        }
    } while (changesMade)

    return accessiblePaperRolls
}