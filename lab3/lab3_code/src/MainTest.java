import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @ParameterizedTest(name = "{index} => age={0}, weight={1}, isHighRisk={2}, hasAllergy={3}, expected={4}")
    @MethodSource("calculateDosageCasesSelectedRows")
    void calculateDosageParameterized(int age, double weight, boolean isHighRisk, boolean hasAllergy, double expected) {
        // Assuming the method is in a class named Main
        assertEquals(expected, Main.calculateDosage(age, weight, isHighRisk, hasAllergy), 0.0001);
    }

    static Stream<Arguments> calculateDosageCasesSelectedRows() {
        return Stream.of(
                // Row 6: A=T, B=F, C=T, D=F (Predicate: TRUE)
                Arguments.of(70, 45.0, false, false, 36.0),

                // Row 14: A=F, B=F, C=T, D=F (Predicate: FALSE)
                Arguments.of(30, 45.0, false, false, 57.0),

                // Row 10: A=F, B=T, C=T, D=F (Predicate: TRUE)
                Arguments.of(40, 48.0, true, false, 38.4),

                // Row 12: A=F, B=T, C=F, D=F (Predicate: FALSE)
                Arguments.of(25, 60.0, true, false, 74.5),

                // Row 9: A=F, B=T, C=T, D=T (Predicate: FALSE)
                Arguments.of(50, 45.0, true, true, 59.0)
        );
    }
}