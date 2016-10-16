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

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import ua.pp.ihorzak.alog.test.*;
import ua.pp.ihorzak.alog.test.Utils;

import static ua.pp.ihorzak.alog.test.Utils.*;

/**
 * {@link ALog} unit tests.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
public class ALogTest extends BaseTest {
    private static final String TAG = "Test";

    @Before
    public void setUp() {
        ALogConfiguration configuration = ALogConfiguration.builder()
                .tag(TAG)
                .threadPrefixEnabled(false)
                .methodPrefixEnabled(false)
                .lineLocationPrefixEnabled(false)
                .build();
        ALog.initialize(configuration);
    }

    @Test
    public void testV() {
        String message = "Message";
        ALog.v(message);
        assertLogEquals(Log.VERBOSE, TAG, message);

        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.v(messageFormat, stringArg, intArg);
        assertLogEquals(Log.VERBOSE, TAG, formattedMessage);

        Throwable throwable = new RuntimeException();
        ALog.v(throwable);
        assertLogEquals(Log.VERBOSE, TAG, Log.getStackTraceString(throwable));

        ALog.v(throwable, messageFormat, stringArg, intArg);
        assertLogEquals(Log.VERBOSE, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testD() {
        String message = "Message";
        ALog.d(message);
        assertLogEquals(Log.DEBUG, TAG, message);

        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.d(messageFormat, stringArg, intArg);
        assertLogEquals(Log.DEBUG, TAG, formattedMessage);

        Throwable throwable = new RuntimeException();
        ALog.d(throwable);
        assertLogEquals(Log.DEBUG, TAG, Log.getStackTraceString(throwable));

        ALog.d(throwable, messageFormat, stringArg, intArg);
        assertLogEquals(Log.DEBUG, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testI() {
        String message = "Message";
        ALog.i(message);
        assertLogEquals(Log.INFO, TAG, message);

        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.i(messageFormat, stringArg, intArg);
        assertLogEquals(Log.INFO, TAG, formattedMessage);

        Throwable throwable = new RuntimeException();
        ALog.i(throwable);
        assertLogEquals(Log.INFO, TAG, Log.getStackTraceString(throwable));

        ALog.i(throwable, messageFormat, stringArg, intArg);
        assertLogEquals(Log.INFO, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testW() {
        String message = "Message";
        ALog.w(message);
        assertLogEquals(Log.WARN, TAG, message);

        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.w(messageFormat, stringArg, intArg);
        assertLogEquals(Log.WARN, TAG, formattedMessage);

        Throwable throwable = new RuntimeException();
        ALog.w(throwable);
        assertLogEquals(Log.WARN, TAG, Log.getStackTraceString(throwable));

        ALog.w(throwable, messageFormat, stringArg, intArg);
        assertLogEquals(Log.WARN, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testE() {
        String message = "Message";
        ALog.e(message);
        assertLogEquals(Log.ERROR, TAG, message);

        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.e(messageFormat, stringArg, intArg);
        assertLogEquals(Log.ERROR, TAG, formattedMessage);

        Throwable throwable = new RuntimeException();
        ALog.e(throwable);
        assertLogEquals(Log.ERROR, TAG, Log.getStackTraceString(throwable));

        ALog.e(throwable, messageFormat, stringArg, intArg);
        assertLogEquals(Log.ERROR, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testWtf() {
        String message = "Message";
        ALog.wtf(message);
        assertLogEquals(Log.ASSERT, TAG, message);

        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.wtf(messageFormat, stringArg, intArg);
        assertLogEquals(Log.ASSERT, TAG, formattedMessage);

        Throwable throwable = new RuntimeException();
        ALog.wtf(throwable);
        assertLogEquals(Log.ASSERT, TAG, Log.getStackTraceString(throwable));

        ALog.wtf(throwable, messageFormat, stringArg, intArg);
        assertLogEquals(Log.ASSERT, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testT() {
        String customTag = "Custom";
        String message = "Message";

        ALogger customTagLogger = ALog.t(customTag);
        customTagLogger.v(message);
        assertLogEquals(null, customTag, null);
        customTagLogger.d(message);
        assertLogEquals(null, customTag, null);
        customTagLogger.i(message);
        assertLogEquals(null, customTag, null);
        customTagLogger.w(message);
        assertLogEquals(null, customTag, null);
        customTagLogger.e(message);
        assertLogEquals(null, customTag, null);
        customTagLogger.wtf(message);
        assertLogEquals(null, customTag, null);

        ALogger autoTagLogger = ALog.t(null);
        String autoTag = Utils.class.getSimpleName();
        v(autoTagLogger, null, message);
        assertLogEquals(null, autoTag, null);
        d(autoTagLogger, null, message);
        assertLogEquals(null, autoTag, null);
        i(autoTagLogger, null, message);
        assertLogEquals(null, autoTag, null);
        w(autoTagLogger, null, message);
        assertLogEquals(null, autoTag, null);
        e(autoTagLogger, null, message);
        assertLogEquals(null, autoTag, null);
        wtf(autoTagLogger, null, message);
        assertLogEquals(null, autoTag, null);
    }

    @Test
    public void testJson() {
        ALog.json(null);
        assertLogEquals(null, null, "Passed JSON string is null");

        ALog.json("");
        assertLogEquals(null, null, "Passed JSON string is empty");

        String invalidJson = "{\"id\":1234, \"name\":\"John Doe\", \"arr\":[\"a\", \"b\"}]}";
        ALog.json(invalidJson);
        assertLogEquals(null, null, "Invalid JSON string: Unterminated array at character 47 of " + invalidJson);

        String validJson = "{\"id\":1234, \"name\":\"John Doe\", \"arr\":[\"a\", \"b\"]}";
        String formattedValidJson = "JSON:\n" +
                "{\n" +
                "  \"id\": 1234,\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"arr\": [\n" +
                "    \"a\",\n" +
                "    \"b\"\n" +
                "  ]\n" +
                "}";
        ALog.json(validJson);
        assertLogEquals(null, null, formattedValidJson);
        ALog.json(ALogLevel.ERROR, validJson);
        assertLogEquals(Log.ERROR, null, formattedValidJson);
    }
    
    @Test
    public void testXml() {
        ALog.xml(null);
        assertLogEquals(null, null, "Passed XML string is null");

        ALog.xml("");
        assertLogEquals(null, null, "Passed XML string is empty");

        String invalidXml1 = "<note><to>Tove</to><from>Jani</Ffrom><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";
        ALog.xml(invalidXml1);
        assertLogStartsWith(null, null, "Invalid XML string: ");

        String invalidXml2 = "<root><item/><item2><root/>";
        ALog.xml(invalidXml2);
        assertLogStartsWith(null, null, "Invalid XML string: ");

        String validXml1 = "<note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";
        String formattedValidXml1 = "XML:\n" +
                "<note>\n" +
                "  <to>Tove</to>\n" +
                "  <from>Jani</from>\n" +
                "  <heading>Reminder</heading>\n" +
                "  <body>Don&apos;t forget me this weekend!</body>\n" +
                "</note>\n";
        ALog.xml(validXml1);
        assertLogEquals(null, null, formattedValidXml1);
        ALog.xml(ALogLevel.ERROR, validXml1);
        assertLogEquals(Log.ERROR, null, formattedValidXml1);

        String validXml2 = "<student nickname='Rob \"The Dog\"'><name>Robert</name><grade>A+</grade></student>";
        String formattedValidXml2 = "XML:\n" +
                "<student nickname=\"Rob &quot;The Dog&quot;\">\n" +
                "  <name>Robert</name>\n" +
                "  <grade>A+</grade>\n" +
                "</student>\n";
        ALog.xml(validXml2);
        assertLogEquals(null, null, formattedValidXml2);
        ALog.xml(ALogLevel.DEBUG, validXml2);
        assertLogEquals(Log.DEBUG, null, formattedValidXml2);

        String validXml3 = "<note day=\"12\" month=\"11\" year=\"99\" to=\"Tove\" from=\"Jani\" heading=\"Reminder\" body=\"Don't forget me this weekend!\"></note>";
        String formattedValidXml3 = "XML:\n" +
                "<note day=\"12\"\n" +
                "      month=\"11\"\n" +
                "      year=\"99\"\n" +
                "      to=\"Tove\"\n" +
                "      from=\"Jani\"\n" +
                "      heading=\"Reminder\"\n" +
                "      body=\"Don&apos;t forget me this weekend!\"/>\n";
        ALog.xml(validXml3);
        assertLogEquals(null, null, formattedValidXml3);
        ALog.xml(ALogLevel.VERBOSE, validXml3);
        assertLogEquals(Log.VERBOSE, null, formattedValidXml3);

        String validXml4 = "<catalog> <book id=\"bk101\"> <author>Gambardella, Matthew</author> <title>XML Developer's Guide</title> <genre>Computer</genre> <price>44.95</price> <publish_date>2000-10-01</publish_date> <description>An in-depth look at creating applications with XML.</description> </book> <book id=\"bk102\"> <author>Ralls, Kim</author> <title>Midnight Rain</title> <genre>Fantasy</genre> <price>5.95</price> <publish_date>2000-12-16</publish_date> <description>A former architect battles corporate zombies, an evil sorceress, and her own childhood to become queen of the world.</description> </book> <book id=\"bk103\"> <author>Corets, Eva</author> <title>Maeve Ascendant</title> <genre>Fantasy</genre> <price>5.95</price> <publish_date>2000-11-17</publish_date> <description>After the collapse of a nanotechnology society in England, the young survivors lay the foundation for a new society.</description> </book> </catalog>";
        String formattedValidXml4 = "XML:\n" +
                "<catalog>\n" +
                "  <book id=\"bk101\">\n" +
                "    <author>Gambardella, Matthew</author>\n" +
                "    <title>XML Developer&apos;s Guide</title>\n" +
                "    <genre>Computer</genre>\n" +
                "    <price>44.95</price>\n" +
                "    <publish_date>2000-10-01</publish_date>\n" +
                "    <description>An in-depth look at creating applications with XML.</description>\n" +
                "  </book>\n" +
                "  <book id=\"bk102\">\n" +
                "    <author>Ralls, Kim</author>\n" +
                "    <title>Midnight Rain</title>\n" +
                "    <genre>Fantasy</genre>\n" +
                "    <price>5.95</price>\n" +
                "    <publish_date>2000-12-16</publish_date>\n" +
                "    <description>A former architect battles corporate zombies, an evil sorceress, and her own childhood to become queen of the world.</description>\n" +
                "  </book>\n" +
                "  <book id=\"bk103\">\n" +
                "    <author>Corets, Eva</author>\n" +
                "    <title>Maeve Ascendant</title>\n" +
                "    <genre>Fantasy</genre>\n" +
                "    <price>5.95</price>\n" +
                "    <publish_date>2000-11-17</publish_date>\n" +
                "    <description>After the collapse of a nanotechnology society in England, the young survivors lay the foundation for a new society.</description>\n" +
                "  </book>\n" +
                "</catalog>\n";
        ALog.xml(validXml4);
        assertLogEquals(null, null, formattedValidXml4);
        ALog.xml(ALogLevel.WTF, validXml4);
        assertLogEquals(Log.ASSERT, null, formattedValidXml4);

        String validXml5 = "<res:resource xmlns:res=\"http://www.example.com/ns/server/resource\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.com/ns/server/resource resource.xsd\" version=\"1\">  <res:message httpCode=\"200\" type=\"ok\">   <![CDATA[Sample Success Response]]>    </res:message>    <dif:person xmlns:dif=\"http://www.example.com/ns/server/resource\"                xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.com/ns/server/person person.xsd\" version=\"1\"> <dif:name>test name</dif:name><dif:description lang=\"en\">test description</dif:description>    </dif:person ></res:resource>";
        String formattedValidXml5 = "XML:\n" +
                "<res:resource xmlns:res=\"http://www.example.com/ns/server/resource\"\n" +
                "              xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "              xsi:schemaLocation=\"http://www.example.com/ns/server/resource resource.xsd\"\n" +
                "              version=\"1\">\n" +
                "  <res:message httpCode=\"200\" type=\"ok\">\n" +
                "    <![CDATA[Sample Success Response]]>\n" +
                "    </res:message>\n" +
                "    <dif:person xmlns:dif=\"http://www.example.com/ns/server/resource\"\n" +
                "                xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "                xsi:schemaLocation=\"http://www.example.com/ns/server/person person.xsd\"\n" +
                "                version=\"1\">\n" +
                "      <dif:name>test name</dif:name>\n" +
                "      <dif:description lang=\"en\">test description</dif:description>\n" +
                "    </dif:person>\n" +
                "</res:resource>";
        ALog.xml(validXml5);
        assertLogEquals(null, null, formattedValidXml5);
        ALog.xml(ALogLevel.INFO, validXml5);
        assertLogEquals(Log.INFO, null, formattedValidXml5);
    }
}
