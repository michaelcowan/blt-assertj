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

package io.blt.test.assertj;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import static io.blt.test.AssertUtils.assertValidUtilityClass;
import static io.blt.test.assertj.AnnotationAssertions.assertHasAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.TargetFieldAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.TargetMethodAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.TargetTypeAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.TypeWithDefaultTargetAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.TypeWithDifferentAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.TypeWithNoAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.TypeWithValueTargetAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.fieldWithDefaultTargetAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.fieldWithDifferentAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.fieldWithNoAnnotations;
import static io.blt.test.assertj.testable.AnnotatedElements.fieldWithValueTargetAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.methodWithDefaultTargetAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.methodWithDifferentAnnotation;
import static io.blt.test.assertj.testable.AnnotatedElements.methodWithNoAnnotations;
import static io.blt.test.assertj.testable.AnnotatedElements.methodWithValueTargetAnnotation;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class AnnotationAssertionsTest {

    @Test
    void shouldBeValidUtilityClass() throws NoSuchMethodException {
        assertValidUtilityClass(AnnotationAssertions.class);
    }

    static Stream<Class<?>> assertHasAnnotationShouldThrowWhenTypeDoesntHaveAnnotation() {
        return Stream.of(TypeWithDifferentAnnotation.class, TypeWithNoAnnotation.class);
    }

    @ParameterizedTest
    @MethodSource
    void assertHasAnnotationShouldThrowWhenTypeDoesntHaveAnnotation(Class<?> type) {
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertHasAnnotation(type, TargetTypeAnnotation.class))
                .withMessage("Cannot find annotation of type TargetTypeAnnotation");
    }

    @Test
    void assertHasAnnotationShouldReturnObjectAssertForTypeWithFailCase() {
        var objectAssert = assertHasAnnotation(TypeWithDefaultTargetAnnotation.class, TargetTypeAnnotation.class);

        assertThatExceptionOfType(AssertionFailedError.class)
                .isThrownBy(() -> objectAssert
                        .extracting(TargetTypeAnnotation::name)
                        .isEqualTo("wrong name"))
                .withMessage(
                        System.lineSeparator() + "expected: \"wrong name\"" +
                        System.lineSeparator() + " but was: \"type annotation default name\"");
    }

    static Stream<Arguments> assertHasAnnotationShouldReturnObjectAssertForTypeWithSuccessCase() {
        return Stream.of(
                Arguments.arguments(TypeWithDefaultTargetAnnotation.class, "type annotation default name"),
                Arguments.arguments(TypeWithValueTargetAnnotation.class, "type annotation value name"));
    }

    @ParameterizedTest
    @MethodSource
    void assertHasAnnotationShouldReturnObjectAssertForTypeWithSuccessCase(Class<?> type, String expected) {
        var objectAssert = assertHasAnnotation(type, TargetTypeAnnotation.class);

        assertThatNoException()
                .isThrownBy(() -> objectAssert
                        .extracting(TargetTypeAnnotation::name)
                        .isEqualTo(expected));
    }

    public static Stream<Method> assertHasAnnotationShouldThrowWhenMethodDoesntHaveAnnotation() {
        return Stream.of(methodWithNoAnnotations, methodWithDifferentAnnotation);
    }

    @ParameterizedTest
    @MethodSource
    void assertHasAnnotationShouldThrowWhenMethodDoesntHaveAnnotation(Method method) {
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertHasAnnotation(method, TargetMethodAnnotation.class))
                .withMessage("Cannot find annotation of type TargetMethodAnnotation");
    }

    @Test
    void assertHasAnnotationShouldReturnObjectAssertForMethodWithFailCase() {
        var objectAssert = assertHasAnnotation(methodWithDefaultTargetAnnotation, TargetMethodAnnotation.class);

        assertThatExceptionOfType(AssertionFailedError.class)
                .isThrownBy(() -> objectAssert
                        .extracting(TargetMethodAnnotation::name)
                        .isEqualTo("wrong name"))
                .withMessage(
                        System.lineSeparator() + "expected: \"wrong name\"" +
                        System.lineSeparator() + " but was: \"method annotation default name\"");
    }

    static Stream<Arguments> assertHasAnnotationShouldReturnObjectAssertForMethodWithSuccessCase() {
        return Stream.of(
                Arguments.arguments(methodWithDefaultTargetAnnotation, "method annotation default name"),
                Arguments.arguments(methodWithValueTargetAnnotation, "method annotation value name"));
    }

    @ParameterizedTest
    @MethodSource
    void assertHasAnnotationShouldReturnObjectAssertForMethodWithSuccessCase(Method method, String expected) {
        var objectAssert = assertHasAnnotation(method, TargetMethodAnnotation.class);

        assertThatNoException()
                .isThrownBy(() -> objectAssert
                        .extracting(TargetMethodAnnotation::name)
                        .isEqualTo(expected));
    }

    public static Stream<Field> assertHasAnnotationShouldThrowWhenFieldDoesntHaveAnnotation() {
        return Stream.of(fieldWithNoAnnotations, fieldWithDifferentAnnotation);
    }

    @ParameterizedTest
    @MethodSource
    void assertHasAnnotationShouldThrowWhenFieldDoesntHaveAnnotation(Field field) {
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertHasAnnotation(field, TargetFieldAnnotation.class))
                .withMessage("Cannot find annotation of type TargetFieldAnnotation");
    }

    @Test
    void assertHasAnnotationShouldReturnObjectAssertForFieldWithFailCase() {
        var objectAssert = assertHasAnnotation(fieldWithDefaultTargetAnnotation, TargetFieldAnnotation.class);

        assertThatExceptionOfType(AssertionFailedError.class)
                .isThrownBy(() -> objectAssert
                        .extracting(TargetFieldAnnotation::name)
                        .isEqualTo("wrong name"))
                .withMessage(
                        System.lineSeparator() + "expected: \"wrong name\"" +
                        System.lineSeparator() + " but was: \"field annotation default name\"");
    }

    static Stream<Arguments> assertHasAnnotationShouldReturnObjectAssertForFieldWithSuccessCase() {
        return Stream.of(
                Arguments.arguments(fieldWithDefaultTargetAnnotation, "field annotation default name"),
                Arguments.arguments(fieldWithValueTargetAnnotation, "field annotation value name"));
    }

    @ParameterizedTest
    @MethodSource
    void assertHasAnnotationShouldReturnObjectAssertForFieldWithSuccessCase(Field field, String expected) {
        var objectAssert = assertHasAnnotation(field, TargetFieldAnnotation.class);

        assertThatNoException()
                .isThrownBy(() -> objectAssert
                        .extracting(TargetFieldAnnotation::name)
                        .isEqualTo(expected));
    }

}
