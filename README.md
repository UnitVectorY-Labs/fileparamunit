[![GitHub release](https://img.shields.io/github/release/UnitVectorY-Labs/fileparamunit.svg)](https://github.com/UnitVectorY-Labs/fileparamunit/releases/latest) [![License](https://img.shields.io/badge/License-EPL%202.0-blue.svg)](https://www.eclipse.org/legal/epl-v20.html) [![Active](https://img.shields.io/badge/Status-Active-green)](https://guide.unitvectorylabs.com/bestpractices/status/#active) [![Maven Central](https://img.shields.io/maven-central/v/com.unitvectory/fileparamunit)](https://central.sonatype.com/artifact/com.unitvectory/fileparamunit) [![javadoc](https://javadoc.io/badge2/com.unitvectory/fileparamunit/javadoc.svg)](https://javadoc.io/doc/com.unitvectory/fileparamunit) [![codecov](https://codecov.io/gh/UnitVectorY-Labs/fileparamunit/graph/badge.svg?token=4V52PCKXPU)](https://codecov.io/gh/UnitVectorY-Labs/fileparamunit)

# fileparamunit

Library for creating parameterized JUnit 5 tests based on files that exist in resources.

## Purpose

This library provides an extension to the JUnit 5 Parameterized Tests [junit-jupiter-params](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-params), a very helpful way to implement multiple test cases without needing to duplicate code.

While JUnit 5 provides useful methods for parameters including Arguments, CSV file contents, enumerations, methods, and values, `fileparamunit` provides an additional mechanism looping through a directory of files and providing the path to each file as an input to the test case. The annotation can specify the file extensions to include in the output and recursion can be turned on if desired.

The reasoning behind this is that a set of test data can be used with a common JUnit test code to acchieve a testing objective while reducing the amount of code needed shifting that complexity over to the contexts of the files.

## Getting Started

This library requires Java 17 and JUnit 5 and is available in the Maven Central Repository:

```xml
<dependency>
    <groupId>com.unitvectory</groupId>
    <artifactId>fileparamunit</artifactId>
    <version>0.1.1</version>
    <scope>test</scope>
</dependency>
```

## Usage

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

## Dependencies and Compatibility

This library depends on JUnit 5.13.0 or newer specifically due to a breaking change in the `AnnotationBasedArgumentsProvider` class. The `junit-jupiter-params` module must be included in your project as a dependency to use the `@ListFileSource` annotation.

If you have compilation issues related to JUnit 5 when using this library, it is recommended to use the JUnit BOM (Bill of Materials) to ensure compatibility with the JUnit 5 version you are using. You can add the following dependency management section to your `pom.xml`:

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.junit</groupId>
      <artifactId>junit-bom</artifactId>
      <version>5.13.1</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```
