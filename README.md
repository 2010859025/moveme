# Move me

It is an application which provide the ability to manage the movement of people and companies to a different location
inside a town, to different towns or even countries.

## Getting Started

To start the application for development go to the Class `fh.burgenland.moveme.MovemeApplication` and run the `main` method with your IDE.

## Gradle

### How to start tests
    gradle test

### How to build the application to get an artifact
    gradle init

## .editorconfig
EditorConfig helps maintain consistent coding styles for multiple developers\
working on the same project across various editors and IDEs

### Examples
See further details at https://editorconfig.org/#example-file

## PMD

### How to start the pmd analysis
add plugin "pmd" to build.gradle

    plugins {
        id 'pmd'
    }

execute command:

    gradle check

