import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.get

fun Number.toI() = toInt()
fun Number.toF() = toFloat()
fun Number.toD() = toDouble()
fun Number.toL() = toLong()

// Arithmetic

data class I2L(val src: Reg, val dst: Reg) : FLInst({ settingL(dst, i(src).toL()) })
data class L2I(val src: Reg, val dst: Reg) : FLInst({ settingI(dst, l(src).toI()) })
data class I2F(val src: Reg, val dst: Reg) : FLInst({ settingF(dst, i(src).toF()) })
data class F2I(val src: Reg, val dst: Reg) : FLInst({ settingI(dst, f(src).toI()) })
data class I2D(val src: Reg, val dst: Reg) : FLInst({ settingD(dst, i(src).toD()) })
data class D2I(val src: Reg, val dst: Reg) : FLInst({ settingI(dst, d(src).toI()) })
data class L2F(val src: Reg, val dst: Reg) : FLInst({ settingF(dst, l(src).toF()) })
data class F2L(val src: Reg, val dst: Reg) : FLInst({ settingL(dst, f(src).toL()) })
data class L2D(val src: Reg, val dst: Reg) : FLInst({ settingD(dst, l(src).toD()) })
data class D2L(val src: Reg, val dst: Reg) : FLInst({ settingL(dst, d(src).toL()) })
data class F2D(val src: Reg, val dst: Reg) : FLInst({ settingD(dst, f(src).toD()) })
data class D2F(val src: Reg, val dst: Reg) : FLInst({ settingF(dst, d(src).toF()) })

data class IAdd_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, i(a) + i(b)) })
data class LAdd_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, l(a) + l(b)) })
data class FAdd_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, f(a) + f(b)) })
data class DAdd_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, d(a) + d(b)) })
data class IAdd_ri(val a: Reg, val b: I, val dst: Reg) : FLInst({ settingI(dst, i(a) + b) })
data class LAdd_ri(val a: Reg, val b: L, val dst: Reg) : FLInst({ settingL(dst, l(a) + b) })
data class FAdd_ri(val a: Reg, val b: F, val dst: Reg) : FLInst({ settingF(dst, f(a) + b) })
data class DAdd_ri(val a: Reg, val b: D, val dst: Reg) : FLInst({ settingD(dst, d(a) + b) })

data class ISub_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, i(a) - i(b)) })
data class LSub_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, l(a) - l(b)) })
data class FSub_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, f(a) - f(b)) })
data class DSub_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, d(a) - d(b)) })
data class ISub_ri(val a: Reg, val b: I, val dst: Reg) : FLInst({ settingI(dst, i(a) - b) })
data class LSub_ri(val a: Reg, val b: L, val dst: Reg) : FLInst({ settingL(dst, l(a) - b) })
data class FSub_ri(val a: Reg, val b: F, val dst: Reg) : FLInst({ settingF(dst, f(a) - b) })
data class DSub_ri(val a: Reg, val b: D, val dst: Reg) : FLInst({ settingD(dst, d(a) - b) })
data class ISub_ir(val a: I, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, a - i(b)) })
data class LSub_ir(val a: L, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, a - l(b)) })
data class FSub_ir(val a: F, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, a - f(b)) })
data class DSub_ir(val a: D, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, a - d(b)) })

data class IMul_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, i(a) * i(b)) })
data class LMul_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, l(a) * l(b)) })
data class FMul_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, f(a) * f(b)) })
data class DMul_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, d(a) * d(b)) })
data class IMul_ri(val a: Reg, val b: I, val dst: Reg) : FLInst({ settingI(dst, i(a) * b) })
data class LMul_ri(val a: Reg, val b: L, val dst: Reg) : FLInst({ settingL(dst, l(a) * b) })
data class FMul_ri(val a: Reg, val b: F, val dst: Reg) : FLInst({ settingF(dst, f(a) * b) })
data class DMul_ri(val a: Reg, val b: D, val dst: Reg) : FLInst({ settingD(dst, d(a) * b) })

data class IDiv_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, i(a) / i(b)) })
data class LDiv_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, l(a) / l(b)) })
data class FDiv_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, f(a) / f(b)) })
data class DDiv_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, d(a) / d(b)) })
data class IDiv_ri(val a: Reg, val b: I, val dst: Reg) : FLInst({ settingI(dst, i(a) / b) })
data class LDiv_ri(val a: Reg, val b: L, val dst: Reg) : FLInst({ settingL(dst, l(a) / b) })
data class FDiv_ri(val a: Reg, val b: F, val dst: Reg) : FLInst({ settingF(dst, f(a) / b) })
data class DDiv_ri(val a: Reg, val b: D, val dst: Reg) : FLInst({ settingD(dst, d(a) / b) })
data class IDiv_ir(val a: I, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, a / i(b)) })
data class LDiv_ir(val a: L, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, a / l(b)) })
data class FDiv_ir(val a: F, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, a / f(b)) })
data class DDiv_ir(val a: D, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, a / d(b)) })

data class IRem_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, i(a) % i(b)) })
data class LRem_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, l(a) % l(b)) })
data class FRem_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, f(a) % f(b)) })
data class DRem_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, d(a) % d(b)) })
data class IRem_ri(val a: Reg, val b: I, val dst: Reg) : FLInst({ settingI(dst, i(a) % b) })
data class LRem_ri(val a: Reg, val b: L, val dst: Reg) : FLInst({ settingL(dst, l(a) % b) })
data class FRem_ri(val a: Reg, val b: F, val dst: Reg) : FLInst({ settingF(dst, f(a) % b) })
data class DRem_ri(val a: Reg, val b: D, val dst: Reg) : FLInst({ settingD(dst, d(a) % b) })
data class IRem_ir(val a: I, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, a % i(b)) })
data class LRem_ir(val a: L, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, a % l(b)) })
data class FRem_ir(val a: F, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, a % f(b)) })
data class DRem_ir(val a: D, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, a % d(b)) })

data class INeg_rr(val src: Reg, val dst: Reg) : FLInst({ settingI(dst, -i(src)) })
data class LNeg_rr(val src: Reg, val dst: Reg) : FLInst({ settingL(dst, -l(src)) })
data class FNeg_rr(val src: Reg, val dst: Reg) : FLInst({ settingF(dst, -f(src)) })
data class DNeg_rr(val src: Reg, val dst: Reg) : FLInst({ settingD(dst, -d(src)) })

data class IL_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) < i(right)) })
data class ILe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) <= i(right)) })
data class IG_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) > i(right)) })
data class IGe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) >= i(right)) })
data class IEq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) == i(right)) })
data class INeq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) != i(right)) })
data class LL_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, l(left) < l(right)) })
data class LLe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, l(left) <= l(right)) })
data class LG_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, l(left) > l(right)) })
data class LGe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, l(left) >= l(right)) })
data class LEq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, l(left) == l(right)) })
data class LNeq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, l(left) != l(right)) })
data class FL_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, f(left) < f(right)) })
data class FLe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, f(left) <= f(right)) })
data class FG_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, f(left) > f(right)) })
data class FGe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, f(left) >= f(right)) })
data class FEq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, f(left) == f(right)) })
data class FNeq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, f(left) != f(right)) })
data class DL_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, d(left) < d(right)) })
data class DLe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, d(left) <= d(right)) })
data class DG_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, d(left) > d(right)) })
data class DGe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, d(left) >= d(right)) })
data class DEq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, d(left) == d(right)) })
data class DNeq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, d(left) != d(right)) })
data class IL_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) < right) })
data class ILe_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) <= right) })
data class IG_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) > right) })
data class IGe_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) >= right) })
data class IEq_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) == right) })
data class INeq_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) != right) })
data class LL_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingI(dst, l(left) < right) })
data class LLe_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingI(dst, l(left) <= right) })
data class LG_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingI(dst, l(left) > right) })
data class LGe_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingI(dst, l(left) >= right) })
data class LEq_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingI(dst, l(left) == right) })
data class LNeq_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingI(dst, l(left) != right) })
data class FL_ri(val left: Reg, val right: F, val dst: Reg) : FLInst({ settingI(dst, f(left) < right) })
data class FLe_ri(val left: Reg, val right: F, val dst: Reg) : FLInst({ settingI(dst, f(left) <= right) })
data class FG_ri(val left: Reg, val right: F, val dst: Reg) : FLInst({ settingI(dst, f(left) > right) })
data class FGe_ri(val left: Reg, val right: F, val dst: Reg) : FLInst({ settingI(dst, f(left) >= right) })
data class FEq_ri(val left: Reg, val right: F, val dst: Reg) : FLInst({ settingI(dst, f(left) == right) })
data class FNeq_ri(val left: Reg, val right: F, val dst: Reg) : FLInst({ settingI(dst, f(left) != right) })
data class DL_ri(val left: Reg, val right: D, val dst: Reg) : FLInst({ settingI(dst, d(left) < right) })
data class DLe_ri(val left: Reg, val right: D, val dst: Reg) : FLInst({ settingI(dst, d(left) <= right) })
data class DG_ri(val left: Reg, val right: D, val dst: Reg) : FLInst({ settingI(dst, d(left) > right) })
data class DGe_ri(val left: Reg, val right: D, val dst: Reg) : FLInst({ settingI(dst, d(left) >= right) })
data class DEq_ri(val left: Reg, val right: D, val dst: Reg) : FLInst({ settingI(dst, d(left) == right) })
data class DNeq_ri(val left: Reg, val right: D, val dst: Reg) : FLInst({ settingI(dst, d(left) != right) })

data class IAnd_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) and i(right)) })
data class IOr_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) or i(right)) })
data class IXor_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) xor i(right)) })
data class LAnd_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, l(left) and l(right)) })
data class LOr_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, l(left) or l(right)) })
data class LXor_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, l(left) xor l(right)) })
data class IAnd_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) and right) })
data class IOr_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) or right) })
data class IXor_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) xor right) })
data class LAnd_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingL(dst, l(left) and right) })
data class LOr_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingL(dst, l(left) or right) })
data class LXor_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingL(dst, l(left) xor right) })

data class IInv(val src: Reg, val dst: Reg) : FLInst({ settingI(dst, i(src).inv()) })
data class LInv(val src: Reg, val dst: Reg) : FLInst({ settingL(dst, l(src).inv()) })

data class IShl_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) shl i(right)) })
data class IShr_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) shr i(right)) })
data class IUshr_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) ushr i(right)) })
data class LShl_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, l(left) shl i(right)) })
data class LShr_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, l(left) shr i(right)) })
data class LUshr_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, l(left) ushr i(right)) })
data class IShl_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) shl right) })
data class IShr_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) shr right) })
data class IUshr_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) ushr right) })
data class LShl_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingL(dst, l(left) shl right) })
data class LShr_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingL(dst, l(left) shr right) })
data class LUshr_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingL(dst, l(left) ushr right) })
data class IShl_ir(val left: I, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, left shl i(right)) })
data class IShr_ir(val left: I, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, left shr i(right)) })
data class IUshr_ir(val left: I, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, left ushr i(right)) })
data class LShl_ir(val left: L, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, left shl i(right)) })
data class LShr_ir(val left: L, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, left shr i(right)) })
data class LUshr_ir(val left: L, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, left ushr i(right)) })

sealed class HeapInstruction(func: context(PersistentHeap) Frame.() -> Pair<PersistentHeap, Frame>) : Instruction({
    val (newHeap, newFrame) = with(heap) { lastFrame().func() }
    withLastFrame { newFrame }.withHeap { newHeap }
})

data class Alloc_r(val amount: Reg, val dst: Reg) : HeapInstruction({
    val (addr, newHeap) = alloc(l(amount))
    newHeap to settingL(dst, addr)
})

data class Alloc_i(val amount: L, val dst: Reg) : HeapInstruction({
    val (addr, newHeap) = alloc(amount)
    newHeap to settingL(dst, addr)
})

data class Free(val addr: Reg) : HeapInstruction({
    free(l(addr)) to this
})

data class BAStore_rrr(val addr: Reg, val shift: Reg, val value: Reg) : HeapInstruction({
    store(l(addr) + i(shift), i(value).toByte().toBytes()) to this
})

data class BAStore_rir(val addr: Reg, val shift: I, val value: Reg) : HeapInstruction({
    store(l(addr) + shift, i(value).toByte().toBytes()) to this
})

data class BAStore_rri(val addr: Reg, val shift: Reg, val value: B) : HeapInstruction({
    store(l(addr) + i(shift), value.toByte().toBytes()) to this
})

data class BAStore_rii(val addr: Reg, val shift: I, val value: B) : HeapInstruction({
    store(l(addr) + shift, value.toByte().toBytes()) to this
})

data class CAStore_rrr(val addr: Reg, val shift: Reg, val value: Reg) : HeapInstruction({
    store(l(addr) + i(shift), i(value).toShort().toBytes()) to this
})

data class CAStore_rir(val addr: Reg, val shift: I, val value: Reg) : HeapInstruction({
    store(l(addr) + shift, i(value).toShort().toBytes()) to this
})

data class CAStore_rri(val addr: Reg, val shift: Reg, val value: C) : HeapInstruction({
    store(l(addr) + i(shift), value.toShort().toBytes()) to this
})

data class CAStore_rii(val addr: Reg, val shift: I, val value: C) : HeapInstruction({
    store(l(addr) + shift, value.toShort().toBytes()) to this
})

data class IAStore_rrr(val addr: Reg, val shift: Reg, val value: Reg) : HeapInstruction({
    store(l(addr) + i(shift), i(value).toBytes()) to this
})

data class IAStore_rir(val addr: Reg, val shift: I, val value: Reg) : HeapInstruction({
    store(l(addr) + shift, i(value).toBytes()) to this
})

data class IAStore_rri(val addr: Reg, val shift: Reg, val value: I) : HeapInstruction({
    store(l(addr) + i(shift), value.toBytes()) to this
})

data class IAStore_rii(val addr: Reg, val shift: I, val value: I) : HeapInstruction({
    store(l(addr) + shift, value.toBytes()) to this
})

data class LAStore_rrr(val addr: Reg, val shift: Reg, val value: Reg) : HeapInstruction({
    store(l(addr) + i(shift), l(value).toBytes()) to this
})

data class LAStore_rir(val addr: Reg, val shift: I, val value: Reg) : HeapInstruction({
    store(l(addr) + shift, l(value).toBytes()) to this
})

data class LAStore_rri(val addr: Reg, val shift: Reg, val value: L) : HeapInstruction({
    store(l(addr) + i(shift), value.toBytes()) to this
})

data class LAStore_rii(val addr: Reg, val shift: I, val value: L) : HeapInstruction({
    store(l(addr) + shift, value.toBytes()) to this
})

data class FAStore_rrr(val addr: Reg, val shift: Reg, val value: Reg) : HeapInstruction({
    store(l(addr) + i(shift), f(value).toBytes()) to this
})

data class FAStore_rir(val addr: Reg, val shift: I, val value: Reg) : HeapInstruction({
    store(l(addr) + shift, f(value).toBytes()) to this
})

data class FAStore_rri(val addr: Reg, val shift: Reg, val value: F) : HeapInstruction({
    store(l(addr) + i(shift), value.toBytes()) to this
})

data class FAStore_rii(val addr: Reg, val shift: I, val value: F) : HeapInstruction({
    store(l(addr) + shift, value.toBytes()) to this
})

data class DAStore_rrr(val addr: Reg, val shift: Reg, val value: Reg) : HeapInstruction({
    store(l(addr) + i(shift), d(value).toBytes()) to this
})

data class DAStore_rir(val addr: Reg, val shift: I, val value: Reg) : HeapInstruction({
    store(l(addr) + shift, d(value).toBytes()) to this
})

data class DAStore_rri(val addr: Reg, val shift: Reg, val value: D) : HeapInstruction({
    store(l(addr) + i(shift), value.toBytes()) to this
})

data class DAStore_rii(val addr: Reg, val shift: I, val value: D) : HeapInstruction({
    store(l(addr) + shift, value.toBytes()) to this
})

data class BALoad_rr(val addr: Reg, val shift: Reg, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingI(dst, load(l(addr) + i(shift), 1).toB().toInt())
})

data class BALoad_ri(val addr: Reg, val shift: I, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingI(dst, load(l(addr) + shift, 1).toB().toInt())
})


data class CALoad_rr(val addr: Reg, val shift: Reg, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingI(dst, load(l(addr) + i(shift), 2).toC().toInt())
})

data class CALoad_ri(val addr: Reg, val shift: I, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingI(dst, load(l(addr) + shift, 2).toC().toInt())
})


data class IALoad_rr(val addr: Reg, val shift: Reg, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingI(dst, load(l(addr) + i(shift), 4).toI())
})

data class IALoad_ri(val addr: Reg, val shift: I, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingI(dst, load(l(addr) + shift, 4).toI())
})


data class LALoad_rr(val addr: Reg, val shift: Reg, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingL(dst, load(l(addr) + i(shift), 8).toL())
})

data class LALoad_ri(val addr: Reg, val shift: I, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingL(dst, load(l(addr) + shift, 8).toL())
})


data class FALoad_rr(val addr: Reg, val shift: Reg, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingF(dst, load(l(addr) + i(shift), 4).toF())
})

data class FALoad_ri(val addr: Reg, val shift: I, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingF(dst, load(l(addr) + shift, 4).toF())
})


data class DALoad_rr(val addr: Reg, val shift: Reg, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingD(dst, load(l(addr) + i(shift), 8).toD())
})

data class DALoad_ri(val addr: Reg, val shift: I, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingD(dst, load(l(addr) + shift, 8).toD())
})

sealed class JumpIfInstruction(label: String, func: Frame.() -> Boolean) : FLInst({
    if (func()) copy(ip = labels[label]!! - 1) else this
})

data class Goto(val label: String) : JumpIfInstruction(label, { true })

data class IfIZ(val label: String, val src: Reg) : JumpIfInstruction(label, { i(src) == 0 })
data class IfLZ(val label: String, val src: Reg) : JumpIfInstruction(label, { l(src) == 0L })
data class IfFZ(val label: String, val src: Reg) : JumpIfInstruction(label, { f(src) == 0F })
data class IfDZ(val label: String, val src: Reg) : JumpIfInstruction(label, { d(src) == .0 })
data class IfInZ(val label: String, val src: Reg) : JumpIfInstruction(label, { i(src) != 0 })
data class IfLnZ(val label: String, val src: Reg) : JumpIfInstruction(label, { l(src) != 0L })
data class IfFnZ(val label: String, val src: Reg) : JumpIfInstruction(label, { f(src) != 0F })
data class IfDnZ(val label: String, val src: Reg) : JumpIfInstruction(label, { d(src) != .0 })

sealed class ReturnInstruction<T>(src: Reg, val getter: Frame.(Reg) -> T, val settingT: Frame.(Reg, T) -> Frame) :
    Instruction({
        // Return value
        val returned = lastFrame().getter(src)
        // Pop last execution frame
        val newStack = frameStack.removeAt(frameStack.size - 1)
        val newLast = newStack.last()
        // Set destination
        copy(frameStack = newStack)
            .withLastFrame {
                settingT(
                    (newLast.first.instructions[ip] as CallInstruction).dst,
                    returned
                )
            }
    })

data class IRet(val src: Reg) : ReturnInstruction<I>(src, Frame::i, Frame::settingI)
data class LRet(val src: Reg) : ReturnInstruction<L>(src, Frame::l, Frame::settingL)
data class FRet(val src: Reg) : ReturnInstruction<F>(src, Frame::f, Frame::settingF)
data class DRet(val src: Reg) : ReturnInstruction<D>(src, Frame::d, Frame::settingD)
data object Ret : ReturnInstruction<Unit>(-1, { }, { _, _ -> this })

sealed class CallInstruction(func: context(Program) Memory.() -> RuneFunction, args: List<Reg>) : Instruction({
    val function = func()
    var newFrame = Frame().copy(ip = -1)
    var iArgs = 0
    var lArgs = 0
    var fArgs = 0
    var dArgs = 0
    val lastFrame = lastFrame()
    for ((idx, ty) in function.signature.args.withIndex()) when (ty) {
        'I' -> newFrame = newFrame.settingI(iArgs++, lastFrame.i(args[idx]))
        'L' -> newFrame = newFrame.settingL(lArgs++, lastFrame.l(args[idx]))
        'F' -> newFrame = newFrame.settingF(fArgs++, lastFrame.f(args[idx]))
        'D' -> newFrame = newFrame.settingD(dArgs++, lastFrame.d(args[idx]))
    }
    withStack { add(function to newFrame) }
}) {
    abstract val dst: Reg
}

data class StaticCall(override val dst: Reg, val func: String, val args: List<Reg>) :
    CallInstruction({ implicit<Program>().functions[func]!! }, args)

data class DynamicCall(override val dst: Reg, val func: Reg, val args: List<Reg>) :
    CallInstruction({ implicit<Program>().functionByPointer[(lastFrame().l(func) - 1).toInt()] }, args)

data class IMove_rr(val src: Reg, val dst: Reg) : FLInst({ settingI(dst, i(src)) })
data class LMove_rr(val src: Reg, val dst: Reg) : FLInst({ settingL(dst, l(src)) })
data class FMove_rr(val src: Reg, val dst: Reg) : FLInst({ settingF(dst, f(src)) })
data class DMove_rr(val src: Reg, val dst: Reg) : FLInst({ settingD(dst, d(src)) })

data class IMove_ri(val src: I, val dst: Reg) : FLInst({ settingI(dst, src) })
data class LMove_ri(val src: L, val dst: Reg) : FLInst({ settingL(dst, src) })
data class FMove_ri(val src: F, val dst: Reg) : FLInst({ settingF(dst, src) })
data class DMove_ri(val src: D, val dst: Reg) : FLInst({ settingD(dst, src) })

class PutPxl(val xReg: Reg, val yReg: Reg, val clrReg: Reg) : Instruction({
    withGraphicMemory { set(xReg * SCREEN_WIDTH + yReg, clrReg) }
})

data object ScrFlush : Instruction({
    withDisplay { graphicMemory }
})

data class Rnd(val dstReg: Reg) : FLInst({
    settingI(dstReg, ThreadLocalRandom.current().nextInt())
})

