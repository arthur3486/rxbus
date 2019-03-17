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

package com.arthurivanets.rxbus.android

import com.arthurivanets.rxbus.RxBus
import com.arthurivanets.rxbus.RxBusImpl
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Android-specific version of the [RxBus].
 */
object AndroidRxBus {


    /**
     * The Global Single Instance.
     */
    @JvmStatic val INSTANCE : RxBus = newInstance()


    /**
     * Creates a new, separate (isolated) Event Bus Instance.
     */
    @JvmStatic fun newInstance() : RxBus {
        return RxBusImpl(AndroidSchedulers.mainThread())
    }


}