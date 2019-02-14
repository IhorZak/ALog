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

package ua.pp.ihorzak.alog.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ua.pp.ihorzak.alog.ALog;
import ua.pp.ihorzak.alog.ALogLevel;

/**
 * Main activity of ALog library sample application.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
public class MainActivity extends AppCompatActivity {
    private static final ListItem[] LIST_ITEMS = {
            new ListItem("ALog.v()", ALog::v),
            new ListItem("ALog.d()", ALog::d),
            new ListItem("ALog.i()", ALog::i),
            new ListItem("ALog.w()", ALog::w),
            new ListItem("ALog.e()", ALog::e),
            new ListItem("ALog.wtf()", ALog::wtf),
            new ListItem("ALog.v(\"Message, %d, %s\", 10, \"Argument\")", () -> ALog.v("Message, %d, %s", 20, "Argument")),
            new ListItem("ALog.d(\"Message, %d, %s\", 10, \"Argument\")", () -> ALog.d("Message, %d, %s", 20, "Argument")),
            new ListItem("ALog.i(\"Message, %d, %s\", 10, \"Argument\")", () -> ALog.i("Message, %d, %s", 20, "Argument")),
            new ListItem("ALog.w(\"Message, %d, %s\", 10, \"Argument\")", () -> ALog.w("Message, %d, %s", 20, "Argument")),
            new ListItem("ALog.e(\"Message, %d, %s\", 10, \"Argument\")", () -> ALog.e("Message, %d, %s", 20, "Argument")),
            new ListItem("ALog.wtf(\"Message, %d, %s\", 10, \"Argument\")", () -> ALog.wtf("Message, %d, %s", 20, "Argument")),
            new ListItem("ALog.t(\"Tag\").d(\"Message, %d, %s\", 10, \"Argument\")", () -> ALog.t("Tag").d("Message, %d, %s", 20, "Argument")),
            new ListItem("ALog.t(null).d(\"Message, %d, %s\", 10, \"Argument\")", () -> ALog.t(null).d("Message, %d, %s", 20, "Argument")),
            new ListItem("ALog.st(3).d(\"Message, %d, %s\", 10, \"Argument\")", () -> ALog.st(3).d("Message, %d, %s", 20, "Argument")),
            new ListItem("ALog.tst(\"Tag\", 5).d(\"Message, %d, %s\", 10, \"Argument\")", () -> ALog.tst("Tag", 5).d("Message, %d, %s", 20, "Argument")),
            new ListItem("ALog.json(\"{\"id\":456,\"data\":[\"a\",\"b\",\"c\"]}\")", () -> ALog.json("{\"id\":456,\"data\":[\"a\",\"b\",\"c\"]}")),
            new ListItem("ALog.json(ALogLevel.ERROR, \"{\"id\":456,\"data\":[\"a\",\"b\",\"c\"]}\")", () -> ALog.json(ALogLevel.ERROR, "{\"id\":456,\"data\":[\"a\",\"b\",\"c\"]}")),
            new ListItem("ALog.json(\"\")", () -> ALog.json("")),
            new ListItem("ALog.json(\"{45, \"id\"}\")", () -> ALog.json("{45, \"id\"}")),
            new ListItem("ALog.xml(\"<root><object name=\"title\"><child/><child id=\"1\"><item/><item/></child></root>\")", () -> ALog.xml("<root><object name=\"title\"><child/><child id=\"1\"><item/><item/></child></object></root>")),
            new ListItem("ALog.xml(ALogLevel.WARNING, \"<root><object name=\"title\"><child/><child id=\"1\"><item/><item/></child></root>\")", () -> ALog.xml(ALogLevel.WARNING, "<root><object name=\"title\"><child/><child id=\"1\"><item/><item/></child></object></root>")),
            new ListItem("ALog.xml(\"\")", () -> ALog.xml("")),
            new ListItem("ALog.xml(\"<root><item/><item2><root/>\")", () -> ALog.xml("<root><item/><item2><root/>")),
            new ListItem("ALog.xml(\"<res:resource xmlns:res=\\\"http://www.example.com/ns/server/resource\\\" xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xsi:schemaLocation=\\\"http://www.example.com/ns/server/resource resource.xsd\\\" version=\\\"1\\\">  <res:message httpCode=\\\"200\\\" type=\\\"ok\\\">   <![CDATA[Sample Success Response]]>    </res:message>    <dif:person xmlns:dif=\\\"http://www.example.com/ns/server/resource\\\"                xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance-1\\\" xsi:schemaLocation=\\\"http://www.example.com/ns/server/person person.xsd\\\" version=\\\"1\\\"> <dif:name>test name</dif:name><dif:description lang=\\\"en\\\">test description</dif:description>    </dif:person ></res:resource>\")", () -> ALog.xml("<res:resource xmlns:res=\"http://www.example.com/ns/server/resource\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.com/ns/server/resource resource.xsd\" version=\"1\">  <res:message httpCode=\"200\" type=\"ok\">   <![CDATA[Sample Success Response]]>    </res:message>    <dif:person xmlns:dif=\"http://www.example.com/ns/server/resource\"                xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance-1\" xsi:schemaLocation=\"http://www.example.com/ns/server/person person.xsd\" version=\"1\"> <dif:name>test name</dif:name><dif:description lang=\"en\">test description</dif:description>    </dif:person ></res:resource>")),
            new ListItem("ALog.xml(\"<example>\\n<!-- This is a comment -->\\n</example>\")", () -> ALog.xml("<example>\n<!-- This is a comment -->\n</example>")),
            new ListItem("ALog.hex(null);", () -> ALog.hex(null)),
            new ListItem("ALog.hex(new byte[]{127, -13, 0, 123, -127});", () -> ALog.hex(new byte[]{127, -13, 0, 123, -127})),
            new ListItem("ALog.hex(ALogLevel.VERBOSE, new byte[] {111, 32, 123, -128, 0, -100, 98, 127, 32, 10});", () -> ALog.hex(ALogLevel.VERBOSE, new byte[] {111, 32, 123, -128, 0, -100, 98, 127, 32, 10})),
            new ListItem("Object o = null;\nALog.e(o);", () -> {
                Object o = null;
                ALog.e(o);
            }),
            new ListItem("byte[] bytes = {1, 2, 3};\nALog.v(bytes);", () -> {
                byte[] bytes = {1, 2, 3};
                ALog.v(bytes);
            }),
            new ListItem("ALog.i(new byte[][] {{1, 2, 3}, {4, 5, 6, 7}, {8, 9}});", () -> ALog.i(new byte[][] {{1, 2, 3}, {4, 5, 6, 7}, {8, 9}})),
            new ListItem("List<Integer> list = new ArrayList<>();\nlist.add(1);\nlist.add(2);\nlist.add(3);\nlist.add(4);\nALog.d(list);", () -> {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                list.add(4);
                ALog.d(list);
            }),
            new ListItem("Map<Long, Integer> map = new LinkedHashMap<>();\nmap.put(1L, 45);\nmap.put(2L, 76);\nmap.put(3L, 100);\nALog.w(map);", () -> {
                Map<Long, Integer> map = new LinkedHashMap<>();
                map.put(1L, 45);
                map.put(2L, 76);
                map.put(3L, 100);
                ALog.w(map);
            }),
            new ListItem("byte[] bytes = {1, 2, 3};\nALog.d(\"Array formatted: %s\", (Object) bytes);", () -> {
                byte[] bytes = {1, 2, 3};
                ALog.d("Array formatted: %s", (Object) bytes);
            }),
            new ListItem("int[] ints = {1, 2, 3, 4};\nALog.d(\"Array formatted: %s\", (Object) ints);", () -> {
                int[] ints = {1, 2, 3, 4};
                ALog.d("Array formatted: %s", (Object) ints);
            }),
            new ListItem("Integer[] integers = {1, 2, 3, 4};\nALog.d(\"Array formatted: %s\", (Object) integers);", () -> {
                Integer[] integers = {1, 2, 3, 4};
                ALog.d("Array formatted: %s", (Object) integers);
            }),
            new ListItem("String[] strings = {\"s1\", \"s2\", null};\nALog.d(\"Array formatted: %s\", (Object) strings);", () -> {
                String[] strings = {"s1", "s2", null};
                ALog.d("Array formatted: %s", (Object) strings);
            }),
            new ListItem("long[][] longs = {{1L, 2L, 3L}, {3L, 2L, 1L}};\nALog.d(\"Array formatted: %s\", (Object) longs);", () -> {
                long[][] longs = {{1L, 2L, 3L}, {3L, 2L, 1L}};
                ALog.d("Array formatted: %s", (Object) longs);
            })
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.list_view);
        if (listView != null) {
            listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, LIST_ITEMS));
            listView.setOnItemClickListener((parent, view, position, id) -> {
                LIST_ITEMS[position].mAction.run();
                Toast.makeText(MainActivity.this, R.string.log_message_was_sent, Toast.LENGTH_SHORT).show();
            });
        }
    }

    private static class ListItem {
        final String mTitle;
        final Runnable mAction;

        ListItem(@NonNull String title, @NonNull Runnable action) {
            mTitle = title;
            mAction = action;
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public String toString() {
            return mTitle;
        }
    }
}
