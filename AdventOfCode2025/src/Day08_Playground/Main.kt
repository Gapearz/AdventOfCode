package Day08_Playground

import java.util.PriorityQueue
import kotlin.math.pow
import kotlin.math.sqrt

fun main(){
    val fileReaderAdvent = Tools.FileReaderAdvent("src/Day08_Playground/input.txt")
    val input = fileReaderAdvent.readFile()

    val positions = input.map {
        val coords = it.split(",")
        Triple(coords[0].toInt(), coords[1].toInt(), coords[2].toInt())
    }

    println("[part 1] Multi of biggest 3: ${part1(positions)}")
    println("[part 2] Multi of x of the last 2: ${part2(positions)}")
}

fun part1(positions: List<Triple<Int, Int, Int>>): Long {
    var multiOfLargest = 1L
    val connectedBlocks = mutableListOf<MutableList<Int>>()
    var neededConnections = 1000

    val distanceQueue = PriorityQueue<Pair<Pair<Int, Int>, Double>>(compareBy { it.second })

    for (i in 0 until positions.size - 1) {
        for (j in i + 1 until positions.size) {
            distanceQueue.add((i to j) to getDistance(positions[i], positions[j]))
        }
    }

    while (neededConnections > 0 && distanceQueue.isNotEmpty()) {
        val (shortestConnection, _) = distanceQueue.poll()

        if (isAlreadyConnected(connectedBlocks, shortestConnection.first, shortestConnection.second)){
            neededConnections--
            continue
        }

        if (alreadyHasBlock(connectedBlocks, shortestConnection.first) && alreadyHasBlock(connectedBlocks, shortestConnection.second)) {
            for (blockSet in connectedBlocks) {
                if (blockSet.contains(shortestConnection.second)) {
                    val blockSetToMerge = connectedBlocks.first { it.contains(shortestConnection.first) }
                    blockSet.addAll(blockSetToMerge)
                    connectedBlocks.remove(blockSetToMerge)
                    neededConnections--
                    break
                }
            }
        } else if (alreadyHasBlock(connectedBlocks, shortestConnection.first) || alreadyHasBlock(connectedBlocks, shortestConnection.second)) {
            val blockToAdd = if (alreadyHasBlock(connectedBlocks, shortestConnection.first)) shortestConnection.second else shortestConnection.first
            for (blockSet in connectedBlocks) {
                if (blockSet.contains(if (alreadyHasBlock(connectedBlocks, shortestConnection.first)) shortestConnection.first else shortestConnection.second)) {
                    blockSet.add(blockToAdd)
                    neededConnections--
                    break
                }
            }
        } else {
            connectedBlocks.add(mutableListOf(shortestConnection.first, shortestConnection.second))
            neededConnections--
        }
    }

    val largest3 = connectedBlocks.map { it.size }.sortedDescending().take(3)
    for (size in largest3) {
        multiOfLargest *= size.toLong()
    }

    return multiOfLargest
}

fun part2(positions: List<Triple<Int, Int, Int>>): Long{
    val connectedBlocks = mutableListOf<MutableList<Int>>()

    val distanceQueue = PriorityQueue<Pair<Pair<Int, Int>, Double>>(compareBy { it.second })

    for (i in 0 until positions.size - 1) {
        for (j in i + 1 until positions.size) {
            distanceQueue.add((i to j) to getDistance(positions[i], positions[j]))
        }
    }

    while (true) {
        val (shortestConnection, _) = distanceQueue.poll()

        if (isAlreadyConnected(connectedBlocks, shortestConnection.first, shortestConnection.second)){
            continue
        } else if (alreadyHasBlock(connectedBlocks, shortestConnection.first) && alreadyHasBlock(connectedBlocks, shortestConnection.second)) {
            for (blockSet in connectedBlocks) {
                if (blockSet.contains(shortestConnection.second)) {
                    val blockSetToMerge = connectedBlocks.first { it.contains(shortestConnection.first) }
                    blockSet.addAll(blockSetToMerge)
                    connectedBlocks.remove(blockSetToMerge)
                    break
                }
            }
        } else if (alreadyHasBlock(connectedBlocks, shortestConnection.first) || alreadyHasBlock(connectedBlocks, shortestConnection.second)) {
            val blockToAdd = if (alreadyHasBlock(connectedBlocks, shortestConnection.first)) shortestConnection.second else shortestConnection.first
            for (blockSet in connectedBlocks) {
                if (blockSet.contains(if (alreadyHasBlock(connectedBlocks, shortestConnection.first)) shortestConnection.first else shortestConnection.second)) {
                    blockSet.add(blockToAdd)
                    break
                }
            }
        } else {
            connectedBlocks.add(mutableListOf(shortestConnection.first, shortestConnection.second))
        }

        if(connectedBlocks.size == 1 && connectedBlocks[0].size == positions.size){
            return positions[shortestConnection.first].first.toLong() * positions[shortestConnection.second].first.toLong()
        }
    }
    return -1L
}

fun getDistance(a: Triple<Int, Int, Int>, b: Triple<Int, Int, Int>): Double {
    return sqrt(((a.first - b.first).toDouble()).pow(2) + ((a.second - b.second).toDouble()).pow(2) + ((a.third - b.third).toDouble()).pow(2))
}

fun isAlreadyConnected(connectedBlocks: MutableList<MutableList<Int>>, blockA: Int, blockB: Int): Boolean{
    for(blockSet in connectedBlocks){
        if(blockSet.contains(blockA) && blockSet.contains(blockB)){
            return true
        }
    }
    return false
}

fun alreadyHasBlock(connectedBlocks: MutableList<MutableList<Int>>, block: Int): Boolean{
    for(blockSet in connectedBlocks){
        if(blockSet.contains(block)){
            return true
        }
    }
    return false
}