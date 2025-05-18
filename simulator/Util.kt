fun Byte.toBytes(): ByteArray = byteArrayOf(this)

fun Short.toBytes(): ByteArray = byteArrayOf(
    (this).toByte(),
    (this.toInt() ushr 8).toByte()
)

fun Int.toBytes(): ByteArray = byteArrayOf(
    (this).toByte(),
    (this ushr 8).toByte(),
    (this ushr 16).toByte(),
    (this ushr 24).toByte()
)

fun Long.toBytes(): ByteArray = byteArrayOf(
    (this).toByte(),
    (this ushr 8).toByte(),
    (this ushr 16).toByte(),
    (this ushr 24).toByte(),
    (this ushr 32).toByte(),
    (this ushr 40).toByte(),
    (this ushr 48).toByte(),
    (this ushr 56).toByte()
)

// Conversion from byte arrays
fun ByteArray.toB(): Byte {
    require(size == 1) { "ByteArray must be exactly 1 byte" }
    return this[0]
}

fun ByteArray.toC(): Short {
    require(size == 2) { "ByteArray must be exactly 2 bytes" }
    return ((this[1].toInt() shl 8) or
            (this[0].toInt())).toShort()
}

fun ByteArray.toI(): Int {
    require(size == 4) { "ByteArray must be exactly 4 bytes" }
    return (this[3].toInt() shl 24) or
            (this[2].toInt() shl 16) or
            (this[1].toInt() shl 8) or
            (this[0].toInt())
}

fun ByteArray.toL(): Long {
    require(size == 8) { "ByteArray must be exactly 8 bytes" }
    return (this[7].toLong() shl 56) or
            (this[6].toLong() shl 48) or
            (this[5].toLong() shl 40) or
            (this[4].toLong() shl 32) or
            (this[3].toLong() shl 24) or
            (this[2].toLong() shl 16) or
            (this[1].toLong() shl 8) or
            (this[0].toLong())
}

fun Float.toBytes() = toBits().toBytes()
fun Double.toBytes() = toBits().toBytes()
fun ByteArray.toF() = Float.fromBits(toI())
fun ByteArray.toD() = Double.fromBits(toL())

context(heap: PersistentHeap) fun alloc(amount: Long) = heap.alloc(amount)
context(heap: PersistentHeap) fun free(address: Long) = heap.free(address)
context(heap: PersistentHeap) fun store(address: Long, data: ByteArray) = heap.store(address, data)
context(heap: PersistentHeap) fun load(address: Long, size: Int) = heap.load(address, size)
context(heap: RuneFunction) val instructions: List<Instruction> get() = heap.instructions
context(heap: RuneFunction) val labels: Map<String, Int> get() = heap.labels

context(t: T) fun <T> implicit() = t

