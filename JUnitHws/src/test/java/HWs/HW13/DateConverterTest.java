package HWs.HW13;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.format.DateTimeParseException;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DateConverterTest {

    @BeforeAll
    static void start() {
        Locale.setDefault(Locale.US);
    }

    @Test
    void checkConvertToFormatyyyyMMdd() {
        String outputResult = DateConverter.convertDate("2013-01-26", "yyyy-MM-dd", "yyyy-MM-dd");
        assertEquals(outputResult, "2013-01-26");
    }

    @Test
    void checkConvertToFormatddMMMMyyyy() {
        String outputResult = DateConverter.convertDate("2013-01-26", "yyyy-MM-dd", "dd MMMM yyyy");
        assertEquals(outputResult, "26 January 2013");
    }

    @ParameterizedTest
    @MethodSource("input")
    void checkConvertFromAllFormats(String inputDate, String inputFormat) {
        String outputResult = DateConverter.convertDate(inputDate, inputFormat, "dd MMM yyyy");
        assertEquals(outputResult, "26 Jan 2013");
    }

    static Stream<Arguments> input() {
        return Stream.of(
                Arguments.arguments("26 January 2013", "dd MMMM yyyy"),
                Arguments.arguments("26-01-2013", "dd-MM-yyyy"),
                Arguments.arguments("01/26/2013", "MM/dd/yyyy"),
                Arguments.arguments("2013-01-26", "yyyy-MM-dd"),
                Arguments.arguments("26 Jan 2013", "dd MMM yyyy")
        );
    }

    @Test
    void checkWrongDateForConvert() {
        assertThrows(DateTimeParseException.class, () -> DateConverter.convertDate("2013.01.26", "yyyy-MM-dd", "dd MMM yyyy"));
    }

    @Test
    void checkWrongInputFormat() {
        assertThrows(DateTimeParseException.class, () -> DateConverter.convertDate("2013-01-26", "dd MMMM yyyy", "dd MMM yyyy"));
    }

    @Test
    void checkIncorrectInputFormat() {
        assertThrows(IllegalArgumentException.class, () -> DateConverter.convertDate("2013-01-26", "nbvnvb", "dd MMM yyyy"));
    }

    @Test
    void checkIncorrectOutputFormat() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> DateConverter.convertDate("2013-01-26", "yyyy-MM-dd", "asdasd"));
    }
}