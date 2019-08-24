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

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.Subject

/**
 * A concrete implementation of the [RxBus] which allows to specify
 * the subject to be used for the event broadcasting.
 */
internal class RxBusImpl<T : BusEvent<*>>(subject : Subject<T>) : RxBus<T> {


    private val busSubject = subject.toSerialized()


    @Suppress("UNCHECKED_CAST")
    override fun <ET : T> register(eventType : Class<ET>, onEvent : Consumer<ET>) : Disposable {
        return busSubject.filter { eventType.isInstance(it) }
            .map { it as ET }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onEvent)
    }


    override fun post(event : T) {
        busSubject.onNext(event)
    }


}