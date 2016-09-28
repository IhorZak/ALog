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

import android.util.SparseBooleanArray;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

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
     * @param indentSpaceCount Identation space count.
     * @return Formatted XML string.
     * @throws XmlPullParserException If XML string is invalid.
     * @throws IOException If some error occurred while reading XML string.
     */
    static String formatXml(String xml, int indentSpaceCount) throws XmlPullParserException,
                                                                     IOException {
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        StringBuilder stringBuilder = new StringBuilder();
        parser.setInput(new StringReader(xml));
        int eventType = parser.getEventType();
        int parentCount = 0;
        SparseBooleanArray hasChildrenArray = new SparseBooleanArray();
        SparseBooleanArray hasTextArray = new SparseBooleanArray();
        int currentIndent = 0;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (parentCount > 0 && !hasChildrenArray.get(parentCount - 1)) {
                        stringBuilder.append('>').append('\n');
                        hasChildrenArray.put(parentCount - 1, true);
                    }
                    for (int i = 0; i < currentIndent; ++i) {
                        stringBuilder.append(' ');
                    }
                    stringBuilder.append('<').append(parser.getName());
                    int attributeCount = parser.getAttributeCount();
                    if (attributeCount > 0) {
                        int attributeIndent = currentIndent + parser.getName().length() + 2;
                        stringBuilder.append(' ');
                        for (int i = 0; i < attributeCount - 1; ++i) {
                            stringBuilder.append(parser.getAttributeName(i))
                                    .append('=')
                                    .append('"')
                                    .append(parser.getAttributeValue(i))
                                    .append('"')
                                    .append('\n');
                            for (int j = 0; j < attributeIndent; ++j) {
                                stringBuilder.append(' ');
                            }
                        }
                        stringBuilder.append(parser.getAttributeName(attributeCount - 1))
                                .append('=')
                                .append('"')
                                .append(parser.getAttributeValue(attributeCount - 1))
                                .append('"');
                    }
                    hasChildrenArray.put(parentCount, false);
                    hasTextArray.put(parentCount, false);
                    ++parentCount;
                    currentIndent += indentSpaceCount;
                    break;
                case XmlPullParser.END_TAG:
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
                                .append(parser.getName())
                                .append('>');
                    } else {
                        stringBuilder.append('/').append('>');
                    }
                    stringBuilder.append('\n');
                    break;
                case XmlPullParser.TEXT:
                    if (parentCount > 0 && !hasChildrenArray.get(parentCount - 1)) {
                        stringBuilder.append('>');
                        hasTextArray.put(parentCount - 1, true);
                    }
                    stringBuilder.append(parser.getText());
                    break;
            }
            eventType = parser.next();
        }
        return stringBuilder.toString();
    }
}
