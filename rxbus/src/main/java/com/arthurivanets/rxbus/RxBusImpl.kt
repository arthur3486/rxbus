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

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * A concrete implementation of the [RxBus] which allows you to specify the exact
 * scheduler to be used during the event observation and consumption in the initialization phase.
 */
class RxBusImpl(private val observingScheduler : Scheduler) : RxBus {


    private val busSubject = PublishSubject.create<Any>().toSerialized()

    private val stickyEvents = LinkedList<BusEvent<*>>()
    private val stickyEventTrackingMap = HashMap<String, BusEvent<*>>()


    override fun <T : BusEvent<*>> register(eventType : Class<T>, onEvent : Consumer<T>) : Disposable {
        return busSubject.filter { eventType.isInstance(it) }
            .map { (it as T) }
            .observeOn(observingScheduler)
            .subscribe(onEvent)
            .also { emitStickyEvents() }
    }


    override fun post(event : BusEvent<*>) {
        busSubject.onNext(event)
    }


    override fun postSticky(event : BusEvent<*>) {
        addSticky(event)
        post(event)
    }


    private fun addSticky(event : BusEvent<*>) {
        // removing the old sticky event (if there's any)
        removeSticky(event)

        // attaching the event bus to the event in order to enable the proper
        // handling of the event consumption on the side of the event itself
        event.attachEventBus(this)

        // adding (creating) a brand-new one
        this.stickyEvents.add(event)
        this.stickyEventTrackingMap[event.tag] = event
    }


    override fun <T : BusEvent<*>> removeSticky(tag : String) : T? {
        return this.stickyEventTrackingMap.remove(tag)?.let {
            it.detachEventBus()

            return@let (if(this.stickyEvents.remove(it)) (it as T?) else null)
        }
    }


    override fun <T : BusEvent<*>> removeSticky(clazz : Class<T>) : T? {
        return removeSticky(clazz.javaClass.name)
    }


    override fun <T : BusEvent<*>> removeSticky(event : T) : T? {
        return removeSticky(event.tag)
    }


    override fun removeAllSticky() {
        this.stickyEvents.forEach { it.detachEventBus() }
        this.stickyEvents.clear()
        this.stickyEventTrackingMap.clear()
    }


    override fun <T : BusEvent<*>> getSticky(tag : String) : T? {
        return (this.stickyEventTrackingMap[tag] as T?)
    }


    override fun <T : BusEvent<*>> getSticky(clazz : Class<T>) : T? {
        return getSticky(clazz.javaClass.name)
    }


    override fun hasSticky(tag : String) : Boolean {
        return (this.stickyEventTrackingMap[tag] != null)
    }


    override fun <T : BusEvent<*>> hasSticky(clazz : Class<T>) : Boolean {
        return hasSticky(clazz.javaClass.name)
    }


    private fun emitStickyEvents() {
        this.stickyEvents.toList().forEach(::post)
    }


}