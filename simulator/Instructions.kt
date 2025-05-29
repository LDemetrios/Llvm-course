import java.util.concurrent.ThreadLocalRandom

fun Number.toI() = toInt()
fun Number.toF() = toFloat()
fun Number.toD() = toDouble()
fun Number.toL() = toLong()

// Arithmetic

class I2L(val src: Reg, val dst: Reg) : FLInst({ settingL(dst, i(src).toL()) })
class L2I(val src: Reg, val dst: Reg) : FLInst({ settingI(dst, l(src).toI()) })
class I2F(val src: Reg, val dst: Reg) : FLInst({ settingF(dst, i(src).toF()) })
class F2I(val src: Reg, val dst: Reg) : FLInst({ settingI(dst, f(src).toI()) })
class I2D(val src: Reg, val dst: Reg) : FLInst({ settingD(dst, i(src).toD()) })
class D2I(val src: Reg, val dst: Reg) : FLInst({ settingI(dst, d(src).toI()) })
class L2F(val src: Reg, val dst: Reg) : FLInst({ settingF(dst, l(src).toF()) })
class F2L(val src: Reg, val dst: Reg) : FLInst({ settingL(dst, f(src).toL()) })
class L2D(val src: Reg, val dst: Reg) : FLInst({ settingD(dst, l(src).toD()) })
class D2L(val src: Reg, val dst: Reg) : FLInst({ settingL(dst, d(src).toL()) })
class F2D(val src: Reg, val dst: Reg) : FLInst({ settingD(dst, f(src).toD()) })
class D2F(val src: Reg, val dst: Reg) : FLInst({ settingF(dst, d(src).toF()) })

class IAdd_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, i(a) + i(b)) })
class LAdd_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, l(a) + l(b)) })
class FAdd_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, f(a) + f(b)) })
class DAdd_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, d(a) + d(b)) })
class IAdd_ri(val a: Reg, val b: I, val dst: Reg) : FLInst({ settingI(dst, i(a) + b) })
class LAdd_ri(val a: Reg, val b: L, val dst: Reg) : FLInst({ settingL(dst, l(a) + b) })
class FAdd_ri(val a: Reg, val b: F, val dst: Reg) : FLInst({ settingF(dst, f(a) + b) })
class DAdd_ri(val a: Reg, val b: D, val dst: Reg) : FLInst({ settingD(dst, d(a) + b) })

class ISub_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, i(a) - i(b)) })
class LSub_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, l(a) - l(b)) })
class FSub_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, f(a) - f(b)) })
class DSub_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, d(a) - d(b)) })
class ISub_ri(val a: Reg, val b: I, val dst: Reg) : FLInst({ settingI(dst, i(a) - b) })
class LSub_ri(val a: Reg, val b: L, val dst: Reg) : FLInst({ settingL(dst, l(a) - b) })
class FSub_ri(val a: Reg, val b: F, val dst: Reg) : FLInst({ settingF(dst, f(a) - b) })
class DSub_ri(val a: Reg, val b: D, val dst: Reg) : FLInst({ settingD(dst, d(a) - b) })
class ISub_ir(val a: I, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, a - i(b)) })
class LSub_ir(val a: L, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, a - l(b)) })
class FSub_ir(val a: F, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, a - f(b)) })
class DSub_ir(val a: D, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, a - d(b)) })

class IMul_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, i(a) * i(b)) })
class LMul_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, l(a) * l(b)) })
class FMul_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, f(a) * f(b)) })
class DMul_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, d(a) * d(b)) })
class IMul_ri(val a: Reg, val b: I, val dst: Reg) : FLInst({ settingI(dst, i(a) * b) })
class LMul_ri(val a: Reg, val b: L, val dst: Reg) : FLInst({ settingL(dst, l(a) * b) })
class FMul_ri(val a: Reg, val b: F, val dst: Reg) : FLInst({ settingF(dst, f(a) * b) })
class DMul_ri(val a: Reg, val b: D, val dst: Reg) : FLInst({ settingD(dst, d(a) * b) })

class IDiv_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, i(a) / i(b)) })
class LDiv_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, l(a) / l(b)) })
class FDiv_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, f(a) / f(b)) })
class DDiv_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, d(a) / d(b)) })
class IDiv_ri(val a: Reg, val b: I, val dst: Reg) : FLInst({ settingI(dst, i(a) / b) })
class LDiv_ri(val a: Reg, val b: L, val dst: Reg) : FLInst({ settingL(dst, l(a) / b) })
class FDiv_ri(val a: Reg, val b: F, val dst: Reg) : FLInst({ settingF(dst, f(a) / b) })
class DDiv_ri(val a: Reg, val b: D, val dst: Reg) : FLInst({ settingD(dst, d(a) / b) })
class IDiv_ir(val a: I, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, a / i(b)) })
class LDiv_ir(val a: L, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, a / l(b)) })
class FDiv_ir(val a: F, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, a / f(b)) })
class DDiv_ir(val a: D, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, a / d(b)) })

class IRem_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, i(a) % i(b)) })
class LRem_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, l(a) % l(b)) })
class FRem_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, f(a) % f(b)) })
class DRem_rr(val a: Reg, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, d(a) % d(b)) })
class IRem_ri(val a: Reg, val b: I, val dst: Reg) : FLInst({ settingI(dst, i(a) % b) })
class LRem_ri(val a: Reg, val b: L, val dst: Reg) : FLInst({ settingL(dst, l(a) % b) })
class FRem_ri(val a: Reg, val b: F, val dst: Reg) : FLInst({ settingF(dst, f(a) % b) })
class DRem_ri(val a: Reg, val b: D, val dst: Reg) : FLInst({ settingD(dst, d(a) % b) })
class IRem_ir(val a: I, val b: Reg, val dst: Reg) : FLInst({ settingI(dst, a % i(b)) })
class LRem_ir(val a: L, val b: Reg, val dst: Reg) : FLInst({ settingL(dst, a % l(b)) })
class FRem_ir(val a: F, val b: Reg, val dst: Reg) : FLInst({ settingF(dst, a % f(b)) })
class DRem_ir(val a: D, val b: Reg, val dst: Reg) : FLInst({ settingD(dst, a % d(b)) })

class INeg_rr(val src: Reg, val dst: Reg) : FLInst({ settingI(dst, -i(src)) })
class LNeg_rr(val src: Reg, val dst: Reg) : FLInst({ settingL(dst, -l(src)) })
class FNeg_rr(val src: Reg, val dst: Reg) : FLInst({ settingF(dst, -f(src)) })
class DNeg_rr(val src: Reg, val dst: Reg) : FLInst({ settingD(dst, -d(src)) })

class IL_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) < i(right)) })
class ILe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) <= i(right)) })
class IG_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) > i(right)) })
class IGe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) >= i(right)) })
class IEq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) == i(right)) })
class INeq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) != i(right)) })
class LL_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, l(left) < l(right)) })
class LLe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, l(left) <= l(right)) })
class LG_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, l(left) > l(right)) })
class LGe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, l(left) >= l(right)) })
class LEq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, l(left) == l(right)) })
class LNeq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, l(left) != l(right)) })
class FL_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, f(left) < f(right)) })
class FLe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, f(left) <= f(right)) })
class FG_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, f(left) > f(right)) })
class FGe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, f(left) >= f(right)) })
class FEq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, f(left) == f(right)) })
class FNeq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, f(left) != f(right)) })
class DL_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, d(left) < d(right)) })
class DLe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, d(left) <= d(right)) })
class DG_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, d(left) > d(right)) })
class DGe_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, d(left) >= d(right)) })
class DEq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, d(left) == d(right)) })
class DNeq_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, d(left) != d(right)) })
class IL_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) < right) })
class ILe_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) <= right) })
class IG_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) > right) })
class IGe_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) >= right) })
class IEq_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) == right) })
class INeq_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) != right) })
class LL_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingI(dst, l(left) < right) })
class LLe_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingI(dst, l(left) <= right) })
class LG_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingI(dst, l(left) > right) })
class LGe_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingI(dst, l(left) >= right) })
class LEq_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingI(dst, l(left) == right) })
class LNeq_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingI(dst, l(left) != right) })
class FL_ri(val left: Reg, val right: F, val dst: Reg) : FLInst({ settingI(dst, f(left) < right) })
class FLe_ri(val left: Reg, val right: F, val dst: Reg) : FLInst({ settingI(dst, f(left) <= right) })
class FG_ri(val left: Reg, val right: F, val dst: Reg) : FLInst({ settingI(dst, f(left) > right) })
class FGe_ri(val left: Reg, val right: F, val dst: Reg) : FLInst({ settingI(dst, f(left) >= right) })
class FEq_ri(val left: Reg, val right: F, val dst: Reg) : FLInst({ settingI(dst, f(left) == right) })
class FNeq_ri(val left: Reg, val right: F, val dst: Reg) : FLInst({ settingI(dst, f(left) != right) })
class DL_ri(val left: Reg, val right: D, val dst: Reg) : FLInst({ settingI(dst, d(left) < right) })
class DLe_ri(val left: Reg, val right: D, val dst: Reg) : FLInst({ settingI(dst, d(left) <= right) })
class DG_ri(val left: Reg, val right: D, val dst: Reg) : FLInst({ settingI(dst, d(left) > right) })
class DGe_ri(val left: Reg, val right: D, val dst: Reg) : FLInst({ settingI(dst, d(left) >= right) })
class DEq_ri(val left: Reg, val right: D, val dst: Reg) : FLInst({ settingI(dst, d(left) == right) })
class DNeq_ri(val left: Reg, val right: D, val dst: Reg) : FLInst({ settingI(dst, d(left) != right) })

class IAnd_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) and i(right)) })
class IOr_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) or i(right)) })
class IXor_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) xor i(right)) })
class LAnd_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, l(left) and l(right)) })
class LOr_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, l(left) or l(right)) })
class LXor_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, l(left) xor l(right)) })
class IAnd_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) and right) })
class IOr_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) or right) })
class IXor_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) xor right) })
class LAnd_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingL(dst, l(left) and right) })
class LOr_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingL(dst, l(left) or right) })
class LXor_ri(val left: Reg, val right: L, val dst: Reg) : FLInst({ settingL(dst, l(left) xor right) })

class IInv_rr(val src: Reg, val dst: Reg) : FLInst({ settingI(dst, i(src).inv()) })
class LInv_rr(val src: Reg, val dst: Reg) : FLInst({ settingL(dst, l(src).inv()) })

class IShl_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) shl i(right)) })
class IShr_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) shr i(right)) })
class IUshr_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, i(left) ushr i(right)) })
class LShl_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, l(left) shl i(right)) })
class LShr_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, l(left) shr i(right)) })
class LUshr_rr(val left: Reg, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, l(left) ushr i(right)) })
class IShl_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) shl right) })
class IShr_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) shr right) })
class IUshr_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingI(dst, i(left) ushr right) })
class LShl_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingL(dst, l(left) shl right) })
class LShr_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingL(dst, l(left) shr right) })
class LUshr_ri(val left: Reg, val right: I, val dst: Reg) : FLInst({ settingL(dst, l(left) ushr right) })
class IShl_ir(val left: I, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, left shl i(right)) })
class IShr_ir(val left: I, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, left shr i(right)) })
class IUshr_ir(val left: I, val right: Reg, val dst: Reg) : FLInst({ settingI(dst, left ushr i(right)) })
class LShl_ir(val left: L, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, left shl i(right)) })
class LShr_ir(val left: L, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, left shr i(right)) })
class LUshr_ir(val left: L, val right: Reg, val dst: Reg) : FLInst({ settingL(dst, left ushr i(right)) })

sealed class HeapInstruction(func: context(PersistentHeap) Frame.() -> Pair<PersistentHeap, Frame>) : Instruction({
    val (newHeap, newFrame) = with(heap) { lastFrame().func() }
    withLastFrame { newFrame }.withHeap { newHeap }
})

class Alloc_r(val amount: Reg, val dst: Reg) : HeapInstruction({
    val (addr, newHeap) = alloc(l(amount))
    newHeap to settingL(dst, addr)
})

class Alloc_i(val amount: L, val dst: Reg) : HeapInstruction({
    val (addr, newHeap) = alloc(amount)
    newHeap to settingL(dst, addr)
})

class Free(val addr: Reg) : HeapInstruction({
    free(l(addr)) to this
})

class BAStore_rrr(val addr: Reg, val shift: Reg, val value: Reg) : HeapInstruction({
    store(l(addr) + i(shift), i(value).toByte().toBytes()) to this
})

class BAStore_rir(val addr: Reg, val shift: I, val value: Reg) : HeapInstruction({
    store(l(addr) + shift, i(value).toByte().toBytes()) to this
})

class BAStore_rri(val addr: Reg, val shift: Reg, val value: B) : HeapInstruction({
    store(l(addr) + i(shift), value.toByte().toBytes()) to this
})

class BAStore_rii(val addr: Reg, val shift: I, val value: B) : HeapInstruction({
    store(l(addr) + shift, value.toByte().toBytes()) to this
})

class SAStore_rrr(val addr: Reg, val shift: Reg, val value: Reg) : HeapInstruction({
    store(l(addr) + i(shift), i(value).toShort().toBytes()) to this
})

class SAStore_rir(val addr: Reg, val shift: I, val value: Reg) : HeapInstruction({
    store(l(addr) + shift, i(value).toShort().toBytes()) to this
})

class SAStore_rri(val addr: Reg, val shift: Reg, val value: S) : HeapInstruction({
    store(l(addr) + i(shift), value.toShort().toBytes()) to this
})

class SAStore_rii(val addr: Reg, val shift: I, val value: S) : HeapInstruction({
    store(l(addr) + shift, value.toShort().toBytes()) to this
})

class IAStore_rrr(val addr: Reg, val shift: Reg, val value: Reg) : HeapInstruction({
    store(l(addr) + i(shift), i(value).toBytes()) to this
})

class IAStore_rir(val addr: Reg, val shift: I, val value: Reg) : HeapInstruction({
    store(l(addr) + shift, i(value).toBytes()) to this
})

class IAStore_rri(val addr: Reg, val shift: Reg, val value: I) : HeapInstruction({
    store(l(addr) + i(shift), value.toBytes()) to this
})

class IAStore_rii(val addr: Reg, val shift: I, val value: I) : HeapInstruction({
    store(l(addr) + shift, value.toBytes()) to this
})

class LAStore_rrr(val addr: Reg, val shift: Reg, val value: Reg) : HeapInstruction({
    store(l(addr) + i(shift), l(value).toBytes()) to this
})

class LAStore_rir(val addr: Reg, val shift: I, val value: Reg) : HeapInstruction({
    store(l(addr) + shift, l(value).toBytes()) to this
})

class LAStore_rri(val addr: Reg, val shift: Reg, val value: L) : HeapInstruction({
    store(l(addr) + i(shift), value.toBytes()) to this
})

class LAStore_rii(val addr: Reg, val shift: I, val value: L) : HeapInstruction({
    store(l(addr) + shift, value.toBytes()) to this
})

class FAStore_rrr(val addr: Reg, val shift: Reg, val value: Reg) : HeapInstruction({
    store(l(addr) + i(shift), f(value).toBytes()) to this
})

class FAStore_rir(val addr: Reg, val shift: I, val value: Reg) : HeapInstruction({
    store(l(addr) + shift, f(value).toBytes()) to this
})

class FAStore_rri(val addr: Reg, val shift: Reg, val value: F) : HeapInstruction({
    store(l(addr) + i(shift), value.toBytes()) to this
})

class FAStore_rii(val addr: Reg, val shift: I, val value: F) : HeapInstruction({
    store(l(addr) + shift, value.toBytes()) to this
})

class DAStore_rrr(val addr: Reg, val shift: Reg, val value: Reg) : HeapInstruction({
    store(l(addr) + i(shift), d(value).toBytes()) to this
})

class DAStore_rir(val addr: Reg, val shift: I, val value: Reg) : HeapInstruction({
    store(l(addr) + shift, d(value).toBytes()) to this
})

class DAStore_rri(val addr: Reg, val shift: Reg, val value: D) : HeapInstruction({
    store(l(addr) + i(shift), value.toBytes()) to this
})

class DAStore_rii(val addr: Reg, val shift: I, val value: D) : HeapInstruction({
    store(l(addr) + shift, value.toBytes()) to this
})

class BALoad_rr(val addr: Reg, val shift: Reg, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingI(dst, load(l(addr) + i(shift), 1).toB().toInt())
})

class BALoad_ri(val addr: Reg, val shift: I, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingI(dst, load(l(addr) + shift, 1).toB().toInt())
})


class SALoad_rr(val addr: Reg, val shift: Reg, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingI(dst, load(l(addr) + i(shift), 2).toC().toInt())
})

class SALoad_ri(val addr: Reg, val shift: I, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingI(dst, load(l(addr) + shift, 2).toC().toInt())
})


class IALoad_rr(val addr: Reg, val shift: Reg, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingI(dst, load(l(addr) + i(shift), 4).toI())
})

class IALoad_ri(val addr: Reg, val shift: I, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingI(dst, load(l(addr) + shift, 4).toI())
})


class LALoad_rr(val addr: Reg, val shift: Reg, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingL(dst, load(l(addr) + i(shift), 8).toL())
})

class LALoad_ri(val addr: Reg, val shift: I, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingL(dst, load(l(addr) + shift, 8).toL())
})


class FALoad_rr(val addr: Reg, val shift: Reg, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingF(dst, load(l(addr) + i(shift), 4).toF())
})

class FALoad_ri(val addr: Reg, val shift: I, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingF(dst, load(l(addr) + shift, 4).toF())
})


class DALoad_rr(val addr: Reg, val shift: Reg, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingD(dst, load(l(addr) + i(shift), 8).toD())
})

class DALoad_ri(val addr: Reg, val shift: I, val dst: Reg) : HeapInstruction({
    implicit<PersistentHeap>() to settingD(dst, load(l(addr) + shift, 8).toD())
})

sealed class JumpIfInstruction(label: Int, func: Frame.() -> Boolean) : FLInst({
    if (func()) copy(ip = label - 1) else this
})

class Goto(val label: Int) : JumpIfInstruction(label, { true })

class IfIZ(val src: Reg, val label: Int) : JumpIfInstruction(label, { i(src) == 0 })
class IfLZ(val src: Reg, val label: Int) : JumpIfInstruction(label, { l(src) == 0L })
class IfFZ(val src: Reg, val label: Int) : JumpIfInstruction(label, { f(src) == 0F })
class IfDZ(val src: Reg, val label: Int) : JumpIfInstruction(label, { d(src) == .0 })
class IfInZ(val src: Reg, val label: Int) : JumpIfInstruction(label, { i(src) != 0 })
class IfLnZ(val src: Reg, val label: Int) : JumpIfInstruction(label, { l(src) != 0L })
class IfFnZ(val src: Reg, val label: Int) : JumpIfInstruction(label, { f(src) != 0F })
class IfDnZ(val src: Reg, val label: Int) : JumpIfInstruction(label, { d(src) != .0 })

class TerminationException(val code: Any?) : Exception()

sealed class ReturnInstruction<T>(src: Reg, val getter: Frame.(Reg) -> T, val settingT: Frame.(Reg, T) -> Frame) :
    Instruction({
        // Return value
        val returned = lastFrame().getter(src)
        // Pop last execution frame
        val newStack = frameStack.removeAt(frameStack.size - 1)
        val newLast = newStack.lastOrNull() ?: throw TerminationException(returned)
        // Set destination
        copy(frameStack = newStack)
            .withLastFrame {
                settingT(
                    (newLast.first.instructions[ip] as CallInstruction).dst,
                    returned
                )
            }
    })

class IRet(val src: Reg) : ReturnInstruction<I>(src, Frame::i, Frame::settingI)
class LRet(val src: Reg) : ReturnInstruction<L>(src, Frame::l, Frame::settingL)
class FRet(val src: Reg) : ReturnInstruction<F>(src, Frame::f, Frame::settingF)
class DRet(val src: Reg) : ReturnInstruction<D>(src, Frame::d, Frame::settingD)
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

class StaticCall(override val dst: Reg, val func: String, val args: List<Reg>) :
    CallInstruction({ implicit<Program>().functions[func]!! }, args)

class DynamicCall(override val dst: Reg, val func: Reg, val args: List<Reg>) :
    CallInstruction({ implicit<Program>().functionByPointer[(lastFrame().l(func) - 1).toInt()] }, args)

class IMove_rr(val src: Reg, val dst: Reg) : FLInst({ settingI(dst, i(src)) })
class LMove_rr(val src: Reg, val dst: Reg) : FLInst({ settingL(dst, l(src)) })
class FMove_rr(val src: Reg, val dst: Reg) : FLInst({ settingF(dst, f(src)) })
class DMove_rr(val src: Reg, val dst: Reg) : FLInst({ settingD(dst, d(src)) })

class IMove_ir(val src: I, val dst: Reg) : FLInst({ settingI(dst, src) })
class LMove_ir(val src: L, val dst: Reg) : FLInst({ settingL(dst, src) })
class FMove_ir(val src: F, val dst: Reg) : FLInst({ settingF(dst, src) })
class DMove_ir(val src: D, val dst: Reg) : FLInst({ settingD(dst, src) })

class PutPxl(val xReg: Reg, val yReg: Reg, val clrReg: Reg) : Instruction({
    withGraphicMemory {
        val frame = lastFrame()
        set(frame.i(xReg) * SCREEN_WIDTH + frame.i(yReg), frame.i(clrReg))
    }
})

object ScrFlush : Instruction({
    withDisplay { graphicMemory }
})

class Rnd(val dstReg: Reg) : FLInst({
    settingI(dstReg, ThreadLocalRandom.current().nextInt())
})

object BrPoint : Instruction({ this })