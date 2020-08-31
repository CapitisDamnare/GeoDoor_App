# GeoDoor App
This is a program used to smart up your garage door or yard gate.
The GPS signal checks your position and calculates the distance to your configured gate position.
If your x meter away from your entrance the app will send an open command to the connected [<b>GeoDoor Server</b>](https://github.com/JustForFunDeveloper/GeoDoorServer).
The [<b>GeoDoor Server</b>](https://github.com/JustForFunDeveloper/GeoDoorServer) will then open your connected gate.

## Libraries
[![](https://img.shields.io/github/release/JustForFunDeveloper/GeoDoor_App.svg)](https://github.com/JustForFunDeveloper/GeoDoor_App) <br/>
[![](https://img.shields.io/badge/lifecycle_extensions-2.2.0-blue.svg)](https://developer.android.com/jetpack/androidx/releases/lifecycle) <br/>
[![](https://img.shields.io/badge/room-2.2.5-blue.svg)](https://developer.android.com/topic/libraries/architecture/room?gclid=Cj0KCQjwv7L6BRDxARIsAGj-34qXseHYVaOpwUCqOsgGlo3LcANq5O0cnykez6R2XHYHOw4Nc9AeC1gaAjjVEALw_wcB&gclsrc=aw.ds) <br/>
[![](https://img.shields.io/badge/google_location-17.0.0-red.svg)](https://developers.google.com/android/guides/setup) <br/>
[![](https://img.shields.io/badge/retrofit2-2.9.0-red.svg)](https://square.github.io/retrofit/) <br/>
[![](https://img.shields.io/badge/auto_value-1.7.4-green.svg)](https://github.com/google/auto) <br/>

## Issues
[![](https://img.shields.io/github/issues-raw/JustForFunDeveloper/GeoDoor_App.svg?style=flat-square)](https://github.com/JustForFunDeveloper/GeoDoor_App/issues)
[![](https://img.shields.io/github/issues-closed-raw/JustForFunDeveloper/GeoDoor_App.svg?style=flat-square)](https://github.com/JustForFunDeveloper/GeoDoor_App/issues)

## Releases!

Find the latest release on GitHub =>
[<b>Release</b>](https://github.com/JustForFunDeveloper/GeoDoor_App/releases) <br/>

Or in the Google App Store =>
[<b>GeoDoor</b>](https://play.google.com/store/apps/details?id=tapsi.geodoor)

## Pictures

![](https://github.com/JustForFunDeveloper/GeoDoor_App/blob/master/Pictures/Funktionsgrafik.png) <br/>

- - - -
## Short description of the used libraries and techniques
This app uses the following components:

1. ViewModel and LiveData from androidx.lifecycle
2. SqLite Database with androidx.room
3. Google Maps from play-services-maps
4. Google Location from play-services-location
5. Auto Value from com.google.auto.value
6. Auto Value Parcel from com.ryanharter.auto.value
7. Retrofit2 from com.squareup.retrofit2

