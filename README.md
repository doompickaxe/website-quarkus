# website-quarkus

This application is the source code of my personal website hosted on https://www.kevinwatzal.de.

Either using the hosted Swagger-UI or by simply executing REST calls my CV can be explored.

# Technological choices

* JVM: Used because I worked with it the most and also because I like the concept of the JVM buildtime as well as
  runtime.
* Quarkus: Quarkus is a flexible, but fast framework very similar to Spring Boot, but with less historical burdens that
  build up over time.
* Kotlin: I like the syntax of Kotlin and the concepts it has brought up since the early beginnings.
* Exposed: Quite simple reason for using Exposed: I wanted to try it out and see where its limits are.
* Gradle: I just dislike XML of Maven so much, that I rather prefer using Gradle.

# AI usage

Even though I do not mind using LLMs to write code and I do it at work professionally,
I want to showcase my skills and also explore some frameworks manually, which is why no code has been written by or with
LLMs here.

# Run/Test

To run the tests and also build the application simply call
`./gradlew build`

Build a docker container with
`./gradlew quarkusBuild`
