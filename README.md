# blt-assertj
> A selection of [AssertJ](http://joel-costigliola.github.io/assertj) utilities that pull in minimal dependencies

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/michaelcowan/blt-assertj/tree/master.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/michaelcowan/blt-assertj/tree/master)

# Installation

## Maven

The library is available via [Maven Central](https://central.sonatype.com/artifact/io.blt/blt-assertj)

e.g., to add the AssertJ library to your dependencies:

```xml
<dependency>
    <groupId>io.blt</groupId>
    <artifactId>blt-assertj</artifactId>
    <version>1.0.0</version>
</dependency>
```

# Documentation

## API Docs

[API docs](https://michaelcowan.github.io/blt-assertj/apidocs) are available for the latest release of the library.

## Examples

### `AnnotationAssertions`

Assertion factory methods that allow testing the annotations of various types.

Allow asserting that an annotation is present and also to perform assertions on the annotation itself:

```java
@Test
void isAnnotatedAsTransactionalWithNoRollbackForException() {
    assertHasAnnotation(NotificationPublisher.class, Transactional.class)
        .extracting(Transactional::noRollbackFor)
        .isEqualTo(Exception.class);
}
```

Similarly, for `Method`:

```java
@ParameterizedTest
@MethodSource
void isAnnotatedAsTransactionalWithNoRollbackForException(Method method) throws Exception {
    assertHasAnnotation(method, Transactional.class)
        .extracting(Transactional::noRollbackFor)
        .isEqualTo(Exception.class);
}
```
