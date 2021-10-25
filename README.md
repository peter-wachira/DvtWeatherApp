# Weather App


## Pre Requisities
- [A valid Open Weather API Key](https://openweathermap.org/appid)
- A valid Google maps Api Key

## Setup
 - Paste your open weather API key to ``api_key`` on `strings.xml`
 - Paste you Google Api key in ``local.properties``
 - Build the project

* gets current weather forecast of a location and displays forecast over the next week.
* Shows a list of favourite locations a map

## Tech-stack

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference.
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations.
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - handle the stream of data asynchronously that executes sequentially.
    * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
    * [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android.
    * [Jetpack](https://developer.android.com/jetpack)
        * [Room](https://developer.android.com/topic/libraries/architecture/room) - a persistence library provides an abstraction layer over SQLite.
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes.
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way.
        * [Jetpack Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) -  Implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.

* Architecture
    * MVVM - Model View View Model
* Tests
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit](https://junit.org/junit4/)) - a simple framework to write repeatable tests.
    * [MockK](https://github.com/mockk) - mocking library for Kotlin
    * [Truth](https://github.com/google/truth) - Truth makes your test assertions and failure messages more readable.

<table>
<tr>
<td>
<img  width="800" height="400" src="./screenshort/tests.png"/>
</td>
</tr>
<td>
<img  width="800" height="400" src="./screenshort/test2.png"/>
</td>
</tr>
</table>

    * Plugins
        * [Detekt](https://github.com/detekt/detekt) - a static code analysis tool for the Kotlin programming language.

* CI/CD
    * Github Actions

<table>
<tr>
<td>
<img  width="200" height="400" src="./screenshort/8.png"/>
</td>
<td>
<img  width="200" height="400" src="./screenshort/9.png"/>
</td>
<td>
<img  width="200" height="400" src="./screenshort/10.png"/>
</td>
</tr>

<tr>
<td>
<img  width="200" height="400" src="./screenshort/3.png"/>
</td>
<td>
<img  width="200" height="400" src="./screenshort/11.png"/>
</td>
<td>
<img  width="200" height="400" src="./screenshort/12.png"/>
</td>
</table>



