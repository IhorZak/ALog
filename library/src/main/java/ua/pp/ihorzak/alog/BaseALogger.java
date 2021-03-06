/*
 * Copyright 2016 Ihor Zakhozhyi
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

package ua.pp.ihorzak.alog;

/**
 * Base abstract {@link ALogger} implementation.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
abstract class BaseALogger implements ALogger {
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private static final String SINGLE_ARGUMENT_FORMAT = "%s";

    @Override
    public void v() {
        v(null, null, EMPTY_ARRAY);
    }

    @Override
    public void v(Object object) {
        v((Throwable) null, SINGLE_ARGUMENT_FORMAT, object);
    }

    @Override
    public void v(String message, Object... args) {
        v(null, message, args);
    }

    @Override
    public void v(Throwable throwable) {
        v(throwable, null);
    }

    @Override
    public void d() {
        d(null, null, EMPTY_ARRAY);
    }

    @Override
    public void d(Object object) {
        d((Throwable) null, SINGLE_ARGUMENT_FORMAT, object);
    }

    @Override
    public void d(String message, Object... args) {
        d(null, message, args);
    }

    @Override
    public void d(Throwable throwable) {
        d(throwable, null);
    }

    @Override
    public void i() {
        i(null, null, EMPTY_ARRAY);
    }

    @Override
    public void i(Object object) {
        i((Throwable) null, SINGLE_ARGUMENT_FORMAT, object);
    }

    @Override
    public void i(String message, Object... args) {
        i(null, message, args);
    }

    @Override
    public void i(Throwable throwable) {
        i(throwable, null);
    }

    @Override
    public void w() {
        w(null, null, EMPTY_ARRAY);
    }

    @Override
    public void w(Object object) {
        w((Throwable) null, SINGLE_ARGUMENT_FORMAT, object);
    }

    @Override
    public void w(String message, Object... args) {
        w(null, message, args);
    }

    @Override
    public void w(Throwable throwable) {
        w(throwable, null);
    }

    @Override
    public void e() {
        e(null, null, EMPTY_ARRAY);
    }

    @Override
    public void e(Object object) {
        e((Throwable) null, SINGLE_ARGUMENT_FORMAT, object);
    }

    @Override
    public void e(String message, Object... args) {
        e(null, message, args);
    }

    @Override
    public void e(Throwable throwable) {
        e(throwable, null);
    }

    @Override
    public void wtf() {
        wtf(null, null, EMPTY_ARRAY);
    }

    @Override
    public void wtf(Object object) {
        wtf((Throwable) null, SINGLE_ARGUMENT_FORMAT, object);
    }

    @Override
    public void wtf(String message, Object... args) {
        wtf(null, message, args);
    }

    @Override
    public void wtf(Throwable throwable) {
        wtf(throwable, null);
    }
}
