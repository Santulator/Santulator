# 🎁 Santulator

[![Twitter Follow](https://img.shields.io/twitter/follow/vocabhunterapp.svg?style=social&label=Follow)](https://twitter.com/vocabhunterapp)

Santulator helps you run your Secret Santa draw simply and flexibly.  You can enter the names of the people participating in the draw and the system will randomly choose who will give presents to whom.  If you want to avoid certainly parings, for example to ensure that people don't get their own partners in the draw, you can add exclusions.  When the draw is run, a PDF will be generated for each person telling them who to buy a present for.  If you want to avoid accidentally seeing all of the results, you can add a secret password to the generated PDF files.

[![Santulator](/assets/Santulator-in-use-1.png)](https://santulator.github.io/)

# Important Note

Santulator isn't yet ready for release.  Feel free to take a look around and try it out while development continues towards the first release.

# Prerequisites

You will need Java 8 to build and run Santulator.  You can download it from the [Oracle Website](http://www.oracle.com/technetwork/java/javase/downloads/index.html).  Everything else, including Gradle, will be downloaded by the build process.

# How To Run Santulator

Santulator is still being developed and isn't ready yet for release.  You can try out the latest development version from the command line as follows:
~~~
$ ./gradlew :gui:run
~~~

# How To Build Santulator

You can build the entire system with the following command:
~~~
$ ./gradlew clean build
~~~

# How To Run The GUI Test

By default the GUI test runs as part of the standard Gradle build, in headless mode.  If you'd like to run the GUI test in a non-headless mode so that you can see what is happening, use the following command:
~~~
$ ./gradlew :gui:test --tests io.github.santulator.gui.main.GuiTest --rerun-tasks -PnoHeadless
~~~
