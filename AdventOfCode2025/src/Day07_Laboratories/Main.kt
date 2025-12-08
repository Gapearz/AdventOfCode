package Day07_Laboratories

fun main(){
    val fileReaderAdvent = Tools.FileReaderAdvent("src/Day07_Laboratories/input.txt")
    val input = fileReaderAdvent.readFileAsMatrix().map { it.toMutableList() }.toMutableList()
    val inputCopy = input.map { it.toMutableList() }.toMutableList()

    println("[part 1] Beam was split: ${part1(input)} times")
    println("[part 2] Different timelines: ${part2(inputCopy, input)}")
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

fun part2(input: MutableList<MutableList<String>>, modifiedInput: MutableList<MutableList<String>>): Int{
    input.removeLast()
    input.add(modifiedInput.last())

    val numRows = input.size
    val numCols = input[0].size
    val memo = mutableMapOf<Pair<Int, Int>, Int>()

    fun search(row: Int, col: Int): Int {
        if (row == 0 && input[row][col] == "S") return 1
        if (row == 0) return 0
        val key = Pair(row, col)
        if (memo.containsKey(key)) return memo[key]!!

        val left = if (col > 0) input[row][col - 1] else null
        val right = if (col < numCols - 1) input[row][col + 1] else null
        val me = input[row][col]
        var count = 0

        if (left == "^" && right == "^") {
            count += search(row - 1, col - 1)
            count += search(row - 1, col + 1)
        } else if (left == "^") {
            count += search(row - 1, col - 1)
        } else if (right == "^") {
            count += search(row - 1, col + 1)
        }

        if (me != "^") {
            count += search(row - 1, col)
        }

        memo[key] = count
        return count
    }

    var timelines = 0
    for (col in 0 until numCols) {
        if (input[numRows - 1][col] == "|") {
            timelines += search(numRows - 1, col)
        }
    }

    return timelines
}