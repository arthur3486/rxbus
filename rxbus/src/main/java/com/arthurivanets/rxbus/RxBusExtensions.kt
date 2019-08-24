@file:JvmName("RxBusUtils")

package com.arthurivanets.rxbus

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer


/**
 * Registers the specified [Consumer] for the observation and further consumption of the
 * event bus events of the type [T].
 *
 * @return the registration [Disposable] (used to manage the registration lifecycle)
 */
inline fun <T : BusEvent<*>, reified ET : T> RxBus<T>.register(onEvent : Consumer<ET>) : Disposable {
    return this.register(ET::class.java, onEvent)
}