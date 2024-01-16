package com.example.gui_project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PolishCalcTest {

    private final PolishCalc polishCalc = new PolishCalc();

    @Test
    void testValidExpression() {
        String input = "3 + 4 * 2 / ( 1 - 5 ) ^ 2";

        String result = polishCalc.Res(input);

        assertEquals("3.5", result);
    }

    @Test
    void testExpressionWithInvalidOperand() {
        String input = "2 + abc * 3";

        String result = polishCalc.Res(input);

        assertEquals("Invalid expression", result);
    }

    @Test
    void testExpressionWithDivisionByZero() {
        String input = "1 / 0";

        String result = polishCalc.Res(input);

        assertEquals("Division by zero", result);
    }

    @Test
    void testEmptyExpression() {
        String input = "";

        String result = polishCalc.Res(input);

        assertEquals("Invalid expression", result);
    }

    // Добавьте другие тесты в зависимости от ваших требований
}
