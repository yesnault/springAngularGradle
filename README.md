Bootstrap with Spring, AngularJS and Gradle
==========

Run Jetty (usefull for dev) with ``` gradle jettyRun ```

Build a war with ``` gradle build ```, war generated in services/build/

Create a new release with ``` gradle release -PreleaseVersion=X.Z.Z ``` or a snaphot release with ``` gradle release ```
See https://github.com/ari/gradle-release-plugin for more informations about Release Plugin.

All js are in one file all-app.js after ```gradle build```
See http://eriwen.github.io/gradle-js-plugin/ for more informations about JS Plugin (if you want to minify or gzip)

