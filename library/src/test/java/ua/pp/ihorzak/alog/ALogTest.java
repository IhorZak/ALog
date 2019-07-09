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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ua.pp.ihorzak.alog.test.BaseTest;
import ua.pp.ihorzak.alog.test.Utils;

import static ua.pp.ihorzak.alog.test.Utils.assertLogEquals;
import static ua.pp.ihorzak.alog.test.Utils.assertLogStartsWith;
import static ua.pp.ihorzak.alog.test.Utils.d;
import static ua.pp.ihorzak.alog.test.Utils.e;
import static ua.pp.ihorzak.alog.test.Utils.i;
import static ua.pp.ihorzak.alog.test.Utils.v;
import static ua.pp.ihorzak.alog.test.Utils.w;
import static ua.pp.ihorzak.alog.test.Utils.wtf;

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
        ALog.v();
        assertLogEquals(Log.VERBOSE, TAG, "");
    }

    @Test
    public void testV_ObjectNull() {
        ALog.v((Object) null);
        assertLogEquals(Log.VERBOSE, TAG, "null");
    }

    @Test
    public void testV_ObjectPrimitiveArray() {
        ALog.v(new int[] {1, 2, 3, 4});
        assertLogEquals(Log.VERBOSE, TAG, "Array(size = 4) [1, 2, 3, 4]");
    }

    @Test
    public void testV_ObjectPrimitiveWrapperArray() {
        ALog.v(new Float[] {1.0f, 2.0f, 3.5f});
        assertLogEquals(Log.VERBOSE, TAG, "Array(size = 3) [1.0, 2.0, 3.5]");
    }

    @Test
    public void testV_ObjectArray() {
        ALog.v(new String[] {"s1", "s2", "s3", "s4", "s5"});
        assertLogEquals(Log.VERBOSE, TAG, "Array(size = 5) [s1, s2, s3, s4, s5]");
    }

    @Test
    public void testV_ObjectTwoDimensionalArray() {
        ALog.v(new byte[][] {{1, 2, 3}, {4, 5, 6, 7}, {8, 9}});
        assertLogEquals(Log.VERBOSE, TAG, "Array(size = 3) [Array(size = 3) [1, 2, 3], Array(size = 4) [4, 5, 6, 7], Array(size = 2) [8, 9]]");
    }

    @Test
    public void testV_ObjectCollection() {
        List<Object> list = new ArrayList<>();
        list.add("Argument");
        list.add(5);
        list.add(1.0f);
        ALog.v(list);
        assertLogEquals(Log.VERBOSE, TAG, "java.util.ArrayList(size = 3) [Argument, 5, 1.0]");
    }

    @Test
    public void testV_ObjectMap() {
        Map<Long, Integer> map = new LinkedHashMap<>();
        map.put(1L, 45);
        map.put(2L, 76);
        map.put(3L, 100);
        ALog.v(map);
        assertLogEquals(Log.VERBOSE, TAG, "java.util.LinkedHashMap(size = 3) [{1 -> 45}, {2 -> 76}, {3 -> 100}]");
    }

    @Test
    public void testV_ObjectCustomFormatter() {
        ALogger logger = ALog.formatter(String.class, ALogFormatter.create((ALogFormatterDelegate<String>) object ->
                "String: length = " + object.length() + ", \"" + object + "\""
        ));
        logger.v("Argument");
        assertLogEquals(Log.VERBOSE, TAG, "String: length = 8, \"Argument\"");
    }

    @Test
    public void testV_FormattedMessageCustomFormatters() {
        Map<Class<?>, ALogFormatter<?>> formatterMap = new HashMap<>();
        formatterMap.put(String.class, ALogFormatter.create((ALogFormatterDelegate<String>) object ->
                "String: length = " + object.length() + ", \"" + object + "\""
        ));
        formatterMap.put(BigInteger.class, ALogFormatter.create((ALogFormatterDelegate<BigInteger>) object -> "BigInteger: " + object.longValue()));
        ALogger logger = ALog.formatters(formatterMap);
        logger.v("Message (%s, %s)", "s1", BigInteger.valueOf(1000L));
        assertLogEquals(Log.VERBOSE, TAG, "Message (String: length = 2, \"s1\", BigInteger: 1000)");
    }

    @Test
    public void testV_Message() {
        String message = "Message";
        ALog.v(message);
        assertLogEquals(Log.VERBOSE, TAG, message);
    }

    @Test
    public void testV_FormattedMessage() {
        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.v(messageFormat, stringArg, intArg);
        assertLogEquals(Log.VERBOSE, TAG, formattedMessage);
    }

    @Test
    public void testV_Throwable() {
        Throwable throwable = new RuntimeException();
        ALog.v(throwable);
        assertLogEquals(Log.VERBOSE, TAG, Log.getStackTraceString(throwable));
    }

    @Test
    public void testV_FormattedMessageThrowable() {
        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        Throwable throwable = new RuntimeException();
        ALog.v(throwable, messageFormat, stringArg, intArg);
        assertLogEquals(Log.VERBOSE, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testV_LongMessage() {
        String longMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet cursus sit amet dictum sit. Ante metus dictum at tempor commodo ullamcorper a lacus vestibulum. Non consectetur a erat nam at. Non tellus orci ac auctor augue mauris augue neque. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Scelerisque eu ultrices vitae auctor eu. Scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Quis enim lobortis scelerisque fermentum. Lectus nulla at volutpat diam ut venenatis tellus. Turpis egestas sed tempus urna et pharetra pharetra. Consequat semper viverra nam libero justo laoreet sit. Nunc scelerisque viverra mauris in aliquam sem fringilla. Elit sed vulputate mi sit amet mauris. Ut porttitor leo a diam sollicitudin tempor. Risus pretium quam vulputate dignissim suspendisse. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et. Ut porttitor leo a diam. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Massa sapien faucibus et molestie ac. Non consectetur a erat nam at lectus urna duis convallis. Posuere ac ut consequat semper viverra. Eu ultrices vitae auctor eu augue ut lectus. Netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non. Urna id volutpat lacus laoreet non curabitur. Sit amet consectetur adipiscing elit duis. Sapien nec sagittis aliquam malesuada bibendum. Et leo duis ut diam quam nulla. Pretium fusce id velit ut tortor pretium viverra suspendisse. Arcu dictum varius duis at consectetur. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Tincidunt id aliquet risus feugiat. Nisl rhoncus mattis rhoncus urna neque. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Aenean euismod elementum nisi quis. Blandit libero volutpat sed cras ornare. Lectus mauris ultrices eros in cursus. Nulla aliquet enim tortor at auctor. Nisi porta lorem mollis aliquam ut porttitor leo a diam. Integer quis auctor elit sed vulputate mi. Eu nisl nunc mi ipsum. Nisl condimentum id venenatis a. In fermentum posuere urna nec. Porttitor rhoncus dolor purus non enim praesent. Pellentesque adipiscing commodo elit at. Porta non pulvinar neque laoreet. Mi ipsum faucibus vitae aliquet. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Pellentesque adipiscing commodo elit at imperdiet dui. Sed augue lacus viverra vitae congue eu. Massa tincidunt dui ut ornare. Dui vivamus arcu felis bibendum ut tristique et egestas. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ut etiam sit amet nisl purus in mollis. Facilisi etiam dignissim diam quis. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Donec ultrices tincidunt arcu non sodales neque sodales. Hendrerit gravida rutrum quisque non tellus orci. Diam sit amet nisl suscipit adipiscing bibendum est. Risus pretium quam vulputate dignissim. Facilisi morbi tempus iaculis urna id volutpat lacus. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt. Sit amet nulla facilisi morbi tempus. Egestas quis ipsum suspendisse ultrices. Ornare suspendisse sed nisi lacus sed viverra. Dictum at tempor commodo ullamcorper a. Augue lacus viverra vitae congue eu consequat ac. Sed lectus vestibulum mattis ullamcorper. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Viverra aliquet eget sit amet tellus. Orci nulla pellentesque dignissim enim sit amet venenatis urna cursus. Volutpat lacus laoreet non curabitur gravida. Malesuada pellentesque elit eget gravida. Sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Diam sollicitudin tempor id eu nisl nunc. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Accumsan tortor posuere ac ut consequat. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Sed ullamcorper morbi tincidunt ornare. Risus viverra adipiscing at in tellus integer. Euismod lacinia at quis risus. Pellentesque dignissim enim sit amet venenatis. Sed risus ultricies tristique nulla aliquet. Fusce ut placerat orci nulla pellentesque dignissim enim sit. Eu tincidunt tortor aliquam nulla facilisi cras fermentum. Dignissim suspendisse in est ante in. Ullamcorper eget nulla facilisi etiam dignissim diam. Eu consequat ac felis donec et odio pellentesque. Consequat interdum varius sit amet mattis vulputate enim nulla. Sit amet nisl suscipit adipiscing bibendum est ultricies integer quis. Quisque sagittis purus sit amet volutpat. At in tellus integer feugiat scelerisque varius morbi enim nunc. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. Vulputate odio ut enim blandit volutpat. Et sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Auctor augue mauris augue neque gravida in fermentum. Etiam sit amet nisl purus in mollis nunc sed id. Iaculis nunc sed augue lacus viverra vitae congue. Donec ac odio tempor orci dapibus ultrices in iaculis nunc. Nisl condimentum id venenatis a. Id ornare arcu odio ut sem nulla pharetra diam sit. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Euismod in pellentesque massa placerat. Tincidunt vitae semper quis lectus. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. At elementum eu facilisis sed odio morbi quis commodo odio. Consequat id porta nibh venenatis. Et molestie ac feugiat sed lectus vestibulum. In iaculis nunc sed augue lacus viverra vitae congue. Mi sit amet mauris commodo quis. Sed tempus urna et pharetra. Vel turpis nunc eget lorem dolor sed viverra ipsum nunc. Nunc consequat interdum varius sit amet mattis vulputate enim. Tristique senectus et netus et malesuada fames. Aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod. Nulla posuere sollicitudin aliquam ultrices sagittis orci a scelerisque. Urna neque viverra justo nec ultrices dui. Sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum. At volutpat diam ut venenatis tellus in metus vulputate. Viverra mauris in aliquam sem fringilla ut morbi. Sed elementum tempus egestas sed sed risus pretium. Tempor orci eu lobortis elementum nibh tellus molestie. Vestibulum lorem sed risus ultricies. Nullam ac tortor vitae purus faucibus ornare suspendisse. Id velit ut tortor pretium viverra suspendisse potenti nullam ac. Risus sed vulputate odio ut. Turpis massa tincidunt dui ut ornare lectus sit amet est. Praesent semper feugiat nibh sed pulvinar proin gravida. Mi bibendum neque egestas congue quisque. Lacus viverra vitae congue eu consequat ac felis donec. Vitae auctor eu augue ut lectus arcu bibendum at. Commodo ullamcorper a lacus vestibulum sed arcu non. Facilisis sed odio morbi quis commodo odio aenean. Urna neque viverra justo nec ultrices dui sapien. Facilisi nullam vehicula ipsum a arcu cursus vitae. Arcu dictum varius duis at consectetur lorem donec.";
        String longMessageChunk1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet cursus sit amet dictum sit. Ante metus dictum at tempor commodo ullamcorper a lacus vestibulum. Non consectetur a erat nam at. Non tellus orci ac auctor augue mauris augue neque. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Scelerisque eu ultrices vitae auctor eu. Scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Quis enim lobortis scelerisque fermentum. Lectus nulla at volutpat diam ut venenatis tellus. Turpis egestas sed tempus urna et pharetra pharetra. Consequat semper viverra nam libero justo laoreet sit. Nunc scelerisque viverra mauris in aliquam sem fringilla. Elit sed vulputate mi sit amet mauris. Ut porttitor leo a diam sollicitudin tempor. Risus pretium quam vulputate dignissim suspendisse. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et. Ut porttitor leo a diam. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Massa sapien faucibus et molestie ac. Non consectetur a erat nam at lectus urna duis convallis. Posuere ac ut consequat semper viverra. Eu ultrices vitae auctor eu augue ut lectus. Netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non. Urna id volutpat lacus laoreet non curabitur. Sit amet consectetur adipiscing elit duis. Sapien nec sagittis aliquam malesuada bibendum. Et leo duis ut diam quam nulla. Pretium fusce id velit ut tortor pretium viverra suspendisse. Arcu dictum varius duis at consectetur. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Tincidunt id aliquet risus feugiat. Nisl rhoncus mattis rhoncus urna neque. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Aenean euismod elementum nisi quis. Blandit libero volutpat sed cras ornare. Lectus mauris ultrices eros in cursus. Nulla aliquet enim tortor at auctor. Nisi porta lorem mollis aliquam ut porttitor leo a diam. Integer quis auctor elit sed vulputate mi. Eu nisl nunc mi ipsum. Nisl condimentum id venenatis a. In fermentum posuere urna nec. Porttitor rhoncus dolor purus non enim praesent. Pellentesque adipiscing commodo elit at. Porta non pulvinar neque laoreet. Mi ipsum faucibus vitae aliquet. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Pellentesque adipiscing commodo elit at imperdiet dui. Sed augue lacus viverra vitae congue eu. Massa tincidunt dui ut ornare. Dui vivamus arcu felis bibendum ut tristique et egestas. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ut etiam sit amet nisl purus in mollis. Facilisi etiam dignissim diam quis. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Donec ultrices tincidunt arcu non sodales neque sodales. Hendrerit gravida rutrum quisque non tellus orci. Diam sit amet nisl suscipit adipiscing bibendum est. Risus pretium quam vulputate dignissim. Facilisi morbi tempus iaculis urna id volutpat lacus. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt. Sit amet nulla facilisi morbi tempus. Egestas quis ipsum suspendisse ultrices. Ornare suspendisse sed nisi lacus sed viverra. Dictum at tempor commodo ullamcorper a. Augue lacus viverra vitae congue eu consequat ac. Sed lectus vestibulum mattis ullamcorper. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Viverra aliquet eget sit amet tellus. Orci nulla pellentesque dignissim enim sit amet venenatis urna cursus. Volutpat lacus laoreet non curabitur gravida. Malesuada pellentesque elit eget gravida. Sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Diam sollic";
        String longMessageChunk2 = "itudin tempor id eu nisl nunc. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Accumsan tortor posuere ac ut consequat. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Sed ullamcorper morbi tincidunt ornare. Risus viverra adipiscing at in tellus integer. Euismod lacinia at quis risus. Pellentesque dignissim enim sit amet venenatis. Sed risus ultricies tristique nulla aliquet. Fusce ut placerat orci nulla pellentesque dignissim enim sit. Eu tincidunt tortor aliquam nulla facilisi cras fermentum. Dignissim suspendisse in est ante in. Ullamcorper eget nulla facilisi etiam dignissim diam. Eu consequat ac felis donec et odio pellentesque. Consequat interdum varius sit amet mattis vulputate enim nulla. Sit amet nisl suscipit adipiscing bibendum est ultricies integer quis. Quisque sagittis purus sit amet volutpat. At in tellus integer feugiat scelerisque varius morbi enim nunc. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. Vulputate odio ut enim blandit volutpat. Et sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Auctor augue mauris augue neque gravida in fermentum. Etiam sit amet nisl purus in mollis nunc sed id. Iaculis nunc sed augue lacus viverra vitae congue. Donec ac odio tempor orci dapibus ultrices in iaculis nunc. Nisl condimentum id venenatis a. Id ornare arcu odio ut sem nulla pharetra diam sit. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Euismod in pellentesque massa placerat. Tincidunt vitae semper quis lectus. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. At elementum eu facilisis sed odio morbi quis commodo odio. Consequat id porta nibh venenatis. Et molestie ac feugiat sed lectus vestibulum. In iaculis nunc sed augue lacus viverra vitae congue. Mi sit amet mauris commodo quis. Sed tempus urna et pharetra. Vel turpis nunc eget lorem dolor sed viverra ipsum nunc. Nunc consequat interdum varius sit amet mattis vulputate enim. Tristique senectus et netus et malesuada fames. Aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod. Nulla posuere sollicitudin aliquam ultrices sagittis orci a scelerisque. Urna neque viverra justo nec ultrices dui. Sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum. At volutpat diam ut venenatis tellus in metus vulputate. Viverra mauris in aliquam sem fringilla ut morbi. Sed elementum tempus egestas sed sed risus pretium. Tempor orci eu lobortis elementum nibh tellus molestie. Vestibulum lorem sed risus ultricies. Nullam ac tortor vitae purus faucibus ornare suspendisse. Id velit ut tortor pretium viverra suspendisse potenti nullam ac. Risus sed vulputate odio ut. Turpis massa tincidunt dui ut ornare lectus sit amet est. Praesent semper feugiat nibh sed pulvinar proin gravida. Mi bibendum neque egestas congue quisque. Lacus viverra vitae congue eu consequat ac felis donec. Vitae auctor eu augue ut lectus arcu bibendum at. Commodo ullamcorper a lacus vestibulum sed arcu non. Facilisis sed odio morbi quis commodo odio aenean. Urna neque viverra justo nec ultrices dui sapien. Facilisi nullam vehicula ipsum a arcu cursus vitae. Arcu dictum varius duis at consectetur lorem donec.";
        ALog.v(longMessage);
        assertLogEquals(Log.VERBOSE, TAG, longMessageChunk1, longMessageChunk2);
    }

    @Test
    public void testD() {
        ALog.d();
        assertLogEquals(Log.DEBUG, TAG, "");
    }

    @Test
    public void testD_ObjectNull() {
        ALog.d((Object) null);
        assertLogEquals(Log.DEBUG, TAG, "null");
    }

    @Test
    public void testD_ObjectPrimitiveArray() {
        ALog.d(new int[] {1, 2, 3, 4});
        assertLogEquals(Log.DEBUG, TAG, "Array(size = 4) [1, 2, 3, 4]");
    }

    @Test
    public void testD_ObjectPrimitiveWrapperArray() {
        ALog.d(new Float[] {1.0f, 2.0f, 3.5f});
        assertLogEquals(Log.DEBUG, TAG, "Array(size = 3) [1.0, 2.0, 3.5]");
    }

    @Test
    public void testD_ObjectArray() {
        ALog.d(new String[] {"s1", "s2", "s3", "s4", "s5"});
        assertLogEquals(Log.DEBUG, TAG, "Array(size = 5) [s1, s2, s3, s4, s5]");
    }

    @Test
    public void testD_ObjectTwoDimensionalArray() {
        ALog.d(new byte[][] {{1, 2, 3}, {4, 5, 6, 7}, {8, 9}});
        assertLogEquals(Log.DEBUG, TAG, "Array(size = 3) [Array(size = 3) [1, 2, 3], Array(size = 4) [4, 5, 6, 7], Array(size = 2) [8, 9]]");
    }

    @Test
    public void testD_ObjectCollection() {
        List<Object> list = new ArrayList<>();
        list.add("Argument");
        list.add(5);
        list.add(1.0f);
        ALog.d(list);
        assertLogEquals(Log.DEBUG, TAG, "java.util.ArrayList(size = 3) [Argument, 5, 1.0]");
    }

    @Test
    public void testD_ObjectMap() {
        Map<Long, Integer> map = new LinkedHashMap<>();
        map.put(1L, 45);
        map.put(2L, 76);
        map.put(3L, 100);
        ALog.d(map);
        assertLogEquals(Log.DEBUG, TAG, "java.util.LinkedHashMap(size = 3) [{1 -> 45}, {2 -> 76}, {3 -> 100}]");
    }

    @Test
    public void testD_ObjectCustomFormatter() {
        ALogger logger = ALog.formatter(String.class, ALogFormatter.create((ALogFormatterDelegate<String>) object ->
                "String: length = " + object.length() + ", \"" + object + "\""
        ));
        logger.d("Argument");
        assertLogEquals(Log.DEBUG, TAG, "String: length = 8, \"Argument\"");
    }

    @Test
    public void testD_FormattedMessageCustomFormatters() {
        Map<Class<?>, ALogFormatter<?>> formatterMap = new HashMap<>();
        formatterMap.put(String.class, ALogFormatter.create((ALogFormatterDelegate<String>) object ->
                "String: length = " + object.length() + ", \"" + object + "\""
        ));
        formatterMap.put(BigInteger.class, ALogFormatter.create((ALogFormatterDelegate<BigInteger>) object ->
                "BigInteger: " + object.longValue()
        ));
        ALogger logger = ALog.formatters(formatterMap);
        logger.d("Message (%s, %s)", "s1", BigInteger.valueOf(1000L));
        assertLogEquals(Log.DEBUG, TAG, "Message (String: length = 2, \"s1\", BigInteger: 1000)");
    }
    
    @Test
    public void testD_Message() {
        String message = "Message";
        ALog.d(message);
        assertLogEquals(Log.DEBUG, TAG, message);
    }

    @Test
    public void testD_FormattedMessage() {
        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.d(messageFormat, stringArg, intArg);
        assertLogEquals(Log.DEBUG, TAG, formattedMessage);
    }

    @Test
    public void testD_Throwable() {
        Throwable throwable = new RuntimeException();
        ALog.d(throwable);
        assertLogEquals(Log.DEBUG, TAG, Log.getStackTraceString(throwable));
    }

    @Test
    public void testD_FormattedMessageThrowable() {
        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        Throwable throwable = new RuntimeException();
        ALog.d(throwable, messageFormat, stringArg, intArg);
        assertLogEquals(Log.DEBUG, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testD_LongMessage() {
        String longMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet cursus sit amet dictum sit. Ante metus dictum at tempor commodo ullamcorper a lacus vestibulum. Non consectetur a erat nam at. Non tellus orci ac auctor augue mauris augue neque. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Scelerisque eu ultrices vitae auctor eu. Scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Quis enim lobortis scelerisque fermentum. Lectus nulla at volutpat diam ut venenatis tellus. Turpis egestas sed tempus urna et pharetra pharetra. Consequat semper viverra nam libero justo laoreet sit. Nunc scelerisque viverra mauris in aliquam sem fringilla. Elit sed vulputate mi sit amet mauris. Ut porttitor leo a diam sollicitudin tempor. Risus pretium quam vulputate dignissim suspendisse. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et. Ut porttitor leo a diam. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Massa sapien faucibus et molestie ac. Non consectetur a erat nam at lectus urna duis convallis. Posuere ac ut consequat semper viverra. Eu ultrices vitae auctor eu augue ut lectus. Netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non. Urna id volutpat lacus laoreet non curabitur. Sit amet consectetur adipiscing elit duis. Sapien nec sagittis aliquam malesuada bibendum. Et leo duis ut diam quam nulla. Pretium fusce id velit ut tortor pretium viverra suspendisse. Arcu dictum varius duis at consectetur. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Tincidunt id aliquet risus feugiat. Nisl rhoncus mattis rhoncus urna neque. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Aenean euismod elementum nisi quis. Blandit libero volutpat sed cras ornare. Lectus mauris ultrices eros in cursus. Nulla aliquet enim tortor at auctor. Nisi porta lorem mollis aliquam ut porttitor leo a diam. Integer quis auctor elit sed vulputate mi. Eu nisl nunc mi ipsum. Nisl condimentum id venenatis a. In fermentum posuere urna nec. Porttitor rhoncus dolor purus non enim praesent. Pellentesque adipiscing commodo elit at. Porta non pulvinar neque laoreet. Mi ipsum faucibus vitae aliquet. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Pellentesque adipiscing commodo elit at imperdiet dui. Sed augue lacus viverra vitae congue eu. Massa tincidunt dui ut ornare. Dui vivamus arcu felis bibendum ut tristique et egestas. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ut etiam sit amet nisl purus in mollis. Facilisi etiam dignissim diam quis. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Donec ultrices tincidunt arcu non sodales neque sodales. Hendrerit gravida rutrum quisque non tellus orci. Diam sit amet nisl suscipit adipiscing bibendum est. Risus pretium quam vulputate dignissim. Facilisi morbi tempus iaculis urna id volutpat lacus. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt. Sit amet nulla facilisi morbi tempus. Egestas quis ipsum suspendisse ultrices. Ornare suspendisse sed nisi lacus sed viverra. Dictum at tempor commodo ullamcorper a. Augue lacus viverra vitae congue eu consequat ac. Sed lectus vestibulum mattis ullamcorper. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Viverra aliquet eget sit amet tellus. Orci nulla pellentesque dignissim enim sit amet venenatis urna cursus. Volutpat lacus laoreet non curabitur gravida. Malesuada pellentesque elit eget gravida. Sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Diam sollicitudin tempor id eu nisl nunc. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Accumsan tortor posuere ac ut consequat. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Sed ullamcorper morbi tincidunt ornare. Risus viverra adipiscing at in tellus integer. Euismod lacinia at quis risus. Pellentesque dignissim enim sit amet venenatis. Sed risus ultricies tristique nulla aliquet. Fusce ut placerat orci nulla pellentesque dignissim enim sit. Eu tincidunt tortor aliquam nulla facilisi cras fermentum. Dignissim suspendisse in est ante in. Ullamcorper eget nulla facilisi etiam dignissim diam. Eu consequat ac felis donec et odio pellentesque. Consequat interdum varius sit amet mattis vulputate enim nulla. Sit amet nisl suscipit adipiscing bibendum est ultricies integer quis. Quisque sagittis purus sit amet volutpat. At in tellus integer feugiat scelerisque varius morbi enim nunc. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. Vulputate odio ut enim blandit volutpat. Et sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Auctor augue mauris augue neque gravida in fermentum. Etiam sit amet nisl purus in mollis nunc sed id. Iaculis nunc sed augue lacus viverra vitae congue. Donec ac odio tempor orci dapibus ultrices in iaculis nunc. Nisl condimentum id venenatis a. Id ornare arcu odio ut sem nulla pharetra diam sit. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Euismod in pellentesque massa placerat. Tincidunt vitae semper quis lectus. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. At elementum eu facilisis sed odio morbi quis commodo odio. Consequat id porta nibh venenatis. Et molestie ac feugiat sed lectus vestibulum. In iaculis nunc sed augue lacus viverra vitae congue. Mi sit amet mauris commodo quis. Sed tempus urna et pharetra. Vel turpis nunc eget lorem dolor sed viverra ipsum nunc. Nunc consequat interdum varius sit amet mattis vulputate enim. Tristique senectus et netus et malesuada fames. Aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod. Nulla posuere sollicitudin aliquam ultrices sagittis orci a scelerisque. Urna neque viverra justo nec ultrices dui. Sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum. At volutpat diam ut venenatis tellus in metus vulputate. Viverra mauris in aliquam sem fringilla ut morbi. Sed elementum tempus egestas sed sed risus pretium. Tempor orci eu lobortis elementum nibh tellus molestie. Vestibulum lorem sed risus ultricies. Nullam ac tortor vitae purus faucibus ornare suspendisse. Id velit ut tortor pretium viverra suspendisse potenti nullam ac. Risus sed vulputate odio ut. Turpis massa tincidunt dui ut ornare lectus sit amet est. Praesent semper feugiat nibh sed pulvinar proin gravida. Mi bibendum neque egestas congue quisque. Lacus viverra vitae congue eu consequat ac felis donec. Vitae auctor eu augue ut lectus arcu bibendum at. Commodo ullamcorper a lacus vestibulum sed arcu non. Facilisis sed odio morbi quis commodo odio aenean. Urna neque viverra justo nec ultrices dui sapien. Facilisi nullam vehicula ipsum a arcu cursus vitae. Arcu dictum varius duis at consectetur lorem donec.";
        String longMessageChunk1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet cursus sit amet dictum sit. Ante metus dictum at tempor commodo ullamcorper a lacus vestibulum. Non consectetur a erat nam at. Non tellus orci ac auctor augue mauris augue neque. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Scelerisque eu ultrices vitae auctor eu. Scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Quis enim lobortis scelerisque fermentum. Lectus nulla at volutpat diam ut venenatis tellus. Turpis egestas sed tempus urna et pharetra pharetra. Consequat semper viverra nam libero justo laoreet sit. Nunc scelerisque viverra mauris in aliquam sem fringilla. Elit sed vulputate mi sit amet mauris. Ut porttitor leo a diam sollicitudin tempor. Risus pretium quam vulputate dignissim suspendisse. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et. Ut porttitor leo a diam. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Massa sapien faucibus et molestie ac. Non consectetur a erat nam at lectus urna duis convallis. Posuere ac ut consequat semper viverra. Eu ultrices vitae auctor eu augue ut lectus. Netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non. Urna id volutpat lacus laoreet non curabitur. Sit amet consectetur adipiscing elit duis. Sapien nec sagittis aliquam malesuada bibendum. Et leo duis ut diam quam nulla. Pretium fusce id velit ut tortor pretium viverra suspendisse. Arcu dictum varius duis at consectetur. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Tincidunt id aliquet risus feugiat. Nisl rhoncus mattis rhoncus urna neque. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Aenean euismod elementum nisi quis. Blandit libero volutpat sed cras ornare. Lectus mauris ultrices eros in cursus. Nulla aliquet enim tortor at auctor. Nisi porta lorem mollis aliquam ut porttitor leo a diam. Integer quis auctor elit sed vulputate mi. Eu nisl nunc mi ipsum. Nisl condimentum id venenatis a. In fermentum posuere urna nec. Porttitor rhoncus dolor purus non enim praesent. Pellentesque adipiscing commodo elit at. Porta non pulvinar neque laoreet. Mi ipsum faucibus vitae aliquet. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Pellentesque adipiscing commodo elit at imperdiet dui. Sed augue lacus viverra vitae congue eu. Massa tincidunt dui ut ornare. Dui vivamus arcu felis bibendum ut tristique et egestas. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ut etiam sit amet nisl purus in mollis. Facilisi etiam dignissim diam quis. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Donec ultrices tincidunt arcu non sodales neque sodales. Hendrerit gravida rutrum quisque non tellus orci. Diam sit amet nisl suscipit adipiscing bibendum est. Risus pretium quam vulputate dignissim. Facilisi morbi tempus iaculis urna id volutpat lacus. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt. Sit amet nulla facilisi morbi tempus. Egestas quis ipsum suspendisse ultrices. Ornare suspendisse sed nisi lacus sed viverra. Dictum at tempor commodo ullamcorper a. Augue lacus viverra vitae congue eu consequat ac. Sed lectus vestibulum mattis ullamcorper. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Viverra aliquet eget sit amet tellus. Orci nulla pellentesque dignissim enim sit amet venenatis urna cursus. Volutpat lacus laoreet non curabitur gravida. Malesuada pellentesque elit eget gravida. Sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Diam sollic";
        String longMessageChunk2 = "itudin tempor id eu nisl nunc. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Accumsan tortor posuere ac ut consequat. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Sed ullamcorper morbi tincidunt ornare. Risus viverra adipiscing at in tellus integer. Euismod lacinia at quis risus. Pellentesque dignissim enim sit amet venenatis. Sed risus ultricies tristique nulla aliquet. Fusce ut placerat orci nulla pellentesque dignissim enim sit. Eu tincidunt tortor aliquam nulla facilisi cras fermentum. Dignissim suspendisse in est ante in. Ullamcorper eget nulla facilisi etiam dignissim diam. Eu consequat ac felis donec et odio pellentesque. Consequat interdum varius sit amet mattis vulputate enim nulla. Sit amet nisl suscipit adipiscing bibendum est ultricies integer quis. Quisque sagittis purus sit amet volutpat. At in tellus integer feugiat scelerisque varius morbi enim nunc. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. Vulputate odio ut enim blandit volutpat. Et sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Auctor augue mauris augue neque gravida in fermentum. Etiam sit amet nisl purus in mollis nunc sed id. Iaculis nunc sed augue lacus viverra vitae congue. Donec ac odio tempor orci dapibus ultrices in iaculis nunc. Nisl condimentum id venenatis a. Id ornare arcu odio ut sem nulla pharetra diam sit. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Euismod in pellentesque massa placerat. Tincidunt vitae semper quis lectus. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. At elementum eu facilisis sed odio morbi quis commodo odio. Consequat id porta nibh venenatis. Et molestie ac feugiat sed lectus vestibulum. In iaculis nunc sed augue lacus viverra vitae congue. Mi sit amet mauris commodo quis. Sed tempus urna et pharetra. Vel turpis nunc eget lorem dolor sed viverra ipsum nunc. Nunc consequat interdum varius sit amet mattis vulputate enim. Tristique senectus et netus et malesuada fames. Aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod. Nulla posuere sollicitudin aliquam ultrices sagittis orci a scelerisque. Urna neque viverra justo nec ultrices dui. Sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum. At volutpat diam ut venenatis tellus in metus vulputate. Viverra mauris in aliquam sem fringilla ut morbi. Sed elementum tempus egestas sed sed risus pretium. Tempor orci eu lobortis elementum nibh tellus molestie. Vestibulum lorem sed risus ultricies. Nullam ac tortor vitae purus faucibus ornare suspendisse. Id velit ut tortor pretium viverra suspendisse potenti nullam ac. Risus sed vulputate odio ut. Turpis massa tincidunt dui ut ornare lectus sit amet est. Praesent semper feugiat nibh sed pulvinar proin gravida. Mi bibendum neque egestas congue quisque. Lacus viverra vitae congue eu consequat ac felis donec. Vitae auctor eu augue ut lectus arcu bibendum at. Commodo ullamcorper a lacus vestibulum sed arcu non. Facilisis sed odio morbi quis commodo odio aenean. Urna neque viverra justo nec ultrices dui sapien. Facilisi nullam vehicula ipsum a arcu cursus vitae. Arcu dictum varius duis at consectetur lorem donec.";
        ALog.d(longMessage);
        assertLogEquals(Log.DEBUG, TAG, longMessageChunk1, longMessageChunk2);
    }

    @Test
    public void testI() {
        ALog.i();
        assertLogEquals(Log.INFO, TAG, "");
    }

    @Test
    public void testI_ObjectNull() {
        ALog.i((Object) null);
        assertLogEquals(Log.INFO, TAG, "null");
    }

    @Test
    public void testI_ObjectPrimitiveArray() {
        ALog.i(new int[] {1, 2, 3, 4});
        assertLogEquals(Log.INFO, TAG, "Array(size = 4) [1, 2, 3, 4]");
    }

    @Test
    public void testI_ObjectPrimitiveWrapperArray() {
        ALog.i(new Float[] {1.0f, 2.0f, 3.5f});
        assertLogEquals(Log.INFO, TAG, "Array(size = 3) [1.0, 2.0, 3.5]");
    }

    @Test
    public void testI_ObjectArray() {
        ALog.i(new String[] {"s1", "s2", "s3", "s4", "s5"});
        assertLogEquals(Log.INFO, TAG, "Array(size = 5) [s1, s2, s3, s4, s5]");
    }

    @Test
    public void testI_ObjectTwoDimensionalArray() {
        ALog.i(new byte[][] {{1, 2, 3}, {4, 5, 6, 7}, {8, 9}});
        assertLogEquals(Log.INFO, TAG, "Array(size = 3) [Array(size = 3) [1, 2, 3], Array(size = 4) [4, 5, 6, 7], Array(size = 2) [8, 9]]");
    }

    @Test
    public void testI_ObjectCollection() {
        List<Object> list = new ArrayList<>();
        list.add("Argument");
        list.add(5);
        list.add(1.0f);
        ALog.i(list);
        assertLogEquals(Log.INFO, TAG, "java.util.ArrayList(size = 3) [Argument, 5, 1.0]");
    }

    @Test
    public void testI_ObjectMap() {
        Map<Long, Integer> map = new LinkedHashMap<>();
        map.put(1L, 45);
        map.put(2L, 76);
        map.put(3L, 100);
        ALog.i(map);
        assertLogEquals(Log.INFO, TAG, "java.util.LinkedHashMap(size = 3) [{1 -> 45}, {2 -> 76}, {3 -> 100}]");
    }

    @Test
    public void testI_ObjectCustomFormatter() {
        ALogger logger = ALog.formatter(String.class, ALogFormatter.create((ALogFormatterDelegate<String>) object ->
                "String: length = " + object.length() + ", \"" + object + "\""
        ));
        logger.i("Argument");
        assertLogEquals(Log.INFO, TAG, "String: length = 8, \"Argument\"");
    }

    @Test
    public void testI_FormattedMessageCustomFormatters() {
        Map<Class<?>, ALogFormatter<?>> formatterMap = new HashMap<>();
        formatterMap.put(String.class, ALogFormatter.create((ALogFormatterDelegate<String>) object ->
                "String: length = " + object.length() + ", \"" + object + "\""
        ));
        formatterMap.put(BigInteger.class, ALogFormatter.create((ALogFormatterDelegate<BigInteger>) object ->
                "BigInteger: " + object.longValue()
        ));
        ALogger logger = ALog.formatters(formatterMap);
        logger.i("Message (%s, %s)", "s1", BigInteger.valueOf(1000L));
        assertLogEquals(Log.INFO, TAG, "Message (String: length = 2, \"s1\", BigInteger: 1000)");
    }
    
    @Test
    public void testI_Message() {
        String message = "Message";
        ALog.i(message);
        assertLogEquals(Log.INFO, TAG, message);
    }

    @Test
    public void testI_FormattedMessage() {
        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.i(messageFormat, stringArg, intArg);
        assertLogEquals(Log.INFO, TAG, formattedMessage);
    }

    @Test
    public void testI_Throwable() {
        Throwable throwable = new RuntimeException();
        ALog.i(throwable);
        assertLogEquals(Log.INFO, TAG, Log.getStackTraceString(throwable));
    }

    @Test
    public void testI_FormattedMessageThrowable() {
        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        Throwable throwable = new RuntimeException();
        ALog.i(throwable, messageFormat, stringArg, intArg);
        assertLogEquals(Log.INFO, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testI_LongMessage() {
        String longMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet cursus sit amet dictum sit. Ante metus dictum at tempor commodo ullamcorper a lacus vestibulum. Non consectetur a erat nam at. Non tellus orci ac auctor augue mauris augue neque. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Scelerisque eu ultrices vitae auctor eu. Scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Quis enim lobortis scelerisque fermentum. Lectus nulla at volutpat diam ut venenatis tellus. Turpis egestas sed tempus urna et pharetra pharetra. Consequat semper viverra nam libero justo laoreet sit. Nunc scelerisque viverra mauris in aliquam sem fringilla. Elit sed vulputate mi sit amet mauris. Ut porttitor leo a diam sollicitudin tempor. Risus pretium quam vulputate dignissim suspendisse. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et. Ut porttitor leo a diam. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Massa sapien faucibus et molestie ac. Non consectetur a erat nam at lectus urna duis convallis. Posuere ac ut consequat semper viverra. Eu ultrices vitae auctor eu augue ut lectus. Netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non. Urna id volutpat lacus laoreet non curabitur. Sit amet consectetur adipiscing elit duis. Sapien nec sagittis aliquam malesuada bibendum. Et leo duis ut diam quam nulla. Pretium fusce id velit ut tortor pretium viverra suspendisse. Arcu dictum varius duis at consectetur. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Tincidunt id aliquet risus feugiat. Nisl rhoncus mattis rhoncus urna neque. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Aenean euismod elementum nisi quis. Blandit libero volutpat sed cras ornare. Lectus mauris ultrices eros in cursus. Nulla aliquet enim tortor at auctor. Nisi porta lorem mollis aliquam ut porttitor leo a diam. Integer quis auctor elit sed vulputate mi. Eu nisl nunc mi ipsum. Nisl condimentum id venenatis a. In fermentum posuere urna nec. Porttitor rhoncus dolor purus non enim praesent. Pellentesque adipiscing commodo elit at. Porta non pulvinar neque laoreet. Mi ipsum faucibus vitae aliquet. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Pellentesque adipiscing commodo elit at imperdiet dui. Sed augue lacus viverra vitae congue eu. Massa tincidunt dui ut ornare. Dui vivamus arcu felis bibendum ut tristique et egestas. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ut etiam sit amet nisl purus in mollis. Facilisi etiam dignissim diam quis. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Donec ultrices tincidunt arcu non sodales neque sodales. Hendrerit gravida rutrum quisque non tellus orci. Diam sit amet nisl suscipit adipiscing bibendum est. Risus pretium quam vulputate dignissim. Facilisi morbi tempus iaculis urna id volutpat lacus. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt. Sit amet nulla facilisi morbi tempus. Egestas quis ipsum suspendisse ultrices. Ornare suspendisse sed nisi lacus sed viverra. Dictum at tempor commodo ullamcorper a. Augue lacus viverra vitae congue eu consequat ac. Sed lectus vestibulum mattis ullamcorper. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Viverra aliquet eget sit amet tellus. Orci nulla pellentesque dignissim enim sit amet venenatis urna cursus. Volutpat lacus laoreet non curabitur gravida. Malesuada pellentesque elit eget gravida. Sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Diam sollicitudin tempor id eu nisl nunc. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Accumsan tortor posuere ac ut consequat. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Sed ullamcorper morbi tincidunt ornare. Risus viverra adipiscing at in tellus integer. Euismod lacinia at quis risus. Pellentesque dignissim enim sit amet venenatis. Sed risus ultricies tristique nulla aliquet. Fusce ut placerat orci nulla pellentesque dignissim enim sit. Eu tincidunt tortor aliquam nulla facilisi cras fermentum. Dignissim suspendisse in est ante in. Ullamcorper eget nulla facilisi etiam dignissim diam. Eu consequat ac felis donec et odio pellentesque. Consequat interdum varius sit amet mattis vulputate enim nulla. Sit amet nisl suscipit adipiscing bibendum est ultricies integer quis. Quisque sagittis purus sit amet volutpat. At in tellus integer feugiat scelerisque varius morbi enim nunc. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. Vulputate odio ut enim blandit volutpat. Et sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Auctor augue mauris augue neque gravida in fermentum. Etiam sit amet nisl purus in mollis nunc sed id. Iaculis nunc sed augue lacus viverra vitae congue. Donec ac odio tempor orci dapibus ultrices in iaculis nunc. Nisl condimentum id venenatis a. Id ornare arcu odio ut sem nulla pharetra diam sit. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Euismod in pellentesque massa placerat. Tincidunt vitae semper quis lectus. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. At elementum eu facilisis sed odio morbi quis commodo odio. Consequat id porta nibh venenatis. Et molestie ac feugiat sed lectus vestibulum. In iaculis nunc sed augue lacus viverra vitae congue. Mi sit amet mauris commodo quis. Sed tempus urna et pharetra. Vel turpis nunc eget lorem dolor sed viverra ipsum nunc. Nunc consequat interdum varius sit amet mattis vulputate enim. Tristique senectus et netus et malesuada fames. Aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod. Nulla posuere sollicitudin aliquam ultrices sagittis orci a scelerisque. Urna neque viverra justo nec ultrices dui. Sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum. At volutpat diam ut venenatis tellus in metus vulputate. Viverra mauris in aliquam sem fringilla ut morbi. Sed elementum tempus egestas sed sed risus pretium. Tempor orci eu lobortis elementum nibh tellus molestie. Vestibulum lorem sed risus ultricies. Nullam ac tortor vitae purus faucibus ornare suspendisse. Id velit ut tortor pretium viverra suspendisse potenti nullam ac. Risus sed vulputate odio ut. Turpis massa tincidunt dui ut ornare lectus sit amet est. Praesent semper feugiat nibh sed pulvinar proin gravida. Mi bibendum neque egestas congue quisque. Lacus viverra vitae congue eu consequat ac felis donec. Vitae auctor eu augue ut lectus arcu bibendum at. Commodo ullamcorper a lacus vestibulum sed arcu non. Facilisis sed odio morbi quis commodo odio aenean. Urna neque viverra justo nec ultrices dui sapien. Facilisi nullam vehicula ipsum a arcu cursus vitae. Arcu dictum varius duis at consectetur lorem donec.";
        String longMessageChunk1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet cursus sit amet dictum sit. Ante metus dictum at tempor commodo ullamcorper a lacus vestibulum. Non consectetur a erat nam at. Non tellus orci ac auctor augue mauris augue neque. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Scelerisque eu ultrices vitae auctor eu. Scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Quis enim lobortis scelerisque fermentum. Lectus nulla at volutpat diam ut venenatis tellus. Turpis egestas sed tempus urna et pharetra pharetra. Consequat semper viverra nam libero justo laoreet sit. Nunc scelerisque viverra mauris in aliquam sem fringilla. Elit sed vulputate mi sit amet mauris. Ut porttitor leo a diam sollicitudin tempor. Risus pretium quam vulputate dignissim suspendisse. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et. Ut porttitor leo a diam. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Massa sapien faucibus et molestie ac. Non consectetur a erat nam at lectus urna duis convallis. Posuere ac ut consequat semper viverra. Eu ultrices vitae auctor eu augue ut lectus. Netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non. Urna id volutpat lacus laoreet non curabitur. Sit amet consectetur adipiscing elit duis. Sapien nec sagittis aliquam malesuada bibendum. Et leo duis ut diam quam nulla. Pretium fusce id velit ut tortor pretium viverra suspendisse. Arcu dictum varius duis at consectetur. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Tincidunt id aliquet risus feugiat. Nisl rhoncus mattis rhoncus urna neque. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Aenean euismod elementum nisi quis. Blandit libero volutpat sed cras ornare. Lectus mauris ultrices eros in cursus. Nulla aliquet enim tortor at auctor. Nisi porta lorem mollis aliquam ut porttitor leo a diam. Integer quis auctor elit sed vulputate mi. Eu nisl nunc mi ipsum. Nisl condimentum id venenatis a. In fermentum posuere urna nec. Porttitor rhoncus dolor purus non enim praesent. Pellentesque adipiscing commodo elit at. Porta non pulvinar neque laoreet. Mi ipsum faucibus vitae aliquet. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Pellentesque adipiscing commodo elit at imperdiet dui. Sed augue lacus viverra vitae congue eu. Massa tincidunt dui ut ornare. Dui vivamus arcu felis bibendum ut tristique et egestas. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ut etiam sit amet nisl purus in mollis. Facilisi etiam dignissim diam quis. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Donec ultrices tincidunt arcu non sodales neque sodales. Hendrerit gravida rutrum quisque non tellus orci. Diam sit amet nisl suscipit adipiscing bibendum est. Risus pretium quam vulputate dignissim. Facilisi morbi tempus iaculis urna id volutpat lacus. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt. Sit amet nulla facilisi morbi tempus. Egestas quis ipsum suspendisse ultrices. Ornare suspendisse sed nisi lacus sed viverra. Dictum at tempor commodo ullamcorper a. Augue lacus viverra vitae congue eu consequat ac. Sed lectus vestibulum mattis ullamcorper. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Viverra aliquet eget sit amet tellus. Orci nulla pellentesque dignissim enim sit amet venenatis urna cursus. Volutpat lacus laoreet non curabitur gravida. Malesuada pellentesque elit eget gravida. Sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Diam sollic";
        String longMessageChunk2 = "itudin tempor id eu nisl nunc. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Accumsan tortor posuere ac ut consequat. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Sed ullamcorper morbi tincidunt ornare. Risus viverra adipiscing at in tellus integer. Euismod lacinia at quis risus. Pellentesque dignissim enim sit amet venenatis. Sed risus ultricies tristique nulla aliquet. Fusce ut placerat orci nulla pellentesque dignissim enim sit. Eu tincidunt tortor aliquam nulla facilisi cras fermentum. Dignissim suspendisse in est ante in. Ullamcorper eget nulla facilisi etiam dignissim diam. Eu consequat ac felis donec et odio pellentesque. Consequat interdum varius sit amet mattis vulputate enim nulla. Sit amet nisl suscipit adipiscing bibendum est ultricies integer quis. Quisque sagittis purus sit amet volutpat. At in tellus integer feugiat scelerisque varius morbi enim nunc. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. Vulputate odio ut enim blandit volutpat. Et sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Auctor augue mauris augue neque gravida in fermentum. Etiam sit amet nisl purus in mollis nunc sed id. Iaculis nunc sed augue lacus viverra vitae congue. Donec ac odio tempor orci dapibus ultrices in iaculis nunc. Nisl condimentum id venenatis a. Id ornare arcu odio ut sem nulla pharetra diam sit. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Euismod in pellentesque massa placerat. Tincidunt vitae semper quis lectus. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. At elementum eu facilisis sed odio morbi quis commodo odio. Consequat id porta nibh venenatis. Et molestie ac feugiat sed lectus vestibulum. In iaculis nunc sed augue lacus viverra vitae congue. Mi sit amet mauris commodo quis. Sed tempus urna et pharetra. Vel turpis nunc eget lorem dolor sed viverra ipsum nunc. Nunc consequat interdum varius sit amet mattis vulputate enim. Tristique senectus et netus et malesuada fames. Aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod. Nulla posuere sollicitudin aliquam ultrices sagittis orci a scelerisque. Urna neque viverra justo nec ultrices dui. Sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum. At volutpat diam ut venenatis tellus in metus vulputate. Viverra mauris in aliquam sem fringilla ut morbi. Sed elementum tempus egestas sed sed risus pretium. Tempor orci eu lobortis elementum nibh tellus molestie. Vestibulum lorem sed risus ultricies. Nullam ac tortor vitae purus faucibus ornare suspendisse. Id velit ut tortor pretium viverra suspendisse potenti nullam ac. Risus sed vulputate odio ut. Turpis massa tincidunt dui ut ornare lectus sit amet est. Praesent semper feugiat nibh sed pulvinar proin gravida. Mi bibendum neque egestas congue quisque. Lacus viverra vitae congue eu consequat ac felis donec. Vitae auctor eu augue ut lectus arcu bibendum at. Commodo ullamcorper a lacus vestibulum sed arcu non. Facilisis sed odio morbi quis commodo odio aenean. Urna neque viverra justo nec ultrices dui sapien. Facilisi nullam vehicula ipsum a arcu cursus vitae. Arcu dictum varius duis at consectetur lorem donec.";
        ALog.i(longMessage);
        assertLogEquals(Log.INFO, TAG, longMessageChunk1, longMessageChunk2);
    }

    @Test
    public void testW() {
        ALog.w();
        assertLogEquals(Log.WARN, TAG, "");
    }

    @Test
    public void testW_ObjectNull() {
        ALog.w((Object) null);
        assertLogEquals(Log.WARN, TAG, "null");
    }

    @Test
    public void testW_ObjectPrimitiveArray() {
        ALog.w(new int[] {1, 2, 3, 4});
        assertLogEquals(Log.WARN, TAG, "Array(size = 4) [1, 2, 3, 4]");
    }

    @Test
    public void testW_ObjectPrimitiveWrapperArray() {
        ALog.w(new Float[] {1.0f, 2.0f, 3.5f});
        assertLogEquals(Log.WARN, TAG, "Array(size = 3) [1.0, 2.0, 3.5]");
    }

    @Test
    public void testW_ObjectArray() {
        ALog.w(new String[] {"s1", "s2", "s3", "s4", "s5"});
        assertLogEquals(Log.WARN, TAG, "Array(size = 5) [s1, s2, s3, s4, s5]");
    }

    @Test
    public void testW_ObjectTwoDimensionalArray() {
        ALog.w(new byte[][] {{1, 2, 3}, {4, 5, 6, 7}, {8, 9}});
        assertLogEquals(Log.WARN, TAG, "Array(size = 3) [Array(size = 3) [1, 2, 3], Array(size = 4) [4, 5, 6, 7], Array(size = 2) [8, 9]]");
    }

    @Test
    public void testW_ObjectCollection() {
        List<Object> list = new ArrayList<>();
        list.add("Argument");
        list.add(5);
        list.add(1.0f);
        ALog.w(list);
        assertLogEquals(Log.WARN, TAG, "java.util.ArrayList(size = 3) [Argument, 5, 1.0]");
    }

    @Test
    public void testW_ObjectMap() {
        Map<Long, Integer> map = new LinkedHashMap<>();
        map.put(1L, 45);
        map.put(2L, 76);
        map.put(3L, 100);
        ALog.w(map);
        assertLogEquals(Log.WARN, TAG, "java.util.LinkedHashMap(size = 3) [{1 -> 45}, {2 -> 76}, {3 -> 100}]");
    }

    @Test
    public void testW_ObjectCustomFormatter() {
        ALogger logger = ALog.formatter(String.class, ALogFormatter.create((ALogFormatterDelegate<String>) object ->
                "String: length = " + object.length() + ", \"" + object + "\""
        ));
        logger.w("Argument");
        assertLogEquals(Log.WARN, TAG, "String: length = 8, \"Argument\"");
    }

    @Test
    public void testW_FormattedMessageCustomFormatters() {
        Map<Class<?>, ALogFormatter<?>> formatterMap = new HashMap<>();
        formatterMap.put(String.class, ALogFormatter.create((ALogFormatterDelegate<String>) object ->
                "String: length = " + object.length() + ", \"" + object + "\""
        ));
        formatterMap.put(BigInteger.class, ALogFormatter.create((ALogFormatterDelegate<BigInteger>) object ->
                "BigInteger: " + object.longValue()
        ));
        ALogger logger = ALog.formatters(formatterMap);
        logger.w("Message (%s, %s)", "s1", BigInteger.valueOf(1000L));
        assertLogEquals(Log.WARN, TAG, "Message (String: length = 2, \"s1\", BigInteger: 1000)");
    }
    
    @Test
    public void testW_Message() {
        String message = "Message";
        ALog.w(message);
        assertLogEquals(Log.WARN, TAG, message);
    }

    @Test
    public void testW_FormattedMessage() {
        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.w(messageFormat, stringArg, intArg);
        assertLogEquals(Log.WARN, TAG, formattedMessage);
    }

    @Test
    public void testW_Throwable() {
        Throwable throwable = new RuntimeException();
        ALog.w(throwable);
        assertLogEquals(Log.WARN, TAG, Log.getStackTraceString(throwable));
    }

    @Test
    public void testW_FormattedMessageThrowable() {
        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        Throwable throwable = new RuntimeException();
        ALog.w(throwable, messageFormat, stringArg, intArg);
        assertLogEquals(Log.WARN, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testW_LongMessage() {
        String longMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet cursus sit amet dictum sit. Ante metus dictum at tempor commodo ullamcorper a lacus vestibulum. Non consectetur a erat nam at. Non tellus orci ac auctor augue mauris augue neque. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Scelerisque eu ultrices vitae auctor eu. Scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Quis enim lobortis scelerisque fermentum. Lectus nulla at volutpat diam ut venenatis tellus. Turpis egestas sed tempus urna et pharetra pharetra. Consequat semper viverra nam libero justo laoreet sit. Nunc scelerisque viverra mauris in aliquam sem fringilla. Elit sed vulputate mi sit amet mauris. Ut porttitor leo a diam sollicitudin tempor. Risus pretium quam vulputate dignissim suspendisse. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et. Ut porttitor leo a diam. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Massa sapien faucibus et molestie ac. Non consectetur a erat nam at lectus urna duis convallis. Posuere ac ut consequat semper viverra. Eu ultrices vitae auctor eu augue ut lectus. Netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non. Urna id volutpat lacus laoreet non curabitur. Sit amet consectetur adipiscing elit duis. Sapien nec sagittis aliquam malesuada bibendum. Et leo duis ut diam quam nulla. Pretium fusce id velit ut tortor pretium viverra suspendisse. Arcu dictum varius duis at consectetur. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Tincidunt id aliquet risus feugiat. Nisl rhoncus mattis rhoncus urna neque. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Aenean euismod elementum nisi quis. Blandit libero volutpat sed cras ornare. Lectus mauris ultrices eros in cursus. Nulla aliquet enim tortor at auctor. Nisi porta lorem mollis aliquam ut porttitor leo a diam. Integer quis auctor elit sed vulputate mi. Eu nisl nunc mi ipsum. Nisl condimentum id venenatis a. In fermentum posuere urna nec. Porttitor rhoncus dolor purus non enim praesent. Pellentesque adipiscing commodo elit at. Porta non pulvinar neque laoreet. Mi ipsum faucibus vitae aliquet. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Pellentesque adipiscing commodo elit at imperdiet dui. Sed augue lacus viverra vitae congue eu. Massa tincidunt dui ut ornare. Dui vivamus arcu felis bibendum ut tristique et egestas. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ut etiam sit amet nisl purus in mollis. Facilisi etiam dignissim diam quis. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Donec ultrices tincidunt arcu non sodales neque sodales. Hendrerit gravida rutrum quisque non tellus orci. Diam sit amet nisl suscipit adipiscing bibendum est. Risus pretium quam vulputate dignissim. Facilisi morbi tempus iaculis urna id volutpat lacus. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt. Sit amet nulla facilisi morbi tempus. Egestas quis ipsum suspendisse ultrices. Ornare suspendisse sed nisi lacus sed viverra. Dictum at tempor commodo ullamcorper a. Augue lacus viverra vitae congue eu consequat ac. Sed lectus vestibulum mattis ullamcorper. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Viverra aliquet eget sit amet tellus. Orci nulla pellentesque dignissim enim sit amet venenatis urna cursus. Volutpat lacus laoreet non curabitur gravida. Malesuada pellentesque elit eget gravida. Sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Diam sollicitudin tempor id eu nisl nunc. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Accumsan tortor posuere ac ut consequat. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Sed ullamcorper morbi tincidunt ornare. Risus viverra adipiscing at in tellus integer. Euismod lacinia at quis risus. Pellentesque dignissim enim sit amet venenatis. Sed risus ultricies tristique nulla aliquet. Fusce ut placerat orci nulla pellentesque dignissim enim sit. Eu tincidunt tortor aliquam nulla facilisi cras fermentum. Dignissim suspendisse in est ante in. Ullamcorper eget nulla facilisi etiam dignissim diam. Eu consequat ac felis donec et odio pellentesque. Consequat interdum varius sit amet mattis vulputate enim nulla. Sit amet nisl suscipit adipiscing bibendum est ultricies integer quis. Quisque sagittis purus sit amet volutpat. At in tellus integer feugiat scelerisque varius morbi enim nunc. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. Vulputate odio ut enim blandit volutpat. Et sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Auctor augue mauris augue neque gravida in fermentum. Etiam sit amet nisl purus in mollis nunc sed id. Iaculis nunc sed augue lacus viverra vitae congue. Donec ac odio tempor orci dapibus ultrices in iaculis nunc. Nisl condimentum id venenatis a. Id ornare arcu odio ut sem nulla pharetra diam sit. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Euismod in pellentesque massa placerat. Tincidunt vitae semper quis lectus. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. At elementum eu facilisis sed odio morbi quis commodo odio. Consequat id porta nibh venenatis. Et molestie ac feugiat sed lectus vestibulum. In iaculis nunc sed augue lacus viverra vitae congue. Mi sit amet mauris commodo quis. Sed tempus urna et pharetra. Vel turpis nunc eget lorem dolor sed viverra ipsum nunc. Nunc consequat interdum varius sit amet mattis vulputate enim. Tristique senectus et netus et malesuada fames. Aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod. Nulla posuere sollicitudin aliquam ultrices sagittis orci a scelerisque. Urna neque viverra justo nec ultrices dui. Sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum. At volutpat diam ut venenatis tellus in metus vulputate. Viverra mauris in aliquam sem fringilla ut morbi. Sed elementum tempus egestas sed sed risus pretium. Tempor orci eu lobortis elementum nibh tellus molestie. Vestibulum lorem sed risus ultricies. Nullam ac tortor vitae purus faucibus ornare suspendisse. Id velit ut tortor pretium viverra suspendisse potenti nullam ac. Risus sed vulputate odio ut. Turpis massa tincidunt dui ut ornare lectus sit amet est. Praesent semper feugiat nibh sed pulvinar proin gravida. Mi bibendum neque egestas congue quisque. Lacus viverra vitae congue eu consequat ac felis donec. Vitae auctor eu augue ut lectus arcu bibendum at. Commodo ullamcorper a lacus vestibulum sed arcu non. Facilisis sed odio morbi quis commodo odio aenean. Urna neque viverra justo nec ultrices dui sapien. Facilisi nullam vehicula ipsum a arcu cursus vitae. Arcu dictum varius duis at consectetur lorem donec.";
        String longMessageChunk1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet cursus sit amet dictum sit. Ante metus dictum at tempor commodo ullamcorper a lacus vestibulum. Non consectetur a erat nam at. Non tellus orci ac auctor augue mauris augue neque. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Scelerisque eu ultrices vitae auctor eu. Scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Quis enim lobortis scelerisque fermentum. Lectus nulla at volutpat diam ut venenatis tellus. Turpis egestas sed tempus urna et pharetra pharetra. Consequat semper viverra nam libero justo laoreet sit. Nunc scelerisque viverra mauris in aliquam sem fringilla. Elit sed vulputate mi sit amet mauris. Ut porttitor leo a diam sollicitudin tempor. Risus pretium quam vulputate dignissim suspendisse. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et. Ut porttitor leo a diam. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Massa sapien faucibus et molestie ac. Non consectetur a erat nam at lectus urna duis convallis. Posuere ac ut consequat semper viverra. Eu ultrices vitae auctor eu augue ut lectus. Netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non. Urna id volutpat lacus laoreet non curabitur. Sit amet consectetur adipiscing elit duis. Sapien nec sagittis aliquam malesuada bibendum. Et leo duis ut diam quam nulla. Pretium fusce id velit ut tortor pretium viverra suspendisse. Arcu dictum varius duis at consectetur. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Tincidunt id aliquet risus feugiat. Nisl rhoncus mattis rhoncus urna neque. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Aenean euismod elementum nisi quis. Blandit libero volutpat sed cras ornare. Lectus mauris ultrices eros in cursus. Nulla aliquet enim tortor at auctor. Nisi porta lorem mollis aliquam ut porttitor leo a diam. Integer quis auctor elit sed vulputate mi. Eu nisl nunc mi ipsum. Nisl condimentum id venenatis a. In fermentum posuere urna nec. Porttitor rhoncus dolor purus non enim praesent. Pellentesque adipiscing commodo elit at. Porta non pulvinar neque laoreet. Mi ipsum faucibus vitae aliquet. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Pellentesque adipiscing commodo elit at imperdiet dui. Sed augue lacus viverra vitae congue eu. Massa tincidunt dui ut ornare. Dui vivamus arcu felis bibendum ut tristique et egestas. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ut etiam sit amet nisl purus in mollis. Facilisi etiam dignissim diam quis. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Donec ultrices tincidunt arcu non sodales neque sodales. Hendrerit gravida rutrum quisque non tellus orci. Diam sit amet nisl suscipit adipiscing bibendum est. Risus pretium quam vulputate dignissim. Facilisi morbi tempus iaculis urna id volutpat lacus. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt. Sit amet nulla facilisi morbi tempus. Egestas quis ipsum suspendisse ultrices. Ornare suspendisse sed nisi lacus sed viverra. Dictum at tempor commodo ullamcorper a. Augue lacus viverra vitae congue eu consequat ac. Sed lectus vestibulum mattis ullamcorper. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Viverra aliquet eget sit amet tellus. Orci nulla pellentesque dignissim enim sit amet venenatis urna cursus. Volutpat lacus laoreet non curabitur gravida. Malesuada pellentesque elit eget gravida. Sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Diam sollic";
        String longMessageChunk2 = "itudin tempor id eu nisl nunc. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Accumsan tortor posuere ac ut consequat. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Sed ullamcorper morbi tincidunt ornare. Risus viverra adipiscing at in tellus integer. Euismod lacinia at quis risus. Pellentesque dignissim enim sit amet venenatis. Sed risus ultricies tristique nulla aliquet. Fusce ut placerat orci nulla pellentesque dignissim enim sit. Eu tincidunt tortor aliquam nulla facilisi cras fermentum. Dignissim suspendisse in est ante in. Ullamcorper eget nulla facilisi etiam dignissim diam. Eu consequat ac felis donec et odio pellentesque. Consequat interdum varius sit amet mattis vulputate enim nulla. Sit amet nisl suscipit adipiscing bibendum est ultricies integer quis. Quisque sagittis purus sit amet volutpat. At in tellus integer feugiat scelerisque varius morbi enim nunc. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. Vulputate odio ut enim blandit volutpat. Et sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Auctor augue mauris augue neque gravida in fermentum. Etiam sit amet nisl purus in mollis nunc sed id. Iaculis nunc sed augue lacus viverra vitae congue. Donec ac odio tempor orci dapibus ultrices in iaculis nunc. Nisl condimentum id venenatis a. Id ornare arcu odio ut sem nulla pharetra diam sit. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Euismod in pellentesque massa placerat. Tincidunt vitae semper quis lectus. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. At elementum eu facilisis sed odio morbi quis commodo odio. Consequat id porta nibh venenatis. Et molestie ac feugiat sed lectus vestibulum. In iaculis nunc sed augue lacus viverra vitae congue. Mi sit amet mauris commodo quis. Sed tempus urna et pharetra. Vel turpis nunc eget lorem dolor sed viverra ipsum nunc. Nunc consequat interdum varius sit amet mattis vulputate enim. Tristique senectus et netus et malesuada fames. Aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod. Nulla posuere sollicitudin aliquam ultrices sagittis orci a scelerisque. Urna neque viverra justo nec ultrices dui. Sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum. At volutpat diam ut venenatis tellus in metus vulputate. Viverra mauris in aliquam sem fringilla ut morbi. Sed elementum tempus egestas sed sed risus pretium. Tempor orci eu lobortis elementum nibh tellus molestie. Vestibulum lorem sed risus ultricies. Nullam ac tortor vitae purus faucibus ornare suspendisse. Id velit ut tortor pretium viverra suspendisse potenti nullam ac. Risus sed vulputate odio ut. Turpis massa tincidunt dui ut ornare lectus sit amet est. Praesent semper feugiat nibh sed pulvinar proin gravida. Mi bibendum neque egestas congue quisque. Lacus viverra vitae congue eu consequat ac felis donec. Vitae auctor eu augue ut lectus arcu bibendum at. Commodo ullamcorper a lacus vestibulum sed arcu non. Facilisis sed odio morbi quis commodo odio aenean. Urna neque viverra justo nec ultrices dui sapien. Facilisi nullam vehicula ipsum a arcu cursus vitae. Arcu dictum varius duis at consectetur lorem donec.";
        ALog.w(longMessage);
        assertLogEquals(Log.WARN, TAG, longMessageChunk1, longMessageChunk2);
    }

    @Test
    public void testE() {
        ALog.e();
        assertLogEquals(Log.ERROR, TAG, "");
    }

    @Test
    public void testE_ObjectNull() {
        ALog.e((Object) null);
        assertLogEquals(Log.ERROR, TAG, "null");
    }

    @Test
    public void testE_ObjectPrimitiveArray() {
        ALog.e(new int[] {1, 2, 3, 4});
        assertLogEquals(Log.ERROR, TAG, "Array(size = 4) [1, 2, 3, 4]");
    }

    @Test
    public void testE_ObjectPrimitiveWrapperArray() {
        ALog.e(new Float[] {1.0f, 2.0f, 3.5f});
        assertLogEquals(Log.ERROR, TAG, "Array(size = 3) [1.0, 2.0, 3.5]");
    }

    @Test
    public void testE_ObjectArray() {
        ALog.e(new String[] {"s1", "s2", "s3", "s4", "s5"});
        assertLogEquals(Log.ERROR, TAG, "Array(size = 5) [s1, s2, s3, s4, s5]");
    }

    @Test
    public void testE_ObjectTwoDimensionalArray() {
        ALog.e(new byte[][] {{1, 2, 3}, {4, 5, 6, 7}, {8, 9}});
        assertLogEquals(Log.ERROR, TAG, "Array(size = 3) [Array(size = 3) [1, 2, 3], Array(size = 4) [4, 5, 6, 7], Array(size = 2) [8, 9]]");
    }

    @Test
    public void testE_ObjectCollection() {
        List<Object> list = new ArrayList<>();
        list.add("Argument");
        list.add(5);
        list.add(1.0f);
        ALog.e(list);
        assertLogEquals(Log.ERROR, TAG, "java.util.ArrayList(size = 3) [Argument, 5, 1.0]");
    }

    @Test
    public void testE_ObjectMap() {
        Map<Long, Integer> map = new LinkedHashMap<>();
        map.put(1L, 45);
        map.put(2L, 76);
        map.put(3L, 100);
        ALog.e(map);
        assertLogEquals(Log.ERROR, TAG, "java.util.LinkedHashMap(size = 3) [{1 -> 45}, {2 -> 76}, {3 -> 100}]");
    }

    @Test
    public void testE_ObjectCustomFormatter() {
        ALogger logger = ALog.formatter(String.class, ALogFormatter.create((ALogFormatterDelegate<String>) object ->
                "String: length = " + object.length() + ", \"" + object + "\""
        ));
        logger.e("Argument");
        assertLogEquals(Log.ERROR, TAG, "String: length = 8, \"Argument\"");
    }

    @Test
    public void testE_FormattedMessageCustomFormatters() {
        Map<Class<?>, ALogFormatter<?>> formatterMap = new HashMap<>();
        formatterMap.put(String.class, ALogFormatter.create((ALogFormatterDelegate<String>) object ->
                "String: length = " + object.length() + ", \"" + object + "\""
        ));
        formatterMap.put(BigInteger.class, ALogFormatter.create((ALogFormatterDelegate<BigInteger>) object ->
                "BigInteger: " + object.longValue()
        ));
        ALogger logger = ALog.formatters(formatterMap);
        logger.e("Message (%s, %s)", "s1", BigInteger.valueOf(1000L));
        assertLogEquals(Log.ERROR, TAG, "Message (String: length = 2, \"s1\", BigInteger: 1000)");
    }
    
    @Test
    public void testE_Message() {
        String message = "Message";
        ALog.e(message);
        assertLogEquals(Log.ERROR, TAG, message);
    }

    @Test
    public void testE_FormattedMessage() {
        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.e(messageFormat, stringArg, intArg);
        assertLogEquals(Log.ERROR, TAG, formattedMessage);
    }

    @Test
    public void testE_Throwable() {
        Throwable throwable = new RuntimeException();
        ALog.e(throwable);
        assertLogEquals(Log.ERROR, TAG, Log.getStackTraceString(throwable));
    }

    @Test
    public void testE_FormattedMessageThrowable() {
        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        Throwable throwable = new RuntimeException();
        ALog.e(throwable, messageFormat, stringArg, intArg);
        assertLogEquals(Log.ERROR, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testE_LongMessage() {
        String longMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet cursus sit amet dictum sit. Ante metus dictum at tempor commodo ullamcorper a lacus vestibulum. Non consectetur a erat nam at. Non tellus orci ac auctor augue mauris augue neque. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Scelerisque eu ultrices vitae auctor eu. Scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Quis enim lobortis scelerisque fermentum. Lectus nulla at volutpat diam ut venenatis tellus. Turpis egestas sed tempus urna et pharetra pharetra. Consequat semper viverra nam libero justo laoreet sit. Nunc scelerisque viverra mauris in aliquam sem fringilla. Elit sed vulputate mi sit amet mauris. Ut porttitor leo a diam sollicitudin tempor. Risus pretium quam vulputate dignissim suspendisse. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et. Ut porttitor leo a diam. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Massa sapien faucibus et molestie ac. Non consectetur a erat nam at lectus urna duis convallis. Posuere ac ut consequat semper viverra. Eu ultrices vitae auctor eu augue ut lectus. Netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non. Urna id volutpat lacus laoreet non curabitur. Sit amet consectetur adipiscing elit duis. Sapien nec sagittis aliquam malesuada bibendum. Et leo duis ut diam quam nulla. Pretium fusce id velit ut tortor pretium viverra suspendisse. Arcu dictum varius duis at consectetur. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Tincidunt id aliquet risus feugiat. Nisl rhoncus mattis rhoncus urna neque. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Aenean euismod elementum nisi quis. Blandit libero volutpat sed cras ornare. Lectus mauris ultrices eros in cursus. Nulla aliquet enim tortor at auctor. Nisi porta lorem mollis aliquam ut porttitor leo a diam. Integer quis auctor elit sed vulputate mi. Eu nisl nunc mi ipsum. Nisl condimentum id venenatis a. In fermentum posuere urna nec. Porttitor rhoncus dolor purus non enim praesent. Pellentesque adipiscing commodo elit at. Porta non pulvinar neque laoreet. Mi ipsum faucibus vitae aliquet. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Pellentesque adipiscing commodo elit at imperdiet dui. Sed augue lacus viverra vitae congue eu. Massa tincidunt dui ut ornare. Dui vivamus arcu felis bibendum ut tristique et egestas. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ut etiam sit amet nisl purus in mollis. Facilisi etiam dignissim diam quis. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Donec ultrices tincidunt arcu non sodales neque sodales. Hendrerit gravida rutrum quisque non tellus orci. Diam sit amet nisl suscipit adipiscing bibendum est. Risus pretium quam vulputate dignissim. Facilisi morbi tempus iaculis urna id volutpat lacus. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt. Sit amet nulla facilisi morbi tempus. Egestas quis ipsum suspendisse ultrices. Ornare suspendisse sed nisi lacus sed viverra. Dictum at tempor commodo ullamcorper a. Augue lacus viverra vitae congue eu consequat ac. Sed lectus vestibulum mattis ullamcorper. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Viverra aliquet eget sit amet tellus. Orci nulla pellentesque dignissim enim sit amet venenatis urna cursus. Volutpat lacus laoreet non curabitur gravida. Malesuada pellentesque elit eget gravida. Sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Diam sollicitudin tempor id eu nisl nunc. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Accumsan tortor posuere ac ut consequat. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Sed ullamcorper morbi tincidunt ornare. Risus viverra adipiscing at in tellus integer. Euismod lacinia at quis risus. Pellentesque dignissim enim sit amet venenatis. Sed risus ultricies tristique nulla aliquet. Fusce ut placerat orci nulla pellentesque dignissim enim sit. Eu tincidunt tortor aliquam nulla facilisi cras fermentum. Dignissim suspendisse in est ante in. Ullamcorper eget nulla facilisi etiam dignissim diam. Eu consequat ac felis donec et odio pellentesque. Consequat interdum varius sit amet mattis vulputate enim nulla. Sit amet nisl suscipit adipiscing bibendum est ultricies integer quis. Quisque sagittis purus sit amet volutpat. At in tellus integer feugiat scelerisque varius morbi enim nunc. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. Vulputate odio ut enim blandit volutpat. Et sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Auctor augue mauris augue neque gravida in fermentum. Etiam sit amet nisl purus in mollis nunc sed id. Iaculis nunc sed augue lacus viverra vitae congue. Donec ac odio tempor orci dapibus ultrices in iaculis nunc. Nisl condimentum id venenatis a. Id ornare arcu odio ut sem nulla pharetra diam sit. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Euismod in pellentesque massa placerat. Tincidunt vitae semper quis lectus. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. At elementum eu facilisis sed odio morbi quis commodo odio. Consequat id porta nibh venenatis. Et molestie ac feugiat sed lectus vestibulum. In iaculis nunc sed augue lacus viverra vitae congue. Mi sit amet mauris commodo quis. Sed tempus urna et pharetra. Vel turpis nunc eget lorem dolor sed viverra ipsum nunc. Nunc consequat interdum varius sit amet mattis vulputate enim. Tristique senectus et netus et malesuada fames. Aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod. Nulla posuere sollicitudin aliquam ultrices sagittis orci a scelerisque. Urna neque viverra justo nec ultrices dui. Sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum. At volutpat diam ut venenatis tellus in metus vulputate. Viverra mauris in aliquam sem fringilla ut morbi. Sed elementum tempus egestas sed sed risus pretium. Tempor orci eu lobortis elementum nibh tellus molestie. Vestibulum lorem sed risus ultricies. Nullam ac tortor vitae purus faucibus ornare suspendisse. Id velit ut tortor pretium viverra suspendisse potenti nullam ac. Risus sed vulputate odio ut. Turpis massa tincidunt dui ut ornare lectus sit amet est. Praesent semper feugiat nibh sed pulvinar proin gravida. Mi bibendum neque egestas congue quisque. Lacus viverra vitae congue eu consequat ac felis donec. Vitae auctor eu augue ut lectus arcu bibendum at. Commodo ullamcorper a lacus vestibulum sed arcu non. Facilisis sed odio morbi quis commodo odio aenean. Urna neque viverra justo nec ultrices dui sapien. Facilisi nullam vehicula ipsum a arcu cursus vitae. Arcu dictum varius duis at consectetur lorem donec.";
        String longMessageChunk1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet cursus sit amet dictum sit. Ante metus dictum at tempor commodo ullamcorper a lacus vestibulum. Non consectetur a erat nam at. Non tellus orci ac auctor augue mauris augue neque. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Scelerisque eu ultrices vitae auctor eu. Scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Quis enim lobortis scelerisque fermentum. Lectus nulla at volutpat diam ut venenatis tellus. Turpis egestas sed tempus urna et pharetra pharetra. Consequat semper viverra nam libero justo laoreet sit. Nunc scelerisque viverra mauris in aliquam sem fringilla. Elit sed vulputate mi sit amet mauris. Ut porttitor leo a diam sollicitudin tempor. Risus pretium quam vulputate dignissim suspendisse. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et. Ut porttitor leo a diam. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Massa sapien faucibus et molestie ac. Non consectetur a erat nam at lectus urna duis convallis. Posuere ac ut consequat semper viverra. Eu ultrices vitae auctor eu augue ut lectus. Netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non. Urna id volutpat lacus laoreet non curabitur. Sit amet consectetur adipiscing elit duis. Sapien nec sagittis aliquam malesuada bibendum. Et leo duis ut diam quam nulla. Pretium fusce id velit ut tortor pretium viverra suspendisse. Arcu dictum varius duis at consectetur. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Tincidunt id aliquet risus feugiat. Nisl rhoncus mattis rhoncus urna neque. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Aenean euismod elementum nisi quis. Blandit libero volutpat sed cras ornare. Lectus mauris ultrices eros in cursus. Nulla aliquet enim tortor at auctor. Nisi porta lorem mollis aliquam ut porttitor leo a diam. Integer quis auctor elit sed vulputate mi. Eu nisl nunc mi ipsum. Nisl condimentum id venenatis a. In fermentum posuere urna nec. Porttitor rhoncus dolor purus non enim praesent. Pellentesque adipiscing commodo elit at. Porta non pulvinar neque laoreet. Mi ipsum faucibus vitae aliquet. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Pellentesque adipiscing commodo elit at imperdiet dui. Sed augue lacus viverra vitae congue eu. Massa tincidunt dui ut ornare. Dui vivamus arcu felis bibendum ut tristique et egestas. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ut etiam sit amet nisl purus in mollis. Facilisi etiam dignissim diam quis. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Donec ultrices tincidunt arcu non sodales neque sodales. Hendrerit gravida rutrum quisque non tellus orci. Diam sit amet nisl suscipit adipiscing bibendum est. Risus pretium quam vulputate dignissim. Facilisi morbi tempus iaculis urna id volutpat lacus. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt. Sit amet nulla facilisi morbi tempus. Egestas quis ipsum suspendisse ultrices. Ornare suspendisse sed nisi lacus sed viverra. Dictum at tempor commodo ullamcorper a. Augue lacus viverra vitae congue eu consequat ac. Sed lectus vestibulum mattis ullamcorper. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Viverra aliquet eget sit amet tellus. Orci nulla pellentesque dignissim enim sit amet venenatis urna cursus. Volutpat lacus laoreet non curabitur gravida. Malesuada pellentesque elit eget gravida. Sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Diam sollic";
        String longMessageChunk2 = "itudin tempor id eu nisl nunc. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Accumsan tortor posuere ac ut consequat. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Sed ullamcorper morbi tincidunt ornare. Risus viverra adipiscing at in tellus integer. Euismod lacinia at quis risus. Pellentesque dignissim enim sit amet venenatis. Sed risus ultricies tristique nulla aliquet. Fusce ut placerat orci nulla pellentesque dignissim enim sit. Eu tincidunt tortor aliquam nulla facilisi cras fermentum. Dignissim suspendisse in est ante in. Ullamcorper eget nulla facilisi etiam dignissim diam. Eu consequat ac felis donec et odio pellentesque. Consequat interdum varius sit amet mattis vulputate enim nulla. Sit amet nisl suscipit adipiscing bibendum est ultricies integer quis. Quisque sagittis purus sit amet volutpat. At in tellus integer feugiat scelerisque varius morbi enim nunc. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. Vulputate odio ut enim blandit volutpat. Et sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Auctor augue mauris augue neque gravida in fermentum. Etiam sit amet nisl purus in mollis nunc sed id. Iaculis nunc sed augue lacus viverra vitae congue. Donec ac odio tempor orci dapibus ultrices in iaculis nunc. Nisl condimentum id venenatis a. Id ornare arcu odio ut sem nulla pharetra diam sit. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Euismod in pellentesque massa placerat. Tincidunt vitae semper quis lectus. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. At elementum eu facilisis sed odio morbi quis commodo odio. Consequat id porta nibh venenatis. Et molestie ac feugiat sed lectus vestibulum. In iaculis nunc sed augue lacus viverra vitae congue. Mi sit amet mauris commodo quis. Sed tempus urna et pharetra. Vel turpis nunc eget lorem dolor sed viverra ipsum nunc. Nunc consequat interdum varius sit amet mattis vulputate enim. Tristique senectus et netus et malesuada fames. Aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod. Nulla posuere sollicitudin aliquam ultrices sagittis orci a scelerisque. Urna neque viverra justo nec ultrices dui. Sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum. At volutpat diam ut venenatis tellus in metus vulputate. Viverra mauris in aliquam sem fringilla ut morbi. Sed elementum tempus egestas sed sed risus pretium. Tempor orci eu lobortis elementum nibh tellus molestie. Vestibulum lorem sed risus ultricies. Nullam ac tortor vitae purus faucibus ornare suspendisse. Id velit ut tortor pretium viverra suspendisse potenti nullam ac. Risus sed vulputate odio ut. Turpis massa tincidunt dui ut ornare lectus sit amet est. Praesent semper feugiat nibh sed pulvinar proin gravida. Mi bibendum neque egestas congue quisque. Lacus viverra vitae congue eu consequat ac felis donec. Vitae auctor eu augue ut lectus arcu bibendum at. Commodo ullamcorper a lacus vestibulum sed arcu non. Facilisis sed odio morbi quis commodo odio aenean. Urna neque viverra justo nec ultrices dui sapien. Facilisi nullam vehicula ipsum a arcu cursus vitae. Arcu dictum varius duis at consectetur lorem donec.";
        ALog.e(longMessage);
        assertLogEquals(Log.ERROR, TAG, longMessageChunk1, longMessageChunk2);
    }

    @Test
    public void testWtf() {
        ALog.wtf();
        assertLogEquals(Log.ASSERT, TAG, "");
    }

    @Test
    public void testWtf_ObjectNull() {
        ALog.wtf((Object) null);
        assertLogEquals(Log.ASSERT, TAG, "null");
    }

    @Test
    public void testWtf_ObjectPrimitiveArray() {
        ALog.wtf(new int[] {1, 2, 3, 4});
        assertLogEquals(Log.ASSERT, TAG, "Array(size = 4) [1, 2, 3, 4]");
    }

    @Test
    public void testWtf_ObjectPrimitiveWrapperArray() {
        ALog.wtf(new Float[] {1.0f, 2.0f, 3.5f});
        assertLogEquals(Log.ASSERT, TAG, "Array(size = 3) [1.0, 2.0, 3.5]");
    }

    @Test
    public void testWtf_ObjectArray() {
        ALog.wtf(new String[] {"s1", "s2", "s3", "s4", "s5"});
        assertLogEquals(Log.ASSERT, TAG, "Array(size = 5) [s1, s2, s3, s4, s5]");
    }

    @Test
    public void testWtf_ObjectTwoDimensionalArray() {
        ALog.wtf(new byte[][] {{1, 2, 3}, {4, 5, 6, 7}, {8, 9}});
        assertLogEquals(Log.ASSERT, TAG, "Array(size = 3) [Array(size = 3) [1, 2, 3], Array(size = 4) [4, 5, 6, 7], Array(size = 2) [8, 9]]");
    }

    @Test
    public void testWtf_ObjectCollection() {
        List<Object> list = new ArrayList<>();
        list.add("Argument");
        list.add(5);
        list.add(1.0f);
        ALog.wtf(list);
        assertLogEquals(Log.ASSERT, TAG, "java.util.ArrayList(size = 3) [Argument, 5, 1.0]");
    }

    @Test
    public void testWtf_ObjectMap() {
        Map<Long, Integer> map = new LinkedHashMap<>();
        map.put(1L, 45);
        map.put(2L, 76);
        map.put(3L, 100);
        ALog.wtf(map);
        assertLogEquals(Log.ASSERT, TAG, "java.util.LinkedHashMap(size = 3) [{1 -> 45}, {2 -> 76}, {3 -> 100}]");
    }

    @Test
    public void testWtf_ObjectCustomFormatter() {
        ALogger logger = ALog.formatter(String.class, ALogFormatter.create((ALogFormatterDelegate<String>) object ->
                "String: length = " + object.length() + ", \"" + object + "\""
        ));
        logger.wtf("Argument");
        assertLogEquals(Log.ASSERT, TAG, "String: length = 8, \"Argument\"");
    }

    @Test
    public void testWtf_FormattedMessageCustomFormatters() {
        Map<Class<?>, ALogFormatter<?>> formatterMap = new HashMap<>();
        formatterMap.put(String.class, ALogFormatter.create((ALogFormatterDelegate<String>) object ->
                "String: length = " + object.length() + ", \"" + object + "\""
        ));
        formatterMap.put(BigInteger.class, ALogFormatter.create((ALogFormatterDelegate<BigInteger>) object ->
                "BigInteger: " + object.longValue()
        ));
        ALogger logger = ALog.formatters(formatterMap);
        logger.wtf("Message (%s, %s)", "s1", BigInteger.valueOf(1000L));
        assertLogEquals(Log.ASSERT, TAG, "Message (String: length = 2, \"s1\", BigInteger: 1000)");
    }
    
    @Test
    public void testWtf_Message() {
        String message = "Message";
        ALog.wtf(message);
        assertLogEquals(Log.ASSERT, TAG, message);
    }

    @Test
    public void testWtf_FormattedMessage() {
        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.wtf(messageFormat, stringArg, intArg);
        assertLogEquals(Log.ASSERT, TAG, formattedMessage);
    }

    @Test
    public void testWtf_Throwable() {
        Throwable throwable = new RuntimeException();
        ALog.wtf(throwable);
        assertLogEquals(Log.ASSERT, TAG, Log.getStackTraceString(throwable));
    }

    @Test
    public void testWtf_FormattedMessageThrowable() {
        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        Throwable throwable = new RuntimeException();
        ALog.wtf(throwable, messageFormat, stringArg, intArg);
        assertLogEquals(Log.ASSERT, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testWtf_LongMessage() {
        String longMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet cursus sit amet dictum sit. Ante metus dictum at tempor commodo ullamcorper a lacus vestibulum. Non consectetur a erat nam at. Non tellus orci ac auctor augue mauris augue neque. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Scelerisque eu ultrices vitae auctor eu. Scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Quis enim lobortis scelerisque fermentum. Lectus nulla at volutpat diam ut venenatis tellus. Turpis egestas sed tempus urna et pharetra pharetra. Consequat semper viverra nam libero justo laoreet sit. Nunc scelerisque viverra mauris in aliquam sem fringilla. Elit sed vulputate mi sit amet mauris. Ut porttitor leo a diam sollicitudin tempor. Risus pretium quam vulputate dignissim suspendisse. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et. Ut porttitor leo a diam. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Massa sapien faucibus et molestie ac. Non consectetur a erat nam at lectus urna duis convallis. Posuere ac ut consequat semper viverra. Eu ultrices vitae auctor eu augue ut lectus. Netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non. Urna id volutpat lacus laoreet non curabitur. Sit amet consectetur adipiscing elit duis. Sapien nec sagittis aliquam malesuada bibendum. Et leo duis ut diam quam nulla. Pretium fusce id velit ut tortor pretium viverra suspendisse. Arcu dictum varius duis at consectetur. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Tincidunt id aliquet risus feugiat. Nisl rhoncus mattis rhoncus urna neque. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Aenean euismod elementum nisi quis. Blandit libero volutpat sed cras ornare. Lectus mauris ultrices eros in cursus. Nulla aliquet enim tortor at auctor. Nisi porta lorem mollis aliquam ut porttitor leo a diam. Integer quis auctor elit sed vulputate mi. Eu nisl nunc mi ipsum. Nisl condimentum id venenatis a. In fermentum posuere urna nec. Porttitor rhoncus dolor purus non enim praesent. Pellentesque adipiscing commodo elit at. Porta non pulvinar neque laoreet. Mi ipsum faucibus vitae aliquet. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Pellentesque adipiscing commodo elit at imperdiet dui. Sed augue lacus viverra vitae congue eu. Massa tincidunt dui ut ornare. Dui vivamus arcu felis bibendum ut tristique et egestas. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ut etiam sit amet nisl purus in mollis. Facilisi etiam dignissim diam quis. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Donec ultrices tincidunt arcu non sodales neque sodales. Hendrerit gravida rutrum quisque non tellus orci. Diam sit amet nisl suscipit adipiscing bibendum est. Risus pretium quam vulputate dignissim. Facilisi morbi tempus iaculis urna id volutpat lacus. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt. Sit amet nulla facilisi morbi tempus. Egestas quis ipsum suspendisse ultrices. Ornare suspendisse sed nisi lacus sed viverra. Dictum at tempor commodo ullamcorper a. Augue lacus viverra vitae congue eu consequat ac. Sed lectus vestibulum mattis ullamcorper. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Viverra aliquet eget sit amet tellus. Orci nulla pellentesque dignissim enim sit amet venenatis urna cursus. Volutpat lacus laoreet non curabitur gravida. Malesuada pellentesque elit eget gravida. Sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Diam sollicitudin tempor id eu nisl nunc. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Accumsan tortor posuere ac ut consequat. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Sed ullamcorper morbi tincidunt ornare. Risus viverra adipiscing at in tellus integer. Euismod lacinia at quis risus. Pellentesque dignissim enim sit amet venenatis. Sed risus ultricies tristique nulla aliquet. Fusce ut placerat orci nulla pellentesque dignissim enim sit. Eu tincidunt tortor aliquam nulla facilisi cras fermentum. Dignissim suspendisse in est ante in. Ullamcorper eget nulla facilisi etiam dignissim diam. Eu consequat ac felis donec et odio pellentesque. Consequat interdum varius sit amet mattis vulputate enim nulla. Sit amet nisl suscipit adipiscing bibendum est ultricies integer quis. Quisque sagittis purus sit amet volutpat. At in tellus integer feugiat scelerisque varius morbi enim nunc. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. Vulputate odio ut enim blandit volutpat. Et sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Auctor augue mauris augue neque gravida in fermentum. Etiam sit amet nisl purus in mollis nunc sed id. Iaculis nunc sed augue lacus viverra vitae congue. Donec ac odio tempor orci dapibus ultrices in iaculis nunc. Nisl condimentum id venenatis a. Id ornare arcu odio ut sem nulla pharetra diam sit. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Euismod in pellentesque massa placerat. Tincidunt vitae semper quis lectus. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. At elementum eu facilisis sed odio morbi quis commodo odio. Consequat id porta nibh venenatis. Et molestie ac feugiat sed lectus vestibulum. In iaculis nunc sed augue lacus viverra vitae congue. Mi sit amet mauris commodo quis. Sed tempus urna et pharetra. Vel turpis nunc eget lorem dolor sed viverra ipsum nunc. Nunc consequat interdum varius sit amet mattis vulputate enim. Tristique senectus et netus et malesuada fames. Aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod. Nulla posuere sollicitudin aliquam ultrices sagittis orci a scelerisque. Urna neque viverra justo nec ultrices dui. Sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum. At volutpat diam ut venenatis tellus in metus vulputate. Viverra mauris in aliquam sem fringilla ut morbi. Sed elementum tempus egestas sed sed risus pretium. Tempor orci eu lobortis elementum nibh tellus molestie. Vestibulum lorem sed risus ultricies. Nullam ac tortor vitae purus faucibus ornare suspendisse. Id velit ut tortor pretium viverra suspendisse potenti nullam ac. Risus sed vulputate odio ut. Turpis massa tincidunt dui ut ornare lectus sit amet est. Praesent semper feugiat nibh sed pulvinar proin gravida. Mi bibendum neque egestas congue quisque. Lacus viverra vitae congue eu consequat ac felis donec. Vitae auctor eu augue ut lectus arcu bibendum at. Commodo ullamcorper a lacus vestibulum sed arcu non. Facilisis sed odio morbi quis commodo odio aenean. Urna neque viverra justo nec ultrices dui sapien. Facilisi nullam vehicula ipsum a arcu cursus vitae. Arcu dictum varius duis at consectetur lorem donec.";
        String longMessageChunk1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet cursus sit amet dictum sit. Ante metus dictum at tempor commodo ullamcorper a lacus vestibulum. Non consectetur a erat nam at. Non tellus orci ac auctor augue mauris augue neque. Sed egestas egestas fringilla phasellus faucibus scelerisque eleifend. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Scelerisque eu ultrices vitae auctor eu. Scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Quis enim lobortis scelerisque fermentum. Lectus nulla at volutpat diam ut venenatis tellus. Turpis egestas sed tempus urna et pharetra pharetra. Consequat semper viverra nam libero justo laoreet sit. Nunc scelerisque viverra mauris in aliquam sem fringilla. Elit sed vulputate mi sit amet mauris. Ut porttitor leo a diam sollicitudin tempor. Risus pretium quam vulputate dignissim suspendisse. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et. Ut porttitor leo a diam. Velit ut tortor pretium viverra suspendisse potenti nullam ac. Massa sapien faucibus et molestie ac. Non consectetur a erat nam at lectus urna duis convallis. Posuere ac ut consequat semper viverra. Eu ultrices vitae auctor eu augue ut lectus. Netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non. Urna id volutpat lacus laoreet non curabitur. Sit amet consectetur adipiscing elit duis. Sapien nec sagittis aliquam malesuada bibendum. Et leo duis ut diam quam nulla. Pretium fusce id velit ut tortor pretium viverra suspendisse. Arcu dictum varius duis at consectetur. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Tincidunt id aliquet risus feugiat. Nisl rhoncus mattis rhoncus urna neque. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Aenean euismod elementum nisi quis. Blandit libero volutpat sed cras ornare. Lectus mauris ultrices eros in cursus. Nulla aliquet enim tortor at auctor. Nisi porta lorem mollis aliquam ut porttitor leo a diam. Integer quis auctor elit sed vulputate mi. Eu nisl nunc mi ipsum. Nisl condimentum id venenatis a. In fermentum posuere urna nec. Porttitor rhoncus dolor purus non enim praesent. Pellentesque adipiscing commodo elit at. Porta non pulvinar neque laoreet. Mi ipsum faucibus vitae aliquet. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Pellentesque adipiscing commodo elit at imperdiet dui. Sed augue lacus viverra vitae congue eu. Massa tincidunt dui ut ornare. Dui vivamus arcu felis bibendum ut tristique et egestas. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ut etiam sit amet nisl purus in mollis. Facilisi etiam dignissim diam quis. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Donec ultrices tincidunt arcu non sodales neque sodales. Hendrerit gravida rutrum quisque non tellus orci. Diam sit amet nisl suscipit adipiscing bibendum est. Risus pretium quam vulputate dignissim. Facilisi morbi tempus iaculis urna id volutpat lacus. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt. Sit amet nulla facilisi morbi tempus. Egestas quis ipsum suspendisse ultrices. Ornare suspendisse sed nisi lacus sed viverra. Dictum at tempor commodo ullamcorper a. Augue lacus viverra vitae congue eu consequat ac. Sed lectus vestibulum mattis ullamcorper. Morbi tempus iaculis urna id volutpat lacus laoreet non curabitur. Viverra aliquet eget sit amet tellus. Orci nulla pellentesque dignissim enim sit amet venenatis urna cursus. Volutpat lacus laoreet non curabitur gravida. Malesuada pellentesque elit eget gravida. Sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus. Purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae. Diam sollic";
        String longMessageChunk2 = "itudin tempor id eu nisl nunc. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Accumsan tortor posuere ac ut consequat. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Sed ullamcorper morbi tincidunt ornare. Risus viverra adipiscing at in tellus integer. Euismod lacinia at quis risus. Pellentesque dignissim enim sit amet venenatis. Sed risus ultricies tristique nulla aliquet. Fusce ut placerat orci nulla pellentesque dignissim enim sit. Eu tincidunt tortor aliquam nulla facilisi cras fermentum. Dignissim suspendisse in est ante in. Ullamcorper eget nulla facilisi etiam dignissim diam. Eu consequat ac felis donec et odio pellentesque. Consequat interdum varius sit amet mattis vulputate enim nulla. Sit amet nisl suscipit adipiscing bibendum est ultricies integer quis. Quisque sagittis purus sit amet volutpat. At in tellus integer feugiat scelerisque varius morbi enim nunc. Volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim. Vulputate odio ut enim blandit volutpat. Et sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Auctor augue mauris augue neque gravida in fermentum. Etiam sit amet nisl purus in mollis nunc sed id. Iaculis nunc sed augue lacus viverra vitae congue. Donec ac odio tempor orci dapibus ultrices in iaculis nunc. Nisl condimentum id venenatis a. Id ornare arcu odio ut sem nulla pharetra diam sit. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Euismod in pellentesque massa placerat. Tincidunt vitae semper quis lectus. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. At elementum eu facilisis sed odio morbi quis commodo odio. Consequat id porta nibh venenatis. Et molestie ac feugiat sed lectus vestibulum. In iaculis nunc sed augue lacus viverra vitae congue. Mi sit amet mauris commodo quis. Sed tempus urna et pharetra. Vel turpis nunc eget lorem dolor sed viverra ipsum nunc. Nunc consequat interdum varius sit amet mattis vulputate enim. Tristique senectus et netus et malesuada fames. Aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod. Nulla posuere sollicitudin aliquam ultrices sagittis orci a scelerisque. Urna neque viverra justo nec ultrices dui. Sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum. At volutpat diam ut venenatis tellus in metus vulputate. Viverra mauris in aliquam sem fringilla ut morbi. Sed elementum tempus egestas sed sed risus pretium. Tempor orci eu lobortis elementum nibh tellus molestie. Vestibulum lorem sed risus ultricies. Nullam ac tortor vitae purus faucibus ornare suspendisse. Id velit ut tortor pretium viverra suspendisse potenti nullam ac. Risus sed vulputate odio ut. Turpis massa tincidunt dui ut ornare lectus sit amet est. Praesent semper feugiat nibh sed pulvinar proin gravida. Mi bibendum neque egestas congue quisque. Lacus viverra vitae congue eu consequat ac felis donec. Vitae auctor eu augue ut lectus arcu bibendum at. Commodo ullamcorper a lacus vestibulum sed arcu non. Facilisis sed odio morbi quis commodo odio aenean. Urna neque viverra justo nec ultrices dui sapien. Facilisi nullam vehicula ipsum a arcu cursus vitae. Arcu dictum varius duis at consectetur lorem donec.";
        ALog.wtf(longMessage);
        assertLogEquals(Log.ASSERT, TAG, longMessageChunk1, longMessageChunk2);
    }

    @Test
    public void testT_CustomTag() {
        String customTag = "Custom";
        String message = "Message";
        ALogger customTagLogger = ALog.t(customTag);
        customTagLogger.v(message);
        assertLogEquals(null, customTag);
        customTagLogger.d(message);
        assertLogEquals(null, customTag);
        customTagLogger.i(message);
        assertLogEquals(null, customTag);
        customTagLogger.w(message);
        assertLogEquals(null, customTag);
        customTagLogger.e(message);
        assertLogEquals(null, customTag);
        customTagLogger.wtf(message);
        assertLogEquals(null, customTag);
    }

    @Test
    public void testT_AutoTag() {
        String message = "Message";
        ALogger autoTagLogger = ALog.t(null);
        String autoTag = Utils.class.getSimpleName();
        v(autoTagLogger, null, message);
        assertLogEquals(null, autoTag);
        d(autoTagLogger, null, message);
        assertLogEquals(null, autoTag);
        i(autoTagLogger, null, message);
        assertLogEquals(null, autoTag);
        w(autoTagLogger, null, message);
        assertLogEquals(null, autoTag);
        e(autoTagLogger, null, message);
        assertLogEquals(null, autoTag);
        wtf(autoTagLogger, null, message);
        assertLogEquals(null, autoTag);
    }

    @Test
    public void testJson_Null() {
        ALog.json(null);
        assertLogEquals(null, null, "Passed JSON string is null");
    }

    @Test
    public void testJson_Empty() {
        ALog.json("");
        assertLogEquals(null, null, "Passed JSON string is empty");
    }

    @Test
    public void testJson_Invalid() {
        String invalidJson = "{\"id\":1234, \"name\":\"John Doe\", \"arr\":[\"a\", \"b\"}]}";
        ALog.json(invalidJson);
        assertLogEquals(null, null, "Invalid JSON string: Unterminated array at character 47 of " + invalidJson);
    }

    @Test
    public void testJson_Valid() {
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
    public void testXml_Null() {
        ALog.xml(null);
        assertLogEquals(null, null, "Passed XML string is null");
    }

    @Test
    public void testXml_Empty() {
        ALog.xml("");
        assertLogEquals(null, null, "Passed XML string is empty");
    }

    @Test
    public void testXml_InvalidCloseTag() {
        String invalidXml = "<note><to>Tove</to><from>Jani</Ffrom><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";
        ALog.xml(invalidXml);
        assertLogStartsWith(null, null, "Invalid XML string: ");
    }

    @Test
    public void testXml_InvalidNonClosedTag() {
        String invalidXml = "<root><item/><item2><root/>";
        ALog.xml(invalidXml);
        assertLogStartsWith(null, null, "Invalid XML string: ");
    }

    @Test
    public void testXml_ValidApos() {
        String validXml = "<note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";
        String formattedValidXml = "XML:\n" +
                "<note>\n" +
                "  <to>Tove</to>\n" +
                "  <from>Jani</from>\n" +
                "  <heading>Reminder</heading>\n" +
                "  <body>Don&apos;t forget me this weekend!</body>\n" +
                "</note>\n";
        ALog.xml(validXml);
        assertLogEquals(null, null, formattedValidXml);
        ALog.xml(ALogLevel.ERROR, validXml);
        assertLogEquals(Log.ERROR, null, formattedValidXml);
    }

    @Test
    public void testXml_ValidQuotAttribute() {
        String validXml = "<student nickname='Rob \"The Dog\"'><name>Robert</name><grade>A+</grade></student>";
        String formattedValidXml = "XML:\n" +
                "<student nickname=\"Rob &quot;The Dog&quot;\">\n" +
                "  <name>Robert</name>\n" +
                "  <grade>A+</grade>\n" +
                "</student>\n";
        ALog.xml(validXml);
        assertLogEquals(null, null, formattedValidXml);
        ALog.xml(ALogLevel.DEBUG, validXml);
        assertLogEquals(Log.DEBUG, null, formattedValidXml);
    }

    @Test
    public void testXml_ValidAttributes() {
        String validXml = "<note day=\"12\" month=\"11\" year=\"99\" to=\"Tove\" from=\"Jani\" heading=\"Reminder\" body=\"Don't forget me this weekend!\"></note>";
        String formattedValidXml = "XML:\n" +
                "<note day=\"12\"\n" +
                "      month=\"11\"\n" +
                "      year=\"99\"\n" +
                "      to=\"Tove\"\n" +
                "      from=\"Jani\"\n" +
                "      heading=\"Reminder\"\n" +
                "      body=\"Don&apos;t forget me this weekend!\"/>\n";
        ALog.xml(validXml);
        assertLogEquals(null, null, formattedValidXml);
        ALog.xml(ALogLevel.VERBOSE, validXml);
        assertLogEquals(Log.VERBOSE, null, formattedValidXml);
    }

    @Test
    public void testXml_ValidLarge() {
        String validXml = "<catalog> <book id=\"bk101\"> <author>Gambardella, Matthew</author> <title>XML Developer's Guide</title> <genre>Computer</genre> <price>44.95</price> <publish_date>2000-10-01</publish_date> <description>An in-depth look at creating applications with XML.</description> </book> <book id=\"bk102\"> <author>Ralls, Kim</author> <title>Midnight Rain</title> <genre>Fantasy</genre> <price>5.95</price> <publish_date>2000-12-16</publish_date> <description>A former architect battles corporate zombies, an evil sorceress, and her own childhood to become queen of the world.</description> </book> <book id=\"bk103\"> <author>Corets, Eva</author> <title>Maeve Ascendant</title> <genre>Fantasy</genre> <price>5.95</price> <publish_date>2000-11-17</publish_date> <description>After the collapse of a nanotechnology society in England, the young survivors lay the foundation for a new society.</description> </book> </catalog>";
        String formattedValidXml = "XML:\n" +
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
        ALog.xml(validXml);
        assertLogEquals(null, null, formattedValidXml);
        ALog.xml(ALogLevel.WTF, validXml);
        assertLogEquals(Log.ASSERT, null, formattedValidXml);
    }

    @Test
    public void testXml_ValidNamespacesCData() {
        String validXml = "<res:resource xmlns:res=\"http://www.example.com/ns/server/resource\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.com/ns/server/resource resource.xsd\" version=\"1\">  <res:message httpCode=\"200\" type=\"ok\">   <![CDATA[Sample Success Response]]>    </res:message>    <dif:person xmlns:dif=\"http://www.example.com/ns/server/resource\"                xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance-1\" xsi:schemaLocation=\"http://www.example.com/ns/server/person person.xsd\" version=\"1\"> <dif:name>test name</dif:name><dif:description lang=\"en\">test description</dif:description>    </dif:person ></res:resource>";
        String formattedValidXml = "XML:\n" +
                "<res:resource xmlns:res=\"http://www.example.com/ns/server/resource\"\n" +
                "              xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "              xsi:schemaLocation=\"http://www.example.com/ns/server/resource resource.xsd\"\n" +
                "              version=\"1\">\n" +
                "  <res:message httpCode=\"200\"\n" +
                "               type=\"ok\"><![CDATA[Sample Success Response]]></res:message>\n" +
                "  <dif:person xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance-1\"\n" +
                "              xmlns:dif=\"http://www.example.com/ns/server/resource\"\n" +
                "              xsi:schemaLocation=\"http://www.example.com/ns/server/person person.xsd\"\n" +
                "              version=\"1\">\n" +
                "    <dif:name>test name</dif:name>\n" +
                "    <dif:description lang=\"en\">test description</dif:description>\n" +
                "  </dif:person>\n" +
                "</res:resource>\n";
        ALog.xml(validXml);
        assertLogEquals(null, null, formattedValidXml);
        ALog.xml(ALogLevel.INFO, validXml);
        assertLogEquals(Log.INFO, null, formattedValidXml);
    }

    @Test
    public void testXml_ValidComment() {
        String validXml = "<example>\n<!-- This is a comment -->\n</example>";
        String formattedValidXml = "XML:\n" +
                "<example><!-- This is a comment --></example>\n";
        ALog.xml(validXml);
        assertLogEquals(null, null, formattedValidXml);
        ALog.xml(ALogLevel.DEBUG, validXml);
        assertLogEquals(Log.DEBUG, null, formattedValidXml);
    }

    @Test
    public void testHex_Null() {
        ALog.hex(null);
        assertLogEquals(null, null, "Passed byte array is null");
        ALog.hex(ALogLevel.WTF, null);
        assertLogEquals(Log.ASSERT, null, "Passed byte array is null");
    }

    @Test
    public void testHex_Empty() {
        byte[] emptyBytes = new byte[0];
        ALog.hex(emptyBytes);
        assertLogEquals(null, null, "Passed byte array is empty");
        ALog.hex(ALogLevel.VERBOSE, emptyBytes);
        assertLogEquals(Log.VERBOSE, null, "Passed byte array is empty");
    }

    @Test
    public void testHex_SingleByte() {
        byte[] singleByte = {45};
        ALog.hex(singleByte);
        assertLogEquals(null, null, "Hex:\n2D");
        ALog.hex(ALogLevel.WARNING, singleByte);
        assertLogEquals(Log.WARN, null, "Hex:\n2D");
    }

    @Test
    public void testHex_TenBytes() {
        byte[] tenBytes = {111, 32, 123, -128, 0, -100, 98, 127, 32, 10};
        ALog.hex(tenBytes);
        assertLogEquals(null, null, "Hex:\n6F 20 7B 80 00 9C 62 7F 20 0A");
        ALog.hex(ALogLevel.DEBUG, tenBytes);
        assertLogEquals(Log.DEBUG, null, "Hex:\n6F 20 7B 80 00 9C 62 7F 20 0A");
    }
}
