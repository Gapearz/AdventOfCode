package Day11_Reactor

import Tools.FileReaderAdvent
import kotlin.system.measureTimeMillis

fun main(){
    val fileReaderAdvent = FileReaderAdvent("src/Day11_Reactor/input.txt")
    val input = fileReaderAdvent.readFile()

    val time1 = measureTimeMillis {
        val result = part1(input)
        println("[part 1] Different paths available: $result")
    }
    println("Part 1 took: $time1 ms")
    val time2 = measureTimeMillis {
        val result = part2(input)
        println("[part 2] Different paths available going through fft and dac: $result")
    }
    println("Part 2 took: $time2 ms")
}

fun part1(input: List<String>): Long{
    val graph = mutableMapOf<String, MutableList<String>>()

    for (line in input) {
        val from = line.split(":")[0]
        val to = line.split(":")[1].trim().split(" ")

        graph.putIfAbsent(from, mutableListOf())
        graph[from]!!.addAll(to)
    }

    return countPaths(graph, "you", "out")
}

fun part2(input: List<String>): Long{
    val graph = mutableMapOf<String, MutableList<String>>()

    for (line in input) {
        val from = line.split(":")[0]
        val to = line.split(":")[1].trim().split(" ")

        graph.putIfAbsent(from, mutableListOf())
        graph[from]!!.addAll(to)
    }

    var pathsCount = 1L

    val testCheck = countPaths(graph, "fft", "dac")

    if(testCheck == 0L){
        pathsCount *= countPaths(graph, "svr", "dac")
        pathsCount *= countPaths(graph, "dac", "fft")
        pathsCount *= countPaths(graph, "fft", "out")
    } else {
        pathsCount *= countPaths(graph, "svr", "fft")
        pathsCount *= countPaths(graph, "fft", "dac")
        pathsCount *= countPaths(graph, "dac", "out")
    }

    return pathsCount
}

fun countPaths(
    graph: Map<String, List<String>>,
    current: String,
    target: String,
    memo: MutableMap<Pair<String, String>, Long> = mutableMapOf()
): Long {
    if (current == target) {
        return 1
    }

    val key = Pair(current, target)
    memo[key]?.let { return it }

    var paths = 0L
    graph[current]?.forEach { neighbor ->
        paths += countPaths(graph, neighbor, target, memo)
    }

    memo[key] = paths
    return paths
}