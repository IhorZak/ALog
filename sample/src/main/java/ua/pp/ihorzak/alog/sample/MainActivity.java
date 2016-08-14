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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import ua.pp.ihorzak.alog.ALog;

/**
 * Main activity of ALog library sample application.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 * @since 12.08.2016
 */
public class MainActivity extends AppCompatActivity {
    private static final ListItem[] LIST_ITEMS = {
            new ListItem("ALog.v(\"Message, %d, %s\", 10, \"Argument\")", new Runnable() {
                @Override
                public void run() {
                    ALog.v("Message, %d, %s", 20, "Argument");
                }
            }),
            new ListItem("ALog.d(\"Message, %d, %s\", 10, \"Argument\")", new Runnable() {
                @Override
                public void run() {
                    ALog.d("Message, %d, %s", 20, "Argument");
                }
            }),
            new ListItem("ALog.i(\"Message, %d, %s\", 10, \"Argument\")", new Runnable() {
                @Override
                public void run() {
                    ALog.i("Message, %d, %s", 20, "Argument");
                }
            }),
            new ListItem("ALog.w(\"Message, %d, %s\", 10, \"Argument\")", new Runnable() {
                @Override
                public void run() {
                    ALog.w("Message, %d, %s", 20, "Argument");
                }
            }),
            new ListItem("ALog.e(\"Message, %d, %s\", 10, \"Argument\")", new Runnable() {
                @Override
                public void run() {
                    ALog.e("Message, %d, %s", 20, "Argument");
                }
            }),
            new ListItem("ALog.wtf(\"Message, %d, %s\", 10, \"Argument\")", new Runnable() {
                @Override
                public void run() {
                    ALog.wtf("Message, %d, %s", 20, "Argument");
                }
            }),
            new ListItem("ALog.t(\"Tag\").d(\"Message, %d, %s\", 10, \"Argument\")", new Runnable() {
                @Override
                public void run() {
                    ALog.t("Tag").d("Message, %d, %s", 20, "Argument");
                }
            }),
            new ListItem("ALog.t(null).d(\"Message, %d, %s\", 10, \"Argument\")", new Runnable() {
                @Override
                public void run() {
                    ALog.t(null).d("Message, %d, %s", 20, "Argument");
                }
            }),
            new ListItem("ALog.st(3).d(\"Message, %d, %s\", 10, \"Argument\")", new Runnable() {
                @Override
                public void run() {
                    ALog.st(3).d("Message, %d, %s", 20, "Argument");
                }
            }),
            new ListItem("ALog.tst(\"Tag\", 5).d(\"Message, %d, %s\", 10, \"Argument\")", new Runnable() {
                @Override
                public void run() {
                    ALog.tst("Tag", 5).d("Message, %d, %s", 20, "Argument");
                }
            })
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list_view);
        if (listView != null) {
            listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, LIST_ITEMS));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LIST_ITEMS[position].mAction.run();
                    Toast.makeText(MainActivity.this, R.string.log_message_was_sent, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private static class ListItem {
        public final String mTitle;
        public final Runnable mAction;

        public ListItem(String title, Runnable action) {
            mTitle = title;
            mAction = action;
        }

        @Override
        public String toString() {
            return mTitle;
        }
    }
}
