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
interface RxBus<T : BusEvent<*>> {

    /**
     * Registers the specified [Consumer] for the observation and further consumption of the
     * event bus events of the type [T].
     *
     * @return the registration [Disposable] (used to manage the registration lifecycle)
     */
    fun <ET : T> register(eventType : Class<ET>, onEvent : Consumer<ET>) : Disposable

    /**
     * Posts the specified bus event of type [T] to the corresponding subscribers.
     */
    fun post(event : T)

}