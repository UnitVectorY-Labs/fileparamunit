/*
 * Copyright 2024 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */
package com.unitvectory.fileparamunit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * The Real Test is an actual parameterized test that is used to determine if the correct files are
 * being loaded. This is not percise as it can't tell if files are missed, but will fail if an
 * incorrect file was loaded.
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class RealTest {

    @ParameterizedTest
    @ListFileSource(resources = "/files/", fileExtension = ".json", recurse = true)
    public void testRecurseJson(String fileName) {
        File file = new File(fileName);
        Set<String> allowedNames = new HashSet<>(Arrays.asList("foo.json", "bar.json", "baz.json"));
        assertTrue(allowedNames.contains(file.getName()),
                "Unexpected file found: " + file.getName());
    }

    @ParameterizedTest
    @ListFileSource(resources = "/files/", fileExtension = ".json", recurse = false)
    public void testNoRecurseJson(String fileName) {
        File file = new File(fileName);
        Set<String> allowedNames = new HashSet<>(Arrays.asList("baz.json"));
        assertTrue(allowedNames.contains(file.getName()),
                "Unexpected file found: " + file.getName());
    }

    @ParameterizedTest
    @ListFileSource(resources = "/files/", fileExtension = ".csv", recurse = true)
    public void testRecurseCsv(String fileName) {
        File file = new File(fileName);
        Set<String> allowedNames = new HashSet<>(Arrays.asList("foo.csv", "bar.csv", "baz.csv"));
        assertTrue(allowedNames.contains(file.getName()),
                "Unexpected file found: " + file.getName());
    }

    @ParameterizedTest
    @ListFileSource(resources = "/files/", fileExtension = ".csv", recurse = false)
    public void testNoRecurseCsv(String fileName) {
        File file = new File(fileName);
        Set<String> allowedNames = new HashSet<>(Arrays.asList("baz.csv"));
        assertTrue(allowedNames.contains(file.getName()),
                "Unexpected file found: " + file.getName());
    }


    @ParameterizedTest
    @ListFileSource(resources = "/files/", fileExtension = ".txt", recurse = true)
    public void testRecurseTxt(String fileName) {
        File file = new File(fileName);
        Set<String> allowedNames = new HashSet<>(Arrays.asList("foo.txt", "bar.txt", "baz.txt"));
        assertTrue(allowedNames.contains(file.getName()),
                "Unexpected file found: " + file.getName());
    }

    @ParameterizedTest
    @ListFileSource(resources = "/files/", fileExtension = ".txt", recurse = false)
    public void testNoRecurseTxt(String fileName) {
        File file = new File(fileName);
        Set<String> allowedNames = new HashSet<>(Arrays.asList("baz.txt"));
        assertTrue(allowedNames.contains(file.getName()),
                "Unexpected file found: " + file.getName());
    }
}
