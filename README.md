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
