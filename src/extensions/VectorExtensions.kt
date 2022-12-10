package extensions

import kotlin.math.sign
import kotlin.math.sqrt

typealias Vector2 = Pair<Int, Int>
val Vector2.x: Int get() = first
val Vector2.y: Int get() = second

infix operator fun Vector2.plus(other: Vector2) = x + other.x to y + other.y
infix operator fun Vector2.minus(other: Vector2) = x - other.x to y - other.y
infix operator fun Vector2.times(other: Vector2) = x * other.x to y * other.y
infix operator fun Vector2.div(other: Vector2) = x / other.x to y / other.y

operator fun Vector2.unaryMinus() = -x to -y

infix operator fun Vector2.times(value: Double) = x * value to y * value
infix operator fun Vector2.div(value: Double) = x / value to y / value

//infix operator fun Vector2.equals(other: Vector2) {
//    val num1 = x - other.x;
//    val num2 = y - other.y;
//    return (num1 * num1 + num2 * num2).toDouble() < 9.999999439624929E-11;
//}

val Vector2.sign: Vector2 get() = x.sign to y.sign

fun Vector2.distance(a: Vector2, b: Vector2): Double {
    val num1 = a.x - b.x
    val num2 = a.y - b.y
    return sqrt(((num1 * num1) + num2 * num2).toDouble())
}

val Vector2.zero: Vector2 get() = Vector2(0, 0);
val Vector2.one: Vector2 get() = Vector2(1, 1);
val Vector2.up: Vector2 get() = Vector2(0, 1);
val Vector2.down: Vector2 get() = Vector2(0, -1);
val Vector2.left: Vector2 get() = Vector2(-1, 0);
val Vector2.right: Vector2 get() = Vector2(1, 0);
