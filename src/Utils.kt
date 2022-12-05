import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')


fun readTestInputForDay(dayNumber: Int): String {
    val dayString = dayNumber.toString().padStart(2, '0')
    return File("src/day$dayString" , "Day${dayString}_test.txt").readText()
}

fun readInputForDay(dayNumber: Int): String {
    val dayString = dayNumber.toString().padStart(2, '0')
    return File("src/day$dayString" , "Day${dayString}.txt").readText()
}
