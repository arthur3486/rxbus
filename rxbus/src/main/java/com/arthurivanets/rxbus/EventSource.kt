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
 * <br>
 *      An observable Event Producer.
 * <br>
 * <br>
 *      Used to provide the support for the production and further consumption of the [BusEvent]s.
 * <br>
 */
interface EventSource<T : BusEvent<*>> {

    /**
     * Subscribes the specified event [Consumer] to the current [EventSource]
     * for the consumption of the produced events.
     *
     * @param eventConsumer the actual event consumer
     * @return subscription [Disposable] (used for the subscription lifecycle management)
     */
    fun subscribe(eventConsumer : Consumer<T>) : Disposable

}