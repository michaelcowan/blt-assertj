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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.internal.Failures;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Assertion factory methods that allow testing the annotations of various types.
 * <p>
 * Each method is a static factory for an annotation specific assertion object
 * i.e. each method returns {@code ObjectAssert<T extends Annotation>}.
 * </p>
 * <p>
 * e.g.
 * <pre>{@code
 * @Test
 * void isAnnotatedAsTransactionalWithNoRollbackForException() {
 *     assertHasAnnotation(NotificationPublisher.class, Transactional.class)
 *             .extracting(Transactional::noRollbackFor)
 *             .isEqualTo(Exception.class);
 * }
 * }</pre>
 * </p>
 */
public final class AnnotationAssertions {

    private AnnotationAssertions() {
        throw new IllegalAccessError("Utility class should be accessed statically and never constructed");
    }

    /**
     * Asserts that a {@code Method} is annotated with a given annotation.
     * <p>
     * If present, an assertion object is returned for the found annotation instance, else the test fails.
     * </p>
     * e.g.
     * <pre>{@code
     * @Test
     * void isAnnotatedAsTransactionalWithNoRollbackForException() throws Exception {
     *     var method = NotificationPublisher.class.getMethod("sendNotification");
     *
     *     assertHasAnnotation(method, Transactional.class)
     *             .extracting(Transactional::noRollbackFor)
     *             .isEqualTo(Exception.class);
     * }
     * }</pre>
     *
     * @param method     a {@code Method} to test for the presence of {@code annotation}
     * @param annotation the expected {@code Annotation} type
     * @param <T>        type of {@code Annotation}
     * @return an assertion object for the found annotation i.e. {@code ObjectAssert<T extends Annotation>}
     */
    public static <T extends Annotation> ObjectAssert<T> assertHasAnnotation(Method method, Class<T> annotation) {
        return assertThat(findAnnotationOfTypeOrFail(method.getAnnotations(), annotation));
    }

    /**
     * Asserts that a {@code Class} is annotated with a given annotation.
     * <p>
     * If present, an assertion object is returned for the found annotation instance, else the test fails.
     * </p>
     * e.g.
     * <pre>{@code
     * @Test
     * void isAnnotatedAsTransactionalWithNoRollbackForException() {
     *     assertHasAnnotation(NotificationPublisher.class, Transactional.class)
     *             .extracting(Transactional::noRollbackFor)
     *             .isEqualTo(Exception.class);
     * }
     * }</pre>
     *
     * @param clazz      a {@code Class} to test for the presence of {@code annotation}
     * @param annotation the expected {@code Annotation} type
     * @param <T>        type of {@code Annotation}
     * @return an assertion object for the found annotation i.e. {@code ObjectAssert<T extends Annotation>}
     */
    public static <T extends Annotation> ObjectAssert<T> assertHasAnnotation(Class<?> clazz, Class<T> annotation) {
        return assertThat(findAnnotationOfTypeOrFail(clazz.getAnnotations(), annotation));
    }

    private static <T extends Annotation> T findAnnotationOfTypeOrFail(Annotation[] annotations, Class<T> type) {
        var instance = Arrays.stream(annotations)
                             .filter(a -> a.annotationType().equals(type))
                             .findFirst()
                             .orElseThrow(() -> Failures.instance().failure(
                                     "Cannot find annotation of type " + type.getSimpleName()));

        return type.cast(instance);
    }

}
