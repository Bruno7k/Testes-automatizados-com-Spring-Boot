package br.com.curso.testes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    public void testSum() {
        Calculator calculator = new Calculator();
        assertEquals(4, calculator.sum(2, 2));
    }

}