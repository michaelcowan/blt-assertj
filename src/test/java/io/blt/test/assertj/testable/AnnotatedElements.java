/*
 * MIT License
 *
 * Copyright (c) 2023 Michael Cowan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.blt.test.assertj.testable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Holds testable elements for annotation testing
 */
public final class AnnotatedElements {

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TargetTypeAnnotation {
        String name() default "type annotation default name";
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DifferentTypeAnnotation {}

    @TargetTypeAnnotation
    public static class TypeWithDefaultTargetAnnotation {}

    @TargetTypeAnnotation(name = "type annotation value name")
    public static class TypeWithValueTargetAnnotation {}

    @DifferentTypeAnnotation
    public static class TypeWithDifferentAnnotation {}

    public static class TypeWithNoAnnotation {}

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TargetMethodAnnotation {
        String name() default "method annotation default name";
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DifferentMethodAnnotation {}

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TargetFieldAnnotation {
        String name() default "field annotation default name";
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DifferentFieldAnnotation {}

    public static class MethodTests {

        @TargetFieldAnnotation
        public String fieldWithDefaultTargetAnnotation;

        @TargetFieldAnnotation(name = "field annotation value name")
        public String fieldWithValueTargetAnnotation;

        @DifferentFieldAnnotation
        public String fieldWithDifferentAnnotation;

        public String fieldWithNoAnnotations;

        @TargetMethodAnnotation
        public void methodWithDefaultTargetAnnotation() {}

        @TargetMethodAnnotation(name = "method annotation value name")
        public void methodWithValueTargetAnnotation() {}

        @DifferentMethodAnnotation
        public void methodWithDifferentAnnotation() {}

        public void methodWithNoAnnotations() {}

        private static Method method(String name) {
            try {
                return MethodTests.class.getMethod(name);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        private static Field field(String name) {
            try {
                return MethodTests.class.getField(name);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Method methodWithDefaultTargetAnnotation = MethodTests.method("methodWithDefaultTargetAnnotation");
    public static Method methodWithValueTargetAnnotation = MethodTests.method("methodWithValueTargetAnnotation");
    public static Method methodWithDifferentAnnotation = MethodTests.method("methodWithDifferentAnnotation");
    public static Method methodWithNoAnnotations = MethodTests.method("methodWithNoAnnotations");
    public static Field fieldWithDefaultTargetAnnotation = MethodTests.field("fieldWithDefaultTargetAnnotation");
    public static Field fieldWithValueTargetAnnotation = MethodTests.field("fieldWithValueTargetAnnotation");
    public static Field fieldWithDifferentAnnotation = MethodTests.field("fieldWithDifferentAnnotation");
    public static Field fieldWithNoAnnotations = MethodTests.field("fieldWithNoAnnotations");

}
