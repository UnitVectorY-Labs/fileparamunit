/*
 * Copyright 2024 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */
package com.unitvectory.fileparamunit;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.AnnotationBasedArgumentsProvider;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.support.ParameterDeclarations;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.util.Preconditions;

/**
 * The list file argument provider will provide the absolute path to the
 * specified files in each of the files that exist in the specified folder
 * filtered by their file extensions.
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class ListFileArgumentsProvider extends AnnotationBasedArgumentsProvider<ListFileSource> {

    /**
     * Creates a new instance of the ListFileArgumentsProvider class.
     */
    public ListFileArgumentsProvider() {
        super();
    }

    @Deprecated
    @Override
    protected Stream<? extends Arguments> provideArguments(
            ExtensionContext context, ListFileSource listFileSource) {
        return provideArguments(null, context, listFileSource);
    }

    @Override
    protected Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters,
            ExtensionContext context, ListFileSource listFileSource) {

        boolean recurse = listFileSource.recurse();
        String fileExtension = Preconditions.notBlank(listFileSource.fileExtension(),
                "The fileExtension must be provided");

        List<Arguments> files = new ArrayList<>();

        // Load all of the directories
        for (String directoryName : listFileSource.directories()) {
            File directory = new File(directoryName);
            files.addAll(collect(recurse, fileExtension, directory));
        }

        // Load all of the resources
        for (String resource : listFileSource.resources()) {
            URL url = ListFileArgumentsProvider.class.getResource(resource);
            if (url == null) {
                throw new JUnitException("Failed to get resource [" + resource + "]");
            }

            File directory = urlToFile(url);
            files.addAll(collect(recurse, fileExtension, directory));
        }

        return files.stream();
    }

    File urlToFile(URL url) {
        // Given the context we are working in this exception should not happen based on
        // prior checks but it is unavailable to catch and rethrow
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new JUnitException("Failed to convert URL to File.", e);
        }
    }

    private List<Arguments> collect(boolean recurse, String fileExtension, File directory) {
        if (!directory.exists()) {
            throw new JUnitException("Directory [" + directory.getPath() + "] does not exist.");
        } else if (!directory.isDirectory()) {
            throw new JUnitException("File [" + directory.getPath() + "] is not a directory.");
        }

        List<Arguments> args = new ArrayList<>();

        // Loop through all of the files
        for (File file : directory.listFiles()) {
            // If we are recursing go into the directory
            if (recurse && file.isDirectory()) {
                args.addAll(collect(recurse, fileExtension, file));
                continue;
            }

            // If file matches the extension add it
            if (file.isFile() && file.getName().endsWith(fileExtension)) {
                args.add(Arguments.of(file.getAbsolutePath()));
            }
        }

        return args;
    }
}
