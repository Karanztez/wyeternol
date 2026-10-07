package dev.wyeternol.core.event

import kotlin.reflect.KClass

class WyEventBus {
    private val listeners = mutableMapOf<KClass<out WyEvent>, MutableList<(WyEvent) -> Unit>>()

    inline fun <reified T : WyEvent> subscribe(noinline handler: (T) -> Unit) {
        subscribe(T::class, handler)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : WyEvent> subscribe(eventClass: KClass<T>, handler: (T) -> Unit) {
        val list = listeners.getOrPut(eventClass) { mutableListOf() }
        list.add { event -> handler(event as T) }
    }

    fun <T : WyEvent> post(event: T): T {
        listeners[event::class]?.forEach { it.invoke(event) }
        return event
    }
}
