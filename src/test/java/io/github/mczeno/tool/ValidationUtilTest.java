package io.github.mczeno.tool;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ValidationUtilTest
 *
 * @author Chongming Zhou
 */
public class ValidationUtilTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void validate() {
        TestObject testObject = new TestObject();
        Set<ConstraintViolation<TestObject>> violations = ValidationUtil.validate(testObject);
        System.out.println(violations);
    }

    @Test
    public void validateFirst() {
        TestObject testObject = new TestObject();
        ConstraintViolation<TestObject> constraintViolation = ValidationUtil.validateFirst(testObject);
        System.out.println(constraintViolation);
    }

    @Test
    public void validateAndGetMessages() {
        TestObject testObject = new TestObject();
        Set<String> messages = ValidationUtil.validateAndGetMessages(testObject);
        System.out.println(messages);
        assertEquals(2, messages.size());
    }

    @Test
    public void validateAndThrow_NoViolations_ShouldNotThrowException() {
        TestObject testObject = new TestObject();
        testObject.setId(1L);
        testObject.setCode("test");
        assertDoesNotThrow(() -> ValidationUtil.validateAndThrow(testObject));
    }

    @Test
    public void validateAndThrow_WithViolations_ShouldThrowValidationException() {
        TestObject testObject = new TestObject();
        assertThrows(ValidationException.class, () -> ValidationUtil.validateAndThrow(testObject));
    }

    @Test
    public void isValid() {
        TestObject testObject = new TestObject();
        boolean valid = ValidationUtil.isValid(testObject);
        System.out.println(valid);
        assertFalse(valid);
    }

}