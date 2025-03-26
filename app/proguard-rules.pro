# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-assumenosideeffects class android.util.Log{*;}


-optimizationpasses 5
-overloadaggressively
-repackageclasses ''
-allowaccessmodification
#ksoap
-ignorewarnings


# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)


#LOCATION
-dontwarn sun.misc.Unsafe
-dontoptimize


#realm

-dontwarn javax.**
-dontwarn io.realm.**

-dontwarn elink.mjp.water.meterreading.**
-dontwarn elink.mjp.water.meterreading.Login.**

-dontwarn elink.mjp.water.meterreading.recyclerviewpager.**


### Picasso
-dontwarn com.squareup.okhttp.**

### Crashlytics
# In order to provide the most meaningful crash reports
# If you're using custom Eception
-dontwarn com.crashlytics.**
-dontwarn com.google.firebase.crashlytics.**

### Crash report
-renamesourcefileattribute SourceFile




-keep class **.R
-keep class **.R$* {
    <fields>;
}
