# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-assumenosideeffects class ua.pp.ihorzak.alog.ALogConfiguration {
    public static *** builder(...);
}
-assumenosideeffects class ua.pp.ihorzak.alog.ALogFormatter {
    public static *** create(...);
}
-assumenosideeffects class ua.pp.ihorzak.alog.ALogConfiguration.Builder {
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
    public *** arrayFormatterEnabled(...);
    public *** collectionFormatterEnabled(...);
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
