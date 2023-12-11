import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.math.absoluteValue

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/resources/$name.txt").readLines()

fun readInputInString(name: String) = Path("src/resources/$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

private fun gcd(
    a: Long,
    b: Long,
): Long {
    return if (b == 0L) a else gcd(b, a % b)
}

fun lcm(
    a: Long,
    b: Long,
): Long {
    if (a == 0L || b == 0L) return 0
    return (a * b).absoluteValue / gcd(a, b)
}

fun lcm(values: List<Long>): Long = values.fold(values.first()) { last, new -> lcm(last, new) }
