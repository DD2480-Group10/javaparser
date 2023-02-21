/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2023 The JavaParser Team.
 *
 * This file is part of JavaParser.
 *
 * JavaParser can be used either under the terms of
 * a) the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * b) the terms of the Apache License
 *
 * You should have received a copy of both licenses in LICENCE.LGPL and
 * LICENCE.APACHE. Please refer to those files for details.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 */

package com.github.javaparser.ast.expr;

import com.github.javaparser.CodeCoverage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignExprTest {
    /*
     * Coverage tool
     */
    private static String[] testInfoArr;
    private static int testIndex;

    @BeforeAll
    public static void beforeAllTest() {
        CodeCoverage.clearFlagArr();
        testInfoArr =  new String[100];
        testIndex = 0;
    }

    @AfterEach
    public void afterEachTest() {
        String testInfo = CodeCoverage.getTestInfo();
        if(testInfo != "") {
            testInfoArr[testIndex] = testInfo;
        }
        CodeCoverage.clearFlagArr();
        testIndex++;


    }

    @AfterAll
    public static void afterAllTests(){
        try {
            CodeCoverage.writeBranchCoverage("AssignExpr.txt",testInfoArr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void convertOperator() {
        assertEquals(BinaryExpr.Operator.PLUS, AssignExpr.Operator.PLUS.toBinaryOperator().get());
    }
}
