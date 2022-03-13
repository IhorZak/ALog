/*
 * Copyright 2022 Ihor Zakhozhyi
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

package ua.pp.ihorzak.alog.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * {@link Iterable} implementation to be used in unit tests.
 * This implementation can be used when there is a need in {@link Iterable} which is not
 *
 * @param <T> Type of this test iterable elements.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
@SuppressWarnings({"unused", "NullableProblems", "unchecked"})
public final class TestIterable<T> implements Iterable<T> {
    private final Collection<T> mElements;

    /**
     * Constructor.
     *
     * @param elements {@link Collection} of elements to be contained in this {@link Iterable}.
     */
    public TestIterable(Collection<T> elements) {
        mElements = elements;
    }

    /**
     * Constructor.
     *
     * @param elements Elements to be contained in this {@link Iterable}.
     */
    public TestIterable(T... elements) {
        this(Arrays.asList(elements));
    }

    @Override
    public Iterator<T> iterator() {
        return new TestIterator<>(mElements.iterator());
    }

    private static final class TestIterator<T> implements Iterator<T> {
        private final Iterator<T> mIterator;

        public TestIterator(Iterator<T> iterator) {
            mIterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return mIterator.hasNext();
        }

        @Override
        public T next() {
            return mIterator.next();
        }

        @Override
        public void remove() {
            mIterator.remove();
        }
    }
}
