@file:Suppress("unused")

import clojure.lang.ArraySeq
import java.util.*

private typealias CljMap = clojure.lang.PersistentTreeMap

// Due to annoying complexities of Java -- Clojure interactions, I'll only include absolutely necessary methods here

class PersistentTreeMap<K, V> private constructor(val map: CljMap) : Map<K, V> {
    constructor() : this(CljMap())
    constructor(map: Map<K, V>) : this(CljMap.create(map) as CljMap)
    constructor(map: SortedMap<K, V>) : this(
        CljMap.create(
            map.comparator(),
            map.entries.flatMap { listOf(it.key, it.value) }
                .let { ArraySeq.create(*it.toTypedArray()) }
        ) as CljMap
    )

    constructor(comp: Comparator<K>) : this(CljMap(null, comp))

    fun put(key: K, value: V) = PersistentTreeMap<K, V>(map.assoc(key, value))

    fun floor(key: K) = map.seqFrom(key, false)?.first() as Map.Entry<K, V>?

    fun without(key: K) = PersistentTreeMap<K, V>(map.without(key))



    override fun containsKey(key: K): Boolean = map.containsKey(key)
    override fun containsValue(value: V): Boolean = map.containsValue(value)
    override fun get(key: K): V? = map[key] as V?
    override fun isEmpty(): Boolean = map.isEmpty()
    override val entries: Set<Map.Entry<K, V>> get() = map.entries as Set<Map.Entry<K, V>>
    override val keys: Set<K> = map.keys as Set<K>
    override val size: Int get() = map.size
    override val values: Collection<V> = map.values as Collection<V>
}