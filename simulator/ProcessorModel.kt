import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

const val SCREEN_WIDTH = 512
const val SCREEN_HEIGHT = 256

typealias I = Int
typealias F = Float
typealias D = Double
typealias L = Long
typealias B = Int
typealias C = Int
typealias Reg = Int


private fun uninit(type: String, value: Int): Nothing =
    throw AssertionError("Attempting to access uninitialized $type register ${type}x$value")

data class Frame(
    val ip: Int,
    val iReg: PersistentList<I?>,
    val lReg: PersistentList<L?>,
    val fReg: PersistentList<F?>,
    val dReg: PersistentList<D?>,
) {
    fun settingI(idx: Int, value: I) = copy(iReg = iReg.set(idx, value))
    fun settingI(idx: Int, value: Boolean) = copy(iReg = iReg.set(idx, if (value) 1 else 0))
    fun settingL(idx: Int, value: L) = copy(lReg = lReg.set(idx, value))
    fun settingF(idx: Int, value: F) = copy(fReg = fReg.set(idx, value))
    fun settingD(idx: Int, value: D) = copy(dReg = dReg.set(idx, value))
    fun stepping() = copy(ip = ip + 1)
    fun jumping(loc: Int) = copy(ip = loc)
    fun i(idx: Int) = iReg[idx] ?: uninit("I", idx)
    fun l(idx: Int) = lReg[idx] ?: uninit("L", idx)
    fun f(idx: Int) = fReg[idx] ?: uninit("F", idx)
    fun d(idx: Int) = dReg[idx] ?: uninit("D", idx)
}

private val UninitIReg = List<I?>(4096) { null }.toPersistentList()
private val UninitLReg = List<L?>(4096) { null }.toPersistentList()
private val UninitFReg = List<F?>(4096) { null }.toPersistentList()
private val UninitDReg = List<D?>(4096) { null }.toPersistentList()

fun Frame() = Frame(
    0,
    UninitIReg,
    UninitLReg,
    UninitFReg,
    UninitDReg,
)

data class Memory(
    val heap: PersistentHeap,
    val frameStack: PersistentList<Pair<RuneFunction, Frame>>,
    val graphicMemory: PersistentList<Int>,
    val display: PersistentList<Int>,
) {
    fun withStack(transform: PersistentList<Pair<RuneFunction, Frame>>.() -> PersistentList<Pair<RuneFunction, Frame>>) =
        copy(frameStack = frameStack.transform())

    fun withGraphicMemory(transform: PersistentList<Int>.() -> PersistentList<Int>) =
        copy(graphicMemory = graphicMemory.transform())

    fun withDisplay(transform: PersistentList<Int>.() -> PersistentList<Int>) = copy(display = display.transform())

    fun withHeap(transform: PersistentHeap.() -> PersistentHeap) = copy(heap = heap.transform())
}

data class PersistentHeap(
    val allocated: Long,
    val heap: PersistentTreeMap<Long, PersistentList<Byte>> // Damn that's """"efficient""""
) {
    fun alloc(amount: Long) = allocated to PersistentHeap(
        allocated + amount,
        heap.put(allocated, List<Byte>(amount.toInt()) { 0 }.toPersistentList())
    )

    fun free(address: Long) = copy(heap = heap.without(address))

    private fun find(address: Long, size: Int): Map.Entry<Long, PersistentList<Byte>> {
        val chunk = heap.floor(address)
        if (chunk == null) throw AssertionError("Segmentation Fault: accessing free address $address")
        if (chunk.value.size + chunk.key < address + size) {
            throw AssertionError("Segmentation Fault: accessing addresses $address..${address + size}, beyond the boundaries of a chunk (${chunk.key}..${chunk.key + chunk.value.size}")
        }
        return chunk
    }

    fun store(address: Long, data: ByteArray): PersistentHeap {
        val (start, chunk) = find(address, data.size)

        val shift = (address - start).toInt()
        var modified = chunk
        for ((idx, b) in data.withIndex()) {
            modified = modified.set(idx + shift, b)
        }
        return copy(heap = heap.put(start, modified))
    }

    fun load(address: Long, size: Int): ByteArray {
        val (start, chunk) = find(address, size)
        val shift = (address - start).toInt()
        return ByteArray(size) { chunk[shift + it] }
    }
}

fun Memory.lastFrame() = frameStack.last().second