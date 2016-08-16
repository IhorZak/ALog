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
 * Util static methods.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 * @since 13.08.2016
 */
final class Utils {
    private Utils() {}

    /**
     * Null-safe objects equality check method.
     *
     * @param o1 First object.
     * @param o2 Second object.
     * @return true if objects are equal, otherwise false.
     */
    public static boolean equals(Object o1, Object o2) {
        return o1 == o2 || !(o1 == null || o2 == null) && o1.equals(o2);
    }

    /**
     * Gets simple class name from full class name.
     *
     * @param className Full class name.
     * @return Simple class name.
     */
    public static String getSimpleClassName(String className) {
        String simpleClassName = null;
        if (className != null) {
            int packageNameEnd = className.lastIndexOf('.');
            if (packageNameEnd == -1) {
                simpleClassName = className;
            } else {
                simpleClassName = className.substring(packageNameEnd + 1);
            }
        }
        return simpleClassName;
    }
}
