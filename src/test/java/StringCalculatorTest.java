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
        assertThrows(NumberFormatException.class, () -> StringCalculator.add("abc,2"));
    }

    @Test
    void add_throws_illegal_argument_exception_if_string_contains_1_number_only() {
        assertThrows(IllegalArgumentException.class, () -> StringCalculator.add("1"));
    }

    @Test
    void add_throws_illegal_argument_exception_if_string_ends_with_comma() {
        assertThrows(IllegalArgumentException.class, () -> StringCalculator.add("1,2,"));
    }

    @Test
    void ensure_add_trims_each_number() {
        assertEquals(StringCalculator.add("   1  , 2  ,  3   "), 6);
    }

    @Test
    void add_sums_all_arguments_when_more_than_2_numbers_are_supplied() {
        assertEquals(StringCalculator.add("1,2,3"), 6);
        assertEquals(StringCalculator.add("1,2,3,4"), 10);
        assertEquals(StringCalculator.add("1,2,3,4,5"), 15);
    }
}