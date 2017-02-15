Elves
============

Elves is an Android Clean Framework which helps to implement clean architecture into your project. It was
designed by [Idealista](http://idealista.com) to use internally, and now we are open sourcing these libraries to make
 them available for anyone that want to use and/or contribute.

Elves framework it's divided into two libraries elves and elves-android, surprisingly, android-free and android 
dependant libraries.

Elves includes:
* "UseCases", an interface that define MVP use cases. It represents the logic of the application.
* "CommandUis", a Callback to be used to handle the response of the `UseCase`.
* "Commands", it encapsulate the execution of `UseCase` and it's callback `CommandUi`.
* "Lists", interface and default implementation to wrap plain lists into a list objects.
* "Tasks", which makes organization of complex asynchronous code more manageable. It's a fork of [Bolts](https://github.com/BoltsFramework/Bolts-Android) decoupling its Android reference.

Elves-Android includes:
* "App", class that extends Android Application to initialize the elves components.
* "Navigators", interface that defines the Navigator pattern used in Elves.
* "Adapter", adapter implementation that cover the simplest solution for ui lists.
* "Presenter", base implementation for all presenters.
* "Activity", base implementation for all Activities.
* "View", base interface for all Views
* "CustomView", base implementation for all CustomViews


## Download
Download [the lates elves JARs][latest] and [the lates elves-android JARs][latest-android] or define in Gradle:

```groovy
dependencies {
  compile 'com.idealista:elves:0.0.1'
  compile 'com.idealista:elves-android:0.0.1'
}
```

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snap].

 [latest]: https://search.maven.org/remote_content?g=com.idealista&a=elves&v=LATEST
 [latest-android]: https://search.maven.org/remote_content?g=com.idealista&a=elves-android&v=LATEST
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
