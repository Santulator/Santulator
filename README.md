# 🎁 Santulator

[![Build Status](https://img.shields.io/travis/Santulator/Santulator/master.svg)](https://travis-ci.org/Santulator/Santulator)
[![Code Coverage](https://img.shields.io/codecov/c/github/Santulator/Santulator.svg)](https://codecov.io/gh/Santulator/Santulator)
[![SonarCloud](/assets/SonarCloud.svg)](https://sonarcloud.io/dashboard?id=io.github.santulator%3Asantulator)
[![Latest Release](https://img.shields.io/github/release/Santulator/Santulator.svg)](https://github.com/Santulator/Santulator/releases/latest)
[![Twitter Follow](https://img.shields.io/twitter/follow/vocabhunterapp.svg?style=social&label=Follow)](https://twitter.com/vocabhunterapp)

Santulator helps you run your Secret Santa draw simply and flexibly.  You can enter the names of the people participating in the draw and the system will randomly choose who will give presents to whom.  If you want to avoid certainly parings, for example to ensure that people don't get their own partners in the draw, you can add exclusions.  When the draw is run, a PDF will be generated for each person telling them who to buy a present for.  If you want to avoid accidentally seeing all of the results, you can add a secret password to the generated PDF files.
[![Santulator draw selection](/assets/Santulator-Draw-Selection-Cards-1.png)](https://santulator.github.io/)
[![Santulator in action](/assets/Santulator-Draw-Wizard-2.gif)](https://santulator.github.io/)

# How To Use Santulator

Santulator is available for free from the [download page](https://santulator.github.io/download/) of the project website.  You can find a complete tutorial explaining how to use the system on the [help page](https://santulator.github.io/help/).

# Running The Development Version of Santulator

You will need JDK 11.  The version used for building Santulator releases is OpenJDK 11 with Hotspot, from [AdoptOpenJDK](https://adoptopenjdk.net/).

With your JDK installed you can then clone this repository and run the following to start Santulator:
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

# How to Build The Installable Bundle

See [PACKAGING.md](package/PACKAGING.md) for full, step-by-step instructions for creating the Santulator installable bundle using JDK 16.

Prior to the release of JDK 16, the Santulator installable bundle used to be created using JDK 11.  You can find more information about this in the article [Using the Java Packager with JDK 11].

[![Using the Java Packager with JDK 11](/assets/Using-The-Java-Packager-With-JDK-11.png)][Using the Java Packager with JDK 11]

# Open Source & Secret Santa with Santulator

[![Open Source & Secret Santa with Santulator](/assets/Open-Source-And-Secret-Santa-With-Santulator.png)][Open Source & Secret Santa with Santulator]

Over on the King Tech Blog you can read more about the project in [Open Source & Secret Santa with Santulator]. The article includes information about how JavaFX CSS is used to give Santulator a festive colour scheme.

[![Before and after colours](/assets/Before-And-After-Colours.png)][Open Source & Secret Santa with Santulator]

[Using the Java Packager with JDK 11]:https://medium.com/@adam_carroll/java-packager-with-jdk11-31b3d620f4a8
[Open Source & Secret Santa with Santulator]:https://medium.com/techking/open-source-secret-santa-with-santulator-9101972359fc
