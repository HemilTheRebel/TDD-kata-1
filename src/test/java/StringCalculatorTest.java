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

    @Test
    void add_allows_newlines_as_separators() {
        assertEquals(StringCalculator.add("1\n2,3"), 6);
    }

    @Test
    void add_throws_illegal_argument_exception_when_string_ends_with_newline() {
        assertThrows(IllegalArgumentException.class, () -> StringCalculator.add("1,2,\n"));
    }

    @Test
    void ensure_test_trims_input() {
        assertThrows(IllegalArgumentException.class, () -> StringCalculator.add("\t\n\n"));
    }

    @Test
    void add_splits_using_custom_delimiters() {
        assertEquals(StringCalculator.add("//;\n1;2"), 3);
    }

    @Test
    void add_throws_illegal_argument_exception_when_end_line_is_not_found_after_slash_slash() {
        assertThrows(IllegalArgumentException.class, () -> StringCalculator.add("//abc"));
    }

    @Test
    void calling_add_with_negative_number_should_throw_NegativesNotAllowed_containing_negative_numbers() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> StringCalculator.add("1,2,-3"));
        assertEquals("Negatives not allowed. Negative numbers are: -3", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> StringCalculator.add("1,2,-3,-4"));
        assertEquals("Negatives not allowed. Negative numbers are: -3 -4", exception.getMessage());
    }

    @Test
    void numbers_above_1000_should_not_be_counted() {
        assertEquals(StringCalculator.add("1, 1001, 2, 4"), 7);
    }

    @Test
    void delimiters_inside_square_brackets_can_be_of_any_length() {
        assertEquals(StringCalculator.add("//[abc]\n1,2\n3,4abc5"), 15);
        assertEquals(StringCalculator.add("//[abc][def]\n1def2\n3,4abc5"), 15);
        assertEquals(StringCalculator.add("//[*][%]\n1*2%3"), 6);
    }

    @Test
    void delimiters_without_left_square_bracket_throws_illegal_argument_exception() {
        assertThrows(IllegalArgumentException.class, () -> StringCalculator.add("//abc]\n1,2\n3,4abc5"));
        assertThrows(IllegalArgumentException.class, () -> StringCalculator.add("//[a][b]c]\n1,2\n3,4abc5"));
    }
}