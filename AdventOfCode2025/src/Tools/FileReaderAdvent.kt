package Tools

import kotlin.toString

public class FileReaderAdvent {
    var file:String? = null
    constructor(file:String){
        this.file = file
    }

    fun readFile(): List<String>{
        return java.io.File(file!!).readLines()
    }

    fun readFileAsIntList(): List<Int>{
        return readFile().map { it.toInt() }
    }

    fun readFileAsLine(splitter: String? = null): List<String> {
        val line = readFile().firstOrNull() ?: return emptyList()
        return if (splitter == null) line.map { it.toString() }
            else line.split(splitter)
    }

    fun readFileAsMatrix(splitter: String? = null): List<List<String>> {
        return readFile().map { line ->
            if (splitter == null) line.map { it.toString() }
            else line.split(splitter)
        }
    }

    fun readFileAsIntMatrix(splitter: String? = null): List<List<Int>> {
        return readFile().map { line ->
            if (splitter == null) line.map { it.toString().toInt() }
            else line.split(splitter).map { it.toInt() }
        }
    }

    fun printMatrix(matrix: List<List<Any>>){
        for (row in matrix){
            println(row)
        }
    }
}