package org.kostpost;

import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckEquationTest {

    @Test
    void testCheckEquationValid() {
        CheckEquation service = new CheckEquation();
        String validEquation = "(1+2)*2=5";
        assertNotNull(service.checkEquation(validEquation));
    }

    @Test
    void testCheckEquationInvalid() {
        CheckEquation service = new CheckEquation();
        String invalidEquation = "1+2)*2=5";
        assertNull(service.checkEquation(invalidEquation));
    }

    @Test
    void testFormatEquation() {
        CheckEquation service = new CheckEquation();
        String formatted = CheckEquation.formatEquation(" (1 + 2) * 2 = 5 ");
        assertEquals("(1+2)*2=5", formatted);
    }

    @Test
    void testIsValidDecimalValid() {
        CheckEquation service = new CheckEquation();
        assertTrue(service.isValidDecimal("3.14"));
    }

    @Test
    void testIsValidDecimalInvalid() {
        CheckEquation service = new CheckEquation();
        assertFalse(service.isValidDecimal("3."));
    }

    @Test
    void testCheckBracketsValid() {
        CheckEquation service = new CheckEquation();
        assertTrue(service.checkBrackets("(1+2)*2=5"));
    }

    @Test
    void testCheckBracketsInvalid() {
        CheckEquation service = new CheckEquation();
        assertFalse(service.checkBrackets("(1+2))*2=5"));
    }

    @Test
    void testIsOperatorSequenceValidValid() {
        CheckEquation service = new CheckEquation();
        assertTrue(service.isOperatorSequenceValid("x-1=0"));
    }

    @Test
    void testIsOperatorSequenceValidInvalid() {
        CheckEquation service = new CheckEquation();
        assertFalse(service.isOperatorSequenceValid("x--1=0"));
    }
}
