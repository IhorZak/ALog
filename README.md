[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ua.pp.ihorzak/alog/badge.svg)](https://search.maven.org/artifact/ua.pp.ihorzak/alog)

# ALog
Simple Android logger based on android.util.Log.

ALog can provide additional information with log messages:
- Thread name
- Class name
- Method name
- Source information(file name and line number)
- Stack trace

Other important features of ALog are:
- Formatted output of JSON and XML
- Formatted output of byte arrays in hexadecimal representation
- Automatic providing of log message tag based on class name
- Pretty formatting of arrays, collections and maps
- Possibility to provide custom formatters for object types
- Additional output to file on device storage

## Usage
ALog supports all Android API versions starting from API 1.

ALog can be configured with the initialize method. It is recommended to do it once in application's `onCreate()` method. If ALog will not be initialized explicitly it will be initialized implicitly on the first call with default configuration.
Example:
```java
ALogConfiguration aLogConfiguration = ALogConfiguration.builder()
         .tag("ALogSampleApplication")
         .classPrefixEnabled(true)
         .jsonIndentSpaceCount(4)
         .xmlIndentSpaceCount(4)
         .build();
ALog.initialize(aLogConfiguration);
```

To perform logging output to file on device storage (additionally to standard output) `ALogFileConfiguration` should be passed to `ALogConfiguration` `file(ALogFileConfiguration fileConfiguration)` method.
Example:
```java
ALogConfiguration aLogConfiguration = ALogConfiguration.builder()
            .file(ALogFileConfiguration.single("launch.log", false))
            .build();
```

Logging with ALog is simple:
```java
ALog.v("Message, %d, %s", 20, "Argument");
ALog.d("Message, %d, %s", 20, "Argument");
ALog.i("Message, %d, %s", 20, "Argument");
ALog.w("Message, %d, %s", 20, "Argument");
ALog.e("Message, %d, %s", 20, "Argument");
ALog.wtf("Message, %d, %s", 20, "Argument"
ALog.json("{\"id\":456,\"data\":[\"a\",\"b\",\"c\"]}");
ALog.xml(ALogLevel.WARNING, "<root><object name=\"title\"><child/><child id=\"1\"><item/><item/></child></object></root>");
ALog.hex(new byte[]{127, -13, 0, 123, -127});
ALog.w(new IOException("Message");
```

There is also possibility to change log tag for some log messages:
```java
ALog.t("Tag").d("Message, %d, %s", 20, "Argument");
```

Also providing stack trace lines for some log messages is available:
```java
ALog.st(3).d("Message, %d, %s", 20, "Argument");
```
If some log message should have custom tag and contain custom stack trace lines count `tst(String tag, int stackTraceLineCount)` method should be used:
```java
ALog.tst("Tag", 5).d("Message, %d, %s", 20, "Argument");
```

In case ALog is configured to provide additional data like thread name, class name, source data, stack trace lines and there is a need to output "bare" log message `b()` method should be used:
```java
ALog.b().d("Message, %d, %s", 20, "Argument");
```
To output "bare" log message with custom tag `tb(String tag)` method should be used:
```java
ALog.tb("Tag").d("Message, %d, %s", 20, "Argument");
```

If you need to log few messages with the same custom tag and/or the same custom count of stack trace lines it is strictly recommended to keep ALogger instance as each call to `t(String tag)`, `st(int stackTraceLineCount)`, `tst(String tag, int stackTraceLineCount)`, `b()` or `tb(String tag)` creates new ALogger instance:
```java
ALogger logger = ALog.tst("Tag", 3);
logger.w("Message, %d, %s", 20, "Argument");
logger.wtf(new RuntimeException("Fatal error"), "Message, %d, %s", 20, "Argument");
```

Using default configuration ALog will use pretty formatters for arrays, collections, iterables and maps. So next code
```java
ALog.d(new int[] {1, 2, 3, 4});
ALog.d(new Float[] {1.0f, 2.0f, 3.5f});
ALog.d(new String[] {"s1", "s2", "s3", "s4", "s5"});
ALog.d(new byte[][] {{1, 2, 3}, {4, 5, 6, 7}, {8, 9}});
List<Integer> list = new ArrayList<>();
list.add(1);
list.add(2);
list.add(3);
list.add(4);
ALog.d(list);
TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
splitter.setString("1,2,3,4");
ALog.d(splitter);
Map<Long, Integer> map = new LinkedHashMap<>();
map.put(1L, 45);
map.put(2L, 76);
map.put(3L, 100);
ALog.d(map);
```
will produce the next output:
```
02-08 21:19:59.637 3461-3461/ua.pp.ihorzak.alog.sample D/ALogSampleApplication: [main|MainActivity$override|onCreate|(MainActivity.java:293)] Array(size = 4) [1, 2, 3, 4]
02-08 21:19:59.637 3461-3461/ua.pp.ihorzak.alog.sample D/ALogSampleApplication: [main|MainActivity$override|onCreate|(MainActivity.java:294)] Array(size = 3) [1.0, 2.0, 3.5]
02-08 21:19:59.637 3461-3461/ua.pp.ihorzak.alog.sample D/ALogSampleApplication: [main|MainActivity$override|onCreate|(MainActivity.java:295)] Array(size = 5) [s1, s2, s3, s4, s5]
02-08 21:19:59.637 3461-3461/ua.pp.ihorzak.alog.sample D/ALogSampleApplication: [main|MainActivity$override|onCreate|(MainActivity.java:296)] Array(size = 3) [Array(size = 3) [1, 2, 3], Array(size = 4) [4, 5, 6, 7], Array(size = 2) [8, 9]]
02-08 21:19:59.637 3461-3461/ua.pp.ihorzak.alog.sample D/ALogSampleApplication: [main|MainActivity$override|onCreate|(MainActivity.java:302)] java.util.ArrayList(size = 4) [1, 2, 3, 4]
03-13 03:00:48.471 3461-3461/ua.pp.ihorzak.alog.sample D/ALogSampleApplication: [main|MainActivity$override|onCreate|(MainActivity.java:307)] android.text.TextUtils$SimpleStringSplitter(size = 4) [1, 2, 3, 4]
02-08 21:19:59.637 3461-3461/ua.pp.ihorzak.alog.sample D/ALogSampleApplication: [main|MainActivity$override|onCreate|(MainActivity.java:312)] java.util.LinkedHashMap(size = 3) [{1 -> 45}, {2 -> 76}, {3 -> 100}]
```

There is also possibility to provide custom logging formatters for objects of almost any classes (except arrays, collections, iterables and primitive wrappers). This can be done at ALog initialization via `ALogConfiguration.Builder` class method `formatter(Class<?> clazz, ALogFormatter<?> formatter)`. If custom formatter is needed only for some piece of code `ALog` methods `formatter(Class<?> clazz, ALogFormatter<?> formatter)` and `formatters(Map<Class<?>, ALogFormatter<?>> formatterMap)` should be used for creation `ALogger` instances with needed formatters support.
For example if we want to add custom formatter for `android.os.Bundle` class instances it could be done with next code
```java
ALogConfiguration configuration = ALogConfiguration.builder()
        .formatter(Bundle.class, ALogFormatter.create(new ALogFormatterDelegate<Bundle>() {
            @Override
            public String toLoggingString(Bundle bundle) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Bundle [ ");
                for (String key : bundle.keySet()) {
                    stringBuilder.append(key).append(" ");
                }
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
        }))
        .formatter(Intent.class, ALogFormatter.create(new ALogFormatterComplexDelegate<Intent>() {
            @Override
            public String toLoggingString(Intent intent, ALogFormatterDelegate<Object> objectFormatterDelegate) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Intent [action = ");
                stringBuilder.append(intent.getAction());
                stringBuilder.append(", categories = ");
                stringBuilder.append(objectFormatterDelegate.toLoggingString(intent.getCategories()));
                stringBuilder.append(", type = ");
                stringBuilder.append(intent.getType());
                stringBuilder.append(", data = ");
                stringBuilder.append(objectFormatterDelegate.toLoggingString(intent.getData()));
                stringBuilder.append(", extras = ");
                stringBuilder.append(objectFormatterDelegate.toLoggingString(intent.getExtras()));
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
        }))
        .build();
ALog.initialize(configuration);
```
and then next snippet
```java
Bundle arguments = new Bundle();
arguments.putInt("Key1", 1);
arguments.putString("Key2", "Some string");
arguments.putBoolean("Key3", true);
ALog.d(arguments);
Intent intent = new Intent(Intent.ACTION_SEND);
intent.setType("*/*");
intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
intent.putExtra(Intent.EXTRA_TEXT, "Body");
ALog.d(intent);
```
will produce such logging output
```
02-08 22:27:16.841 4709-4709/ua.pp.ihorzak.alog.sample D/ALogSampleApplication: [main|MainActivity|onCreate|(MainActivity.java:297)] Bundle [ Key1 Key2 Key3 ]
02-08 22:27:16.842 4709-4709/ua.pp.ihorzak.alog.sample D/ALogSampleApplication: [main|MainActivity|onCreate|(MainActivity.java:302)] Intent [action = android.intent.action.SEND, categories = null, type = */*, data = null, extras = Bundle[{android.intent.extra.SUBJECT=Subject, android.intent.extra.TEXT=Body}]]
```

## Download
The latest version is available via [Maven Central][1].
For example, to grab it via Gradle you can use next snippet:
```groovy
buildscript {
    repositories {
        mavenCentral()
    }
}

dependencies {
    compile 'ua.pp.ihorzak:alog:0.5.0'
}
```

## API Reference
[Javadoc][2]

## ProGuard/R8 Configuration
If you want to remove ALog calls from release builds, you can use next ProGuard/R8 rules:
```
-assumenosideeffects class ua.pp.ihorzak.alog.ALogConfiguration {
    public static *** builder(...);
}
-assumenosideeffects class ua.pp.ihorzak.alog.ALogChunkFileNameProvider {
    public *** getName(...);
}
-assumenosideeffects class ua.pp.ihorzak.alog.ALogFileConfiguration {
    public static *** single(...);
    public static *** chunked(...);
}
-assumenosideeffects class ua.pp.ihorzak.alog.ALogFormatter {
    public static *** create(...);
}
-assumenosideeffects class ua.pp.ihorzak.alog.ALogConfiguration$Builder {
    public *** enabled(...);
    public *** minimalLevel(...);
    public *** jsonLevel(...);
    public *** xmlLevel(...);
    public *** hexLevel(...);
    public *** tag(...);
    public *** threadPrefixEnabled(...);
    public *** classPrefixEnabled(...);
    public *** methodPrefixEnabled(...);
    public *** lineLocationPrefixEnabled(...);
    public *** stackTraceLineCount(...);
    public *** jsonIndentSpaceCount(...);
    public *** xmlIndentSpaceCount(...);
    public *** file(...);
    public *** arrayFormatterEnabled(...);
    public *** collectionFormatterEnabled(...);
    public *** iterableFormatterEnabled(...);
    public *** mapFormatterEnabled(...);
    public *** formatter(...);
    public *** build(...);
}
-assumenosideeffects class ua.pp.ihorzak.alog.ALog {
    public static *** initialize(...);
    public static *** t(...);
    public static *** st(...);
    public static *** tst(...);
    public static *** formatter(...);
    public static *** formatters(...);
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
    public static *** json(...);
    public static *** xml(...);
    public static *** hex(...);
}
-assumenosideeffects class ua.pp.ihorzak.alog.ALogger {
    public *** v(...);
    public *** d(...);
    public *** i(...);
    public *** w(...);
    public *** e(...);
    public *** wtf(...);
    public *** json(...);
    public *** xml(...);
    public *** hex(...);
}
```

# License
<pre>
Copyright 2016 Ihor Zakhozhyi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>

  [1]: https://search.maven.org/artifact/ua.pp.ihorzak/alog/0.5.0/aar
  [2]: https://ihorzak.github.io/ALog/
