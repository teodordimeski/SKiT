import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MainTest {

    @ParameterizedTest(name = "{index} => temps={0}, expected=''{1}''")
    @MethodSource("analyzeTemperaturesCasesPPC")
    void analyzeTemperaturesParameterizedPrimepathCoverage(int[] temps, String expected) {
        assertEquals(expected, Main.analyzeTemperatures(temps));
    }

    static Stream<Arguments> analyzeTemperaturesCasesPPC() {
        return Stream.of(
                Arguments.of(new int[] {}, "No warm days."),
                Arguments.of(new int[] {10, 11, -70, 100, 15}, "Invalid temperatures detected."),
                Arguments.of(new int[] {-100}, "Invalid temperatures detected."),
                Arguments.of(new int[] {30, 40}, "All days were warm."),
                Arguments.of(new int[] {10, 11}, "No warm days."),
                Arguments.of(new int[] {10, 30}, "Some days were warm.")
        );
    }

    @ParameterizedTest(name = "{index} => temps={0}, expected=''{1}''")
    @MethodSource("analyzeTemperaturesCasesEPC")
    void analyzeTemperaturesEdgePairCoverage(int[] temps, String expected) {
        assertEquals(expected, Main.analyzeTemperatures(temps));
    }

    static Stream<Arguments> analyzeTemperaturesCasesEPC() {
        return Stream.of(
                Arguments.of(new int[] {}, "No warm days."),
                Arguments.of(new int[] {100}, "Invalid temperatures detected."),
                Arguments.of(new int[] {40}, "All days were warm."),
                Arguments.of(new int[] {10, 30}, "Some days were warm.")
        );
    }
}