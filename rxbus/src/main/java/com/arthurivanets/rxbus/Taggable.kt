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
 * <br>
 *      A marker interface used to indicate that the current class has a dedicated tag.
 * <br>
 * <br>
 *      Used internally to provide the tag creation support for the sticky [BusEvent]s, in order to
 *      be able to properly distinguish one sticky event from another.
 * <br>
 */
internal interface Taggable<T : Any> {

    /**
     * Retrieves the tag of the current class.
     *
     * @return the class tag
     */
    val tag : T

}