/*
 * Copyright 2018 Arthur Ivanets, arthur.ivanets.l@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arthurivanets.rxbus

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * A base contract used to implement the concrete versions of the [RxBus].
 */
interface RxBus {

    /**
     * Registers the specified [Consumer] for the observation and further consumption of the
     * event bus events of the type [T].
     *
     * @return the registration [Disposable] (used to manage the registration lifecycle)
     */
    fun <T : BusEvent<*>> register(eventType : Class<T>, onEvent : Consumer<T>) : Disposable

    /**
     * Posts the specified regular [BusEvent] to the corresponding subscribers.
     */
    fun post(event : BusEvent<*>)

    /**
     * Posts the specified sticky [BusEvent] to the corresponding subscribers.
     */
    fun postSticky(event : BusEvent<*>)

    /**
     * Removes the existing sticky [BusEvent] (if there's any) that corresponds to the specified tag.
     *
     * @return the removed sticky [BusEvent], or <strong>null</strong> if there's no corresponding event
     */
    fun <T : BusEvent<*>> removeSticky(tag : String) : T?

    /**
     * Removes the existing sticky [BusEvent] (if there's any) that corresponds to the specified class.
     *
     * @return the removed sticky [BusEvent], or <strong>null</strong> if there's no corresponding event
     */
    fun <T : BusEvent<*>> removeSticky(clazz : Class<T>) : T?

    /**
     * Removes the existing sticky [BusEvent] (if there's any) that corresponds to the specified event.
     *
     * @return the removed sticky [BusEvent], or <strong>null</strong> if there's no corresponding event
     */
    fun <T : BusEvent<*>> removeSticky(event : T) : T?

    /**
     * Removes all the existing sticky [BusEvent]s.
     */
    fun removeAllSticky()

    /**
     * Retrieves the existing sticky [BusEvent] (if there's any) that corresponds to the specified tag.
     *
     * @return the corresponding sticky [BusEvent], or <strong>null</strong> if there's no corresponding event
     */
    fun <T : BusEvent<*>> getSticky(tag : String) : T?

    /**
     * Retrieves the existing sticky [BusEvent] (if there's any) that corresponds to the specified class.
     *
     * @return the corresponding sticky [BusEvent], or <strong>null</strong> if there's no corresponding event
     */
    fun <T : BusEvent<*>> getSticky(clazz : Class<T>) : T?

    /**
     * Checks whether the sticky [BusEvent] that corresponds to the specified tag is present within the bus.
     *
     * @return <strong>true</strong> if the corresponding sticky [BusEvent] is present, <strong>false</strong> otherwise
     */
    fun hasSticky(tag : String) : Boolean

    /**
     * Checks whether the sticky [BusEvent] that corresponds to the specified class is present within the bus.
     *
     * @return <strong>true</strong> if the corresponding sticky [BusEvent] is present, <strong>false</strong> otherwise
     */
    fun <T : BusEvent<*>> hasSticky(clazz : Class<T>) : Boolean

}