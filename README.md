# BeatFly

BeatFly is a simple Android application that was a task as part of my
bachelor studies. It a music player for an imaginary radio station, 
which allows users to see current songs, history of previous songs and 
moderators. It also allows to review the current playlist and wish for
specific songs. Moderators can also log in to have access to the same 
functionality but also see recent reviews of users.

As the radio station is imaginary, no real server exists to communicate
with. Instead the application simulates the interaction with the server
by playing a randomly generated list of predefined songs. Actions like
writing reviews and wishing for songs won't have any actual effect.

## Prerequisites

To build this project, some setup has to be done. If you already worked 
other Android projects before, you can probably skip some steps.

### Java

Make sure you have the latest Java 1.8 installed. On Mac you can install
it with quickly with Homebrew:

```
brew cask install adoptopenjdk8
```

### Signing key

To build the release version of the application, you need to create a
keystore and tell Gradle which account and password within it to use.

To create a new keystore, follow the [official docs](https://developer.android.com/studio/publish/app-signing).
Put the keystore at the root of the project and name it `keystore.jks`,
so Gradle can find it.

Then update your `local.properties` file (or create a new one if it 
doesn't exist already). Add the entries `keystore.password`, 
`keystore.key-alias` and `keystore.key-password` with the information
of your keystore.

## Build

To build the project you can simply run `./gradlew assemble` to build
both debug and release versions. If you don't want to set up your own
keystore you can run `./gradlew assembleDebug` to only build the debug
version.

## License

This project is licensed under the [GNU General Public License v3](LICENSE).
