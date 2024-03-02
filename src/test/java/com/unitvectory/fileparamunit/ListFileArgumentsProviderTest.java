/*
 * Copyright 2024 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */
package com.unitvectory.fileparamunit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Directly testing the ListFileArgumentsProvider class without going through a full JUnit
 * parameterized test case.
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class ListFileArgumentsProviderTest {

    private ListFileSource buildMock(String resource, boolean recurse, String fileExtension) {
        ListFileSource listFileSource = mock(ListFileSource.class);

        when(listFileSource.resources()).thenReturn(new String[] {resource});
        when(listFileSource.directories()).thenReturn(new String[] {});
        when(listFileSource.recurse()).thenReturn(recurse);
        when(listFileSource.fileExtension()).thenReturn(fileExtension);

        return listFileSource;
    }

    private Set<String> getFiles(ListFileSource listFileSource) {
        ListFileArgumentsProvider provider = new ListFileArgumentsProvider();
        provider.accept(listFileSource);
        Stream<? extends Arguments> stream = provider.provideArguments(null);
        Set<String> set = new TreeSet<String>();
        for (Arguments arg : stream.toList()) {

            Object[] obj = arg.get();
            String str = (String) obj[0];
            set.add(str);
        }

        return set;
    }

    private Set<String> convertToFileNames(Set<String> in) {
        Set<String> out = new TreeSet<>();
        for (String i : in) {
            File f = new File(i);
            out.add(f.getName());
        }
        return out;
    }

    @Test
    public void testYamlEmpty() {
        Set<String> files = getFiles(buildMock("/files/", true, ".yaml"));
        Set<String> actualNames = convertToFileNames(files);
        Set<String> expectedNames = new HashSet<>(Arrays.asList());
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void testRecurseJson() {
        Set<String> files = getFiles(buildMock("/files/", true, ".json"));
        Set<String> actualNames = convertToFileNames(files);
        Set<String> expectedNames =
                new HashSet<>(Arrays.asList("foo.json", "bar.json", "baz.json"));
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void testNoRecurseJson() {
        Set<String> files = getFiles(buildMock("/files/", false, ".json"));
        Set<String> actualNames = convertToFileNames(files);
        Set<String> expectedNames = new HashSet<>(Arrays.asList("baz.json"));
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void testRecurseCsv() {
        Set<String> files = getFiles(buildMock("/files/", true, ".csv"));
        Set<String> actualNames = convertToFileNames(files);
        Set<String> expectedNames = new HashSet<>(Arrays.asList("foo.csv", "bar.csv", "baz.csv"));
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void testNoRecurseCsv() {
        Set<String> files = getFiles(buildMock("/files/", false, ".csv"));
        Set<String> actualNames = convertToFileNames(files);
        Set<String> expectedNames = new HashSet<>(Arrays.asList("baz.csv"));
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void testRecurseTxt() {
        Set<String> files = getFiles(buildMock("/files/", true, ".txt"));
        Set<String> actualNames = convertToFileNames(files);
        Set<String> expectedNames = new HashSet<>(Arrays.asList("foo.txt", "bar.txt", "baz.txt"));
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void testNoRecurseTxt() {
        Set<String> files = getFiles(buildMock("/files/", false, ".txt"));
        Set<String> actualNames = convertToFileNames(files);
        Set<String> expectedNames = new HashSet<>(Arrays.asList("baz.txt"));
        assertEquals(expectedNames, actualNames);
    }
}
