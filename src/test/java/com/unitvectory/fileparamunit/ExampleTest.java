/*
 * Copyright 2015-2023 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */
package com.unitvectory.fileparamunit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * An example test.
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class ExampleTest {

    @ParameterizedTest
    @ListFileSource(resources = "/testData/", fileExtension = ".json", recurse = true)
    public void exampleTest(String fileName) {
        File file = new File(fileName);

        // This is where logic to use the content of the file would go
        assertTrue(file.exists());
    }
}
