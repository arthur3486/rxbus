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

/**
 * A base class used to implement the [RxBus] events.
 */
abstract class BusEvent<T>(val data : T? = null) : Taggable<String> {


    override val tag = javaClass.name

    private var eventBus : RxBus? = null

    val hasData = (data != null)


    /**
     * <br>
     *      Attaches the specified [RxBus] to the current event.
     * <br>
     *      (Used for the management of the sticky events)
     * <br>
     *
     * @return the current instance of the [BusEvent] (for chaining purposes).
     */
    internal fun attachEventBus(eventBus : RxBus) : BusEvent<T> = apply {
        this.eventBus = eventBus
    }


    /**
     * <br>
     *      Detaches the associated [RxBus] from the current event.
     * <br>
     *      (Used for the management of the sticky events)
     * <br>
     *
     * @return the current instance of the [BusEvent] (for chaining purposes).
     */
    internal fun detachEventBus() : BusEvent<T> = apply {
        this.eventBus = null
    }


    /**
     * Retrieves the event data and automatically casts it to the desired type.
     *
     * @return the cast event data, or <strong>null</strong> if there's no associated data.
     */
    fun <DT> getDataAs() : DT? {
        return (data as DT?)
    }


    /**
     * Consumes the current event (if it's a sticky one).
     */
    fun consume() {
        if(!isConsumed()) {
            eventBus?.removeSticky(this)
            eventBus = null
        }
    }


    /**
     * Consumes the current event if necessary (if the event isn't consumed already).
     *
     * @param consumer the callback to be called when the event is to be consumed (will be called only if the event hasn't been consumed already)
     */
    inline fun consume(consumer : (BusEvent<T>) -> Unit) {
        if(!isConsumed()) {
            consumer(this)
            consume()
        }
    }


    /**
     * Checks whether the current is event is already consumed or not.
     *
     * @return <strong>true</strong> if the current event is consumed or it's not a sticky one, <strong>false</strong> otherwise.
     */
    fun isConsumed() : Boolean {
        val hasCorrespondingStickyEvent = (eventBus?.hasSticky(tag) ?: false)
        return !hasCorrespondingStickyEvent
    }


}