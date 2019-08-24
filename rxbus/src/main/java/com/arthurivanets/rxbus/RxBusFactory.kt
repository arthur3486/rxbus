package com.arthurivanets.rxbus

import io.reactivex.subjects.Subject

object RxBusFactory {


    /**
     * Creates a new instance of the [RxBus] that is based on the specified [Subject].
     *
     * @param subject the subject to base the [RxBus] on
     * @return the created [RxBus]
     */
    @JvmStatic fun <T : BusEvent<*>> create(subject : Subject<T>) : RxBus<T> {
        return RxBusImpl(subject)
    }


}