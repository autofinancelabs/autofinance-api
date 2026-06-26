package com.autofinance.api;

import com.autofinance.api.shared.domain.exceptions.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Anti-drift guard: every {@link ErrorCode} enum value across all contexts must be documented in
 * {@code docs/architecture/error-codes.md} (the frontend's contract). Adding a new code — or a whole new
 * context's catalog — fails this test until the doc is updated. Pure JUnit (no Spring context).
 */
class ErrorCodesDocumentedTest {

    private static final Path DOC = Path.of("docs/architecture/error-codes.md");

    @Test
    void everyErrorCodeIsDocumented() throws IOException, ClassNotFoundException {
        String doc = Files.readString(DOC);
        List<String> codes = allErrorCodes();

        assertThat(codes)
                .as("the classpath scan should find the ErrorCode enums (so the doc can't pass vacuously)")
                .hasSizeGreaterThanOrEqualTo(15);

        List<String> undocumented = codes.stream().filter(code -> !doc.contains(code)).toList();
        assertThat(undocumented)
                .as("codes missing from " + DOC)
                .isEmpty();
    }

    /** All values of every enum implementing {@link ErrorCode} under the application base package. */
    private static List<String> allErrorCodes() throws ClassNotFoundException {
        var scanner = new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return beanDefinition.getMetadata().isIndependent();
            }
        };
        scanner.addIncludeFilter(new AssignableTypeFilter(ErrorCode.class));

        List<String> codes = new ArrayList<>();
        for (var candidate : scanner.findCandidateComponents("com.autofinance.api")) {
            Class<?> type = Class.forName(candidate.getBeanClassName());
            if (!type.isEnum()) {
                continue;
            }
            for (Object constant : type.getEnumConstants()) {
                codes.add(((ErrorCode) constant).code());
            }
        }
        return codes;
    }
}
