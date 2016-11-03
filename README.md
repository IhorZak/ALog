[ ![Download](https://api.bintray.com/packages/ihorzak/maven/ALog/images/download.svg) ](https://bintray.com/ihorzak/maven/ALog/_latestVersion)

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
- Automatic providing of log message tag based on class name

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

If you need to log few messages with the same custom tag and/or the same custom count of stack trace lines it is strictly recommended to keep ALogger instance as each call to `t(String tag)`, `st(int stackTraceLineCount)` or `tst(String tag, int stackTraceLineCount)` creates new ALogger instance:
```java
ALogger logger = ALog.tst("Tag", 3);
logger.w("Message, %d, %s", 20, "Argument");
logger.wtf(new RuntimeException("Fatal error"), "Message, %d, %s", 20, "Argument");
```

## Download
The latest version is available via [JCenter][1].
For example, to grab it via Gradle you can use next snippet:
```groovy
buildscript {
    repositories {
        jcenter()
    }
}

dependencies {
    compile 'ua.pp.ihorzak:alog:0.1.0'
}
```

## API Reference
[Javadoc][2]

## ProGuard Configuration
If you want to remove ALog calls from release builds, you can use next ProGuard rules:
```
-assumenosideeffects class ua.pp.ihorzak.alog.ALog {
    public static *** initialize(...);
    public static *** t(...);
    public static *** st(...);
    public static *** tst(...);
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
    public static *** json(...);
    public static *** xml(...);
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

  [1]: https://bintray.com/ihorzak/maven/ALog
  [2]: https://ihorzak.github.io/ALog/
