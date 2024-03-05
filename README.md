[![License](https://img.shields.io/badge/License-EPL%202.0-blue.svg)](https://www.eclipse.org/legal/epl-v20.html) [![Maven Central](https://img.shields.io/maven-central/v/com.unitvectory/fileparamunit)](https://central.sonatype.com/artifact/com.unitvectory/fileparamunit) [![javadoc](https://javadoc.io/badge2/com.unitvectory/fileparamunit/javadoc.svg)](https://javadoc.io/doc/com.unitvectory/fileparamunit) [![codecov](https://codecov.io/gh/UnitVectorY-Labs/fileparamunit/graph/badge.svg?token=4V52PCKXPU)](https://codecov.io/gh/UnitVectorY-Labs/fileparamunit)

# fileparamunit

Library for creating parameterized JUnit 5 tests based on files that exist in resources.

## Purpose

This library provides an extension to the [JUnit 5 Parameterized Tests](https://www.baeldung.com/parameterized-tests-junit-5), a very helpful way to implement multiple test cases without needing to duplicate code.

While this framework provides useful methods for parameters including Arguments, CSV file contents, enumerations, methods, and values, this library provides an additional method, looping through a directory of files and providing the path to each file as an input. The parameter can specify the file extensions to include in the output and recursion can be turned on if desired.

The reasoning behind this is that a set of test data can be used with a common JUnit test code to acchieve a testing objective while reducing the amount of code needed shifting that complexity over to the contexts of the file.

## Getting Started

This library requires Java 17 and JUnit 5 and is available in the Maven Central Repository:

```xml
<dependency>
    <groupId>com.unitvectory</groupId>
    <artifactId>fileparamunit</artifactId>
    <version>0.0.2</version>
    <scope>test</scope>
</dependency>
```

## Usage

This library is simple to use but is an extension of [junit-jupiter-params](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-params) and is built on top of that library.

The `@ListFileSource` annotation is provided by this library and is used in conjunction with `@ParameterizedTest` to provide the parameterized test. It provides a single parameter, a String populated with the absolute path to the files specifed by providing `resources` which is the path under resources to crawl along with `fileExtension` which must be specified to filter into files with the indicated file extension. By default only the specified directory is crawled, but `recurse` can be set to true to recursive crawl the directories for additional files.

The following example looks at all files under resources in the `testData` folder with the `.json` file extension recursing through any subfolders. The fileName is passed to the method where it can be used to complete a test.

```java
package example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.unitvectory.fileparamunit.ListFileSource;
import java.io.File;
import org.junit.jupiter.params.ParameterizedTest;

public class ExampleTest {

    @ParameterizedTest
    @ListFileSource(resources = "/testData/", fileExtension = ".json", recurse = true)
    public void exampleTest(String fileName) {
        File file = new File(fileName);

        // This is where logic to use the content of the file would go
        assertTrue(file.exists());
    }
}
```
