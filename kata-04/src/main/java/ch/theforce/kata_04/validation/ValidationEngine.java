package ch.theforce.kata_04.validation;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Validation engine that executes validation rules in parallel.
 * This component discovers all {@link ValidationRule} implementations
 * for a given type and executes them concurrently using virtual threads.
 * It collects validation errors from all rules and returns a consolidated result.
 */
@Component
@AllArgsConstructor
public class ValidationEngine {

    private final ApplicationContext context;

    /**
     * Runs all validation rules for the given target object in parallel.
     * This method discovers all {@link ValidationRule} implementations registered
     * in the Spring context for the target's type and executes them concurrently
     * using virtual threads. Validation errors from all rules are collected and
     * returned as a consolidated result.
     *
     * @param <T>    the type of the target object being validated
     * @param target the object to validate
     * @return a {@link ValidationResult} containing either the valid target or
     * a list of validation errors from all executed rules
     */
    @SuppressWarnings("unchecked")
    public <T> ValidationResult<T> runParallel(T target) {
        String[] beanNames = context.getBeanNamesForType(
                ResolvableType.forClassWithGenerics(ValidationRule.class, target.getClass())
        );

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Future<ValidationResult<T>>> futures = Arrays.stream(beanNames)
                    .map(name -> (ValidationRule<T>) context.getBean(name))
                    .map(rule -> executor.submit(() -> rule.validate(target)))
                    .toList();

            List<ValidationError> allErrors = new ArrayList<>();
            for (var future : futures) {
                try {
                    if (future.get() instanceof Invalid<T>(var errors)) {
                        allErrors.addAll(errors);
                    }
                } catch (Exception e) {
                    allErrors.add(new ValidationError("system", "Unexpected error: " + e.getMessage()));
                }
            }

            return allErrors.isEmpty()
                    ? ValidationResult.ok(target)
                    : new Invalid<>(allErrors);
        }
    }
}






