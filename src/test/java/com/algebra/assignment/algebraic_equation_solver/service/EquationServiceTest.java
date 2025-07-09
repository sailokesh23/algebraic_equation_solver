package com.algebra.assignment.algebraic_equation_solver.service;

import com.algebra.assignment.algebraic_equation_solver.model.Equation;
import com.algebra.assignment.algebraic_equation_solver.repository.InMemoryEquationRepository;
import com.algebra.assignment.algebraic_equation_solver.utility.EquationParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EquationServiceTest {

    private EquationService equationService;

    @BeforeEach
    void setup() {
        InMemoryEquationRepository repository = new InMemoryEquationRepository();
        EquationParser parser = new EquationParser();
        equationService = new EquationService(repository, parser);
    }

    @Test
    void testStoreEquation() {
        Equation equation = equationService.storeEquation("3 * x + 2");
        assertNotNull(equation.getEquationId());
        assertEquals("3 * x + 2", equation.getOriginalEquation());
    }

    @Test
    void testRetrieveEquations() {
        equationService.storeEquation("x + y");
        equationService.storeEquation("x - y");
        List<Equation> all = equationService.getAllEquations();
        assertEquals(2, all.size());
    }

    @Test
    void testEvaluateEquation() {
        Equation equation = equationService.storeEquation("2 * x + 3");
        double result = equationService.evaluateEquation(
                equation.getEquationId(),
                Map.of("x", 4.0)
        );
        assertEquals(11.0, result);
    }

    @Test
    void testEvaluateMissingVariable() {
        Equation equation = equationService.storeEquation("x + y");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> equationService.evaluateEquation(
                        equation.getEquationId(),
                        Map.of("x", 2.0)  // Missing y!
                ));
        assertTrue(exception.getMessage().contains("Missing variable"));
    }

    @Test
    void testComplexEquation() {
        Equation equation = equationService.storeEquation("(x + y) * (z - 2)");
        double result = equationService.evaluateEquation(
                equation.getEquationId(),
                Map.of("x", 1.0, "y", 2.0, "z", 5.0)
        );
        assertEquals(9.0, result);
    }
}
