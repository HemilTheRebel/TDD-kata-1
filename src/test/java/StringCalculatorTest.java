import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorTest {
    /**
     * I know about Java convention of using camelCase.
     * But the method names would have been unreadable
     * if I would have used camelCase.
     */
    @Test
    void addition_of_empty_string_is_0() {
        assertEquals(StringCalculator.add(""), 0);
    }

    @Test
    void add_adds_comma_separated_numbers() {
        assertEquals(StringCalculator.add("1,2"), 3);
    }

    @Test
    void add_throws_number_format_exception_if_string_does_not_contain_valid_numbers() {
        assertThrows(NumberFormatException.class, () -> { StringCalculator.add("abc,2"); });
    }

    @Test
    void add_throws_illegal_argument_exception_if_string_contains_1_number_only() {
        assertThrows(IllegalStateException.class, () -> { StringCalculator.add("1"); });
    }

    @Test
    void add_throws_illegal_argument_exception_if_string_contains_more_than_2_numbers() {
        assertThrows(IllegalStateException.class, () -> { StringCalculator.add("1,2,3"); });
    }
}