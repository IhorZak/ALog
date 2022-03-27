/*
 * Copyright 2017 Ihor Zakhozhyi
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
 * {@link ALogFormatter} to transform arrays into logging strings.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
final class ArrayALogFormatter extends ConfigurationALogFormatter<Object> {
    /**
     * Constructor.
     *
     * @param configuration {@link ALogConfiguration} instance.
     */
    ArrayALogFormatter(ALogConfiguration configuration) {
        super(configuration);
    }

    @Override
    public String toLoggingString(Object object, ALogFormatter<Object> objectFormatter) {
        StringBuilder builder = new StringBuilder();
        Class<?> componentType = object.getClass().getComponentType();
        if (componentType != null) {
            if (Object.class.isAssignableFrom(componentType)) {
                logObjectArray((Object[]) object, builder);
            } else if (byte.class.isAssignableFrom(componentType)) {
                logByteArray((byte[]) object, builder);
            } else if (short.class.isAssignableFrom(componentType)) {
                logShortArray((short[]) object, builder);
            } else if (int.class.isAssignableFrom(componentType)) {
                logIntArray((int[]) object, builder);
            } else if (long.class.isAssignableFrom(componentType)) {
                logLongArray((long[]) object, builder);
            } else if (float.class.isAssignableFrom(componentType)) {
                logFloatArray((float[]) object, builder);
            } else if (double.class.isAssignableFrom(componentType)) {
                logDoubleArray((double[]) object, builder);
            } else if (boolean.class.isAssignableFrom(componentType)) {
                logBooleanArray((boolean[]) object, builder);
            } else if (char.class.isAssignableFrom(componentType)) {
                logCharArray((char[]) object, builder);
            }
        }
        return builder.toString();
    }

    private void logObjectArray(Object[] objects, StringBuilder builder) {
        builder.append("Array(size = ")
                .append(objects.length)
                .append(") [");
        for (int i = 0; i < objects.length - 1; ++i) {
            builder.append(Utils.formatArgument(objects[i], mConfiguration))
                    .append(", ");
        }
        if (objects.length > 0) {
            builder.append(Utils.formatArgument(objects[objects.length - 1], mConfiguration));
        }
        builder.append(']');
    }

    private void logByteArray(byte[] bytes, StringBuilder builder) {
        builder.append("Array(size = ")
               .append(bytes.length)
               .append(") [");
        for (int i = 0; i < bytes.length - 1; ++i) {
            builder.append(bytes[i])
                   .append(", ");
        }
        if (bytes.length > 0) {
            builder.append(bytes[bytes.length - 1]);
        }
        builder.append(']');
    }

    private void logShortArray(short[] shorts, StringBuilder builder) {
        builder.append("Array(size = ")
               .append(shorts.length)
               .append(") [");
        for (int i = 0; i < shorts.length - 1; ++i) {
            builder.append(shorts[i])
                   .append(", ");
        }
        if (shorts.length > 0) {
            builder.append(shorts[shorts.length - 1]);
        }
        builder.append(']');
    }

    private void logIntArray(int[] ints, StringBuilder builder) {
        builder.append("Array(size = ")
               .append(ints.length)
               .append(") [");
        for (int i = 0; i < ints.length - 1; ++i) {
            builder.append(ints[i])
                   .append(", ");
        }
        if (ints.length > 0) {
            builder.append(ints[ints.length - 1]);
        }
        builder.append(']');
    }

    private void logLongArray(long[] longs, StringBuilder builder) {
        builder.append("Array(size = ")
               .append(longs.length)
               .append(") [");
        for (int i = 0; i < longs.length - 1; ++i) {
            builder.append(longs[i])
                   .append(", ");
        }
        if (longs.length > 0) {
            builder.append(longs[longs.length - 1]);
        }
        builder.append(']');
    }

    private void logFloatArray(float[] floats, StringBuilder builder) {
        builder.append("Array(size = ")
               .append(floats.length)
               .append(") [");
        for (int i = 0; i < floats.length - 1; ++i) {
            builder.append(floats[i])
                   .append(", ");
        }
        if (floats.length > 0) {
            builder.append(floats[floats.length - 1]);
        }
        builder.append(']');
    }

    private void logDoubleArray(double[] doubles, StringBuilder builder) {
        builder.append("Array(size = ")
               .append(doubles.length)
               .append(") [");
        for (int i = 0; i < doubles.length - 1; ++i) {
            builder.append(doubles[i])
                   .append(", ");
        }
        if (doubles.length > 0) {
            builder.append(doubles[doubles.length - 1]);
        }
        builder.append(']');
    }

    private void logBooleanArray(boolean[] booleans, StringBuilder builder) {
        builder.append("Array(size = ")
               .append(booleans.length)
               .append(") [");
        for (int i = 0; i < booleans.length - 1; ++i) {
            builder.append(booleans[i])
                   .append(", ");
        }
        if (booleans.length > 0) {
            builder.append(booleans[booleans.length - 1]);
        }
        builder.append(']');
    }

    private void logCharArray(char[] chars, StringBuilder builder) {
        builder.append("Array(size = ")
               .append(chars.length)
               .append(") [");
        for (int i = 0; i < chars.length - 1; ++i) {
            builder.append(chars[i])
                   .append(", ");
        }
        if (chars.length > 0) {
            builder.append(chars[chars.length - 1]);
        }
        builder.append(']');
    }
}
