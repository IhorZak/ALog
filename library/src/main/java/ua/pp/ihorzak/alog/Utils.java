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

import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Util static methods.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
final class Utils {
    private static final String PREFIX_XMLNS = "xmlns";
    private static final String PREFIX_CDATA = "<![CDATA[";
    private static final String SUFFIX_CDATA = "]]>";
    private static final String PREFIX_COMMENT = "<!--";
    private static final String SUFFIX_COMMENT = "-->";

    private static final int MAX_BYTE = 0xFF;
    private static final int BYTE_MAX_LOW_CHAR = 0x0F;
    private static final int BYTE_CHAR_BIT_COUNT = 4;
    private static final char[] HEX_CHARS = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };
    private static final char HEX_SEPARATOR = ' ';

    private Utils() {}

    /**
     * Null-safe objects equality check method.
     *
     * @param o1 First object.
     * @param o2 Second object.
     * @return true if objects are equal, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    static boolean equals(Object o1, Object o2) {
        return o1 == o2 || !(o1 == null || o2 == null) && o1.equals(o2);
    }

    /**
     * Gets simple class name from full class name.
     *
     * @param className Full class name.
     * @return Simple class name.
     */
    static String getSimpleClassName(String className) {
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

    /**
     * Formats XML string.
     *
     * @param xml XML string to be formatted.
     * @param indentSpaceCount Indentation space count.
     * @return Formatted XML string.
     * @throws XmlPullParserException If XML string is invalid.
     * @throws IOException If some error occurred while reading XML string.
     */
    static String formatXml(String xml, int indentSpaceCount) throws XmlPullParserException,
                                                                     IOException {
        XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
        parserFactory.setNamespaceAware(true);
        XmlPullParser parser = parserFactory.newPullParser();
        StringBuilder stringBuilder = new StringBuilder();
        parser.setInput(new StringReader(xml));
        int eventType = parser.getEventType();
        int parentCount = 0;
        Stack<String> nameStack = new Stack<>();
        SparseArray<List<String>> namespaceArray = new SparseArray<>();
        SparseBooleanArray hasChildrenArray = new SparseBooleanArray();
        SparseBooleanArray hasTextArray = new SparseBooleanArray();
        int currentIndent = 0;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    String startPrefix = parser.getPrefix();
                    String startName = (startPrefix != null ? startPrefix + ':' : "") + parser.getName();
                    nameStack.push(startName);
                    if (parentCount > 0 && !hasChildrenArray.get(parentCount - 1)) {
                        if (!hasTextArray.get(parentCount - 1)) {
                            stringBuilder.append('>');
                        }
                        stringBuilder.append('\n');
                        hasChildrenArray.put(parentCount - 1, true);
                    }
                    for (int i = 0; i < currentIndent; ++i) {
                        stringBuilder.append(' ');
                    }
                    stringBuilder.append('<').append(startName);
                    boolean hasNamespaces = false;
                    int namespaceCount = parser.getNamespaceCount(parentCount + 1);
                    if (namespaceCount > 0) {
                        List<String> newNamespaceList = null;
                        for (int i = 0; i < namespaceCount; ++i) {
                            String namespacePrefix = parser.getNamespacePrefix(i);
                            String namespaceUri = parser.getNamespace(namespacePrefix);
                            String namespace = namespacePrefix + '=' + '\"' +
                                    escapeXmlSpecialCharacters(namespaceUri) + '\"';
                            boolean isNew = true;
                            for (int j = 0; j < namespaceArray.size() && isNew; ++j) {
                                List<String> namespaceList = namespaceArray.get(namespaceArray.keyAt(j));
                                if (namespaceList != null && !namespaceList.isEmpty()) {
                                    isNew = !namespaceList.contains(namespace);
                                }
                            }
                            if (isNew) {
                                if (newNamespaceList == null) {
                                    newNamespaceList = new ArrayList<>();
                                }
                                if (!newNamespaceList.contains(namespace)) {
                                    newNamespaceList.add(namespace);
                                }
                            }
                        }
                        if (newNamespaceList != null && !newNamespaceList.isEmpty()) {
                            hasNamespaces = true;
                            int namespaceIndent = currentIndent + startName.length() + 2;
                            stringBuilder.append(' ');
                            for (int i = 0; i < newNamespaceList.size() - 1; ++i) {
                                stringBuilder.append(PREFIX_XMLNS)
                                        .append(':')
                                        .append(newNamespaceList.get(i))
                                        .append('\n');
                                for (int j = 0; j < namespaceIndent; ++j) {
                                    stringBuilder.append(' ');
                                }
                            }
                            stringBuilder.append(PREFIX_XMLNS)
                                    .append(':')
                                    .append(newNamespaceList.get(newNamespaceList.size() - 1))
                                    .append('\n');
                        }
                        namespaceArray.put(parentCount, newNamespaceList);
                    }
                    int attributeCount = parser.getAttributeCount();
                    if (attributeCount > 0) {
                        String attributePrefix;
                        int attributeIndent = currentIndent + startName.length() + 2;
                        for (int j = 0; j < (hasNamespaces ? attributeIndent : 1); ++j) {
                            stringBuilder.append(' ');
                        }
                        for (int i = 0; i < attributeCount - 1; ++i) {
                            attributePrefix = parser.getAttributePrefix(i);
                            if (!TextUtils.isEmpty(attributePrefix)) {
                                stringBuilder.append(attributePrefix).append(':');
                            }
                            stringBuilder.append(parser.getAttributeName(i))
                                    .append('=')
                                    .append('"')
                                    .append(escapeXmlSpecialCharacters(parser.getAttributeValue(i)))
                                    .append('"')
                                    .append('\n');
                            for (int j = 0; j < attributeIndent; ++j) {
                                stringBuilder.append(' ');
                            }
                        }
                        attributePrefix = parser.getAttributePrefix(attributeCount - 1);
                        if (!TextUtils.isEmpty(attributePrefix)) {
                            stringBuilder.append(attributePrefix).append(':');
                        }
                        stringBuilder.append(parser.getAttributeName(attributeCount - 1))
                                .append('=')
                                .append('"')
                                .append(escapeXmlSpecialCharacters(parser.getAttributeValue(attributeCount - 1)))
                                .append('"');
                    }
                    hasChildrenArray.put(parentCount, false);
                    hasTextArray.put(parentCount, false);
                    ++parentCount;
                    currentIndent += indentSpaceCount;
                    break;
                case XmlPullParser.END_TAG:
                    String endPrefix = parser.getPrefix();
                    String endName = (endPrefix != null ? endPrefix + ':' : "") + parser.getName();
                    boolean isNameStackEmpty = nameStack.empty();
                    String expectedName = nameStack.pop();
                    if (isNameStackEmpty || !endName.equals(expectedName)) {
                        throw new XmlPullParserException("Expected \"" + expectedName +
                                "\" close tag, but found \"" + endName + "\" close tag");
                    }
                    --parentCount;
                    currentIndent -= indentSpaceCount;
                    if (hasChildrenArray.get(parentCount)) {
                        for (int i = 0; i < currentIndent; ++i) {
                            stringBuilder.append(' ');
                        }
                    }
                    if (hasChildrenArray.get(parentCount) || hasTextArray.get(parentCount)) {
                        stringBuilder.append('<')
                                .append('/')
                                .append(endName)
                                .append('>');
                    } else {
                        stringBuilder.append('/').append('>');
                    }
                    stringBuilder.append('\n');
                    namespaceArray.delete(parentCount);
                    break;
                case XmlPullParser.TEXT:
                    if (parentCount > 0 && !hasChildrenArray.get(parentCount - 1) && !hasTextArray.get(parentCount - 1)) {
                        stringBuilder.append('>');
                    }
                    hasTextArray.put(parentCount - 1, true);
                    String text = parser.getText().trim();
                    if (text.length() > 0) {
                        stringBuilder.append(escapeXmlSpecialCharacters(text));
                    }
                    break;
                case XmlPullParser.CDSECT:
                    if (parentCount > 0 && !hasChildrenArray.get(parentCount - 1) && !hasTextArray.get(parentCount - 1)) {
                        stringBuilder.append('>');
                    }
                    hasTextArray.put(parentCount - 1, true);
                    String cData = parser.getText();
                    if (cData.length() > 0) {
                        stringBuilder.append(PREFIX_CDATA).append(cData).append(SUFFIX_CDATA);
                    }
                    break;
                case XmlPullParser.COMMENT:
                    if (parentCount > 0 && !hasChildrenArray.get(parentCount - 1) && !hasTextArray.get(parentCount - 1)) {
                        stringBuilder.append('>');
                    }
                    hasTextArray.put(parentCount - 1, true);
                    String comment = parser.getText();
                    if (comment.length() > 0) {
                        stringBuilder.append(PREFIX_COMMENT).append(comment).append(SUFFIX_COMMENT);
                    }
                    break;
            }
            eventType = parser.nextToken();
        }
        if (!nameStack.empty()) {
            throw new XmlPullParserException("Expected \"" + nameStack.pop() + "\" close tag");
        }
        return stringBuilder.toString();
    }

    static String formatBytesAsHexString(byte[] bytes) {
        char[] chars = new char[bytes.length * 3 - 1];
        for (int i = 0; i < bytes.length; ++i) {
            int temp = bytes[i] & MAX_BYTE;
            int index = i * 3;
            chars[index] = HEX_CHARS[temp >>> BYTE_CHAR_BIT_COUNT];
            chars[index + 1] = HEX_CHARS[temp & BYTE_MAX_LOW_CHAR];
            if (i < bytes.length - 1) {
                chars[index + 2] = HEX_SEPARATOR;
            }
        }
        return new String(chars);
    }

    /**
     * Checks if passed class is primitive class wrapper.
     *
     * @param clazz Class to check.
     * @return true if passed class is primitive class wrapper, false otherwise.
     */
    static boolean isClassBoxedPrimitive(Class<?> clazz) {
        return Byte.class.equals(clazz)
                || Short.class.equals(clazz)
                || Integer.class.equals(clazz)
                || Long.class.equals(clazz)
                || Float.class.equals(clazz)
                || Double.class.equals(clazz)
                || Boolean.class.equals(clazz)
                || Character.class.equals(clazz);
    }

    /**
     * Formats passed logging message format with passed arguments.
     *
     * @param message Logging message format.
     * @param arguments Logging message arguments.
     * @param configuration {@link ALog} configuration.
     * @return Logging string.
     */
    static String formatMessageWithArguments(String message,
                                             Object[] arguments,
                                             ALogConfiguration configuration) {
        if (message == null) {
            return "";
        }
        if (arguments == null || arguments.length == 0) {
            return message;
        }
        for (int i = 0; i < arguments.length; ++i) {
            Object argument = arguments[i];
            if (argument != null) {
                Class<?> argumentClass = argument.getClass();
                if (!argumentClass.isPrimitive() && !isClassBoxedPrimitive(argumentClass)) {
                    arguments[i] = formatArgument(argument, configuration);
                }
            }
        }
        return String.format(message, arguments);
    }

    /**
     * Formats logging message argument to logging string.
     *
     * @param argument Logging message argument.
     * @param configuration {@link ALog} configuration.
     * @return Formatted logging string for passed logging argument.
     */
    static String formatArgument(Object argument, ALogConfiguration configuration) {
        String formattedArgument;
        if (argument == null) {
            formattedArgument = "null";
        } else {
            Class<?> argumentClass = argument.getClass();
            if (configuration.mArrayFormatter != null && argumentClass.isArray()) {
                formattedArgument = configuration.mArrayFormatter.format(argument);
            } else if (configuration.mCollectionFormatter != null && Collection.class.isAssignableFrom(argumentClass)) {
                formattedArgument = configuration.mCollectionFormatter.format(argument);
            } else if (configuration.mMapFormatter != null && Map.class.isAssignableFrom(argumentClass)) {
                formattedArgument = configuration.mMapFormatter.format(argument);
            } else if (configuration.mFormatterMap.containsKey(argumentClass)) {
                ALogFormatter<?> formatter = configuration.mFormatterMap.get(argumentClass);
                //noinspection ConstantConditions
                formattedArgument = formatter.format(argument);
            } else {
                formattedArgument = argument.toString();
            }
        }
        return formattedArgument;
    }

    private static String escapeXmlSpecialCharacters(String s) {
        return s.replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
