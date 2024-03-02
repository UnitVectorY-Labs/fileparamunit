/*
 * Copyright 2024 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */
package com.unitvectory.fileparamunit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * The list file source annotation provides the ability to have a parameterized test case for the
 * files contained within a folder.
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(ListFileArgumentsProvider.class)
public @interface ListFileSource {

    /**
     * The path classpath resources to use as the sources of arguments; must not be empty unless
     * {@link #directories} is non-empty.
     * 
     * @return the list of resource paths
     */
    String[] resources() default {};

    /**
     * The path to use as the sources of arguments; must not be empty unless {@link #resources} is
     * non-empty.
     * 
     * @return the list of directory paths
     */
    String[] directories() default {};

    /**
     * Recursively go through the subdirectories listing all files
     * 
     * @return should recurse through subdirectories
     */
    boolean recurse() default false;

    /**
     * Only files with this file extension will be returned.
     * 
     * @return file extension filter
     */
    String fileExtension();
}
