package com.algebra.assignment.algebraic_equation_solver.service;

import com.algebra.assignment.algebraic_equation_solver.model.Equation;
import com.algebra.assignment.algebraic_equation_solver.model.ExpressionTree;
import com.algebra.assignment.algebraic_equation_solver.repository.EquationRepository;
import com.algebra.assignment.algebraic_equation_solver.utility.EquationParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EquationService {

    private final EquationRepository equationRepository;
    private final EquationParser equationParser;

    @Autowired
    public EquationService(EquationRepository equationRepository,
                           EquationParser equationParser) {
        this.equationRepository = equationRepository;
        this.equationParser = equationParser;
    }

    // Store a new equation
    public Equation storeEquation(String infix) {
        ExpressionTree expressionTree = equationParser.parse(infix);
        Equation equation = new Equation();
        equation.setOriginalEquation(infix);
        equation.setExpressionTree(expressionTree);
        return equationRepository.save(equation);
    }

    // Retrieve all equations
    public List<Equation> getAllEquations() {
        return equationRepository.findAll();
    }

    // Evaluate one equation by ID with variable map
    public double evaluateEquation(String equationId, Map<String, Double> variables) {
        Equation equation = equationRepository.findById(equationId)
                .orElseThrow(() -> new IllegalArgumentException("Equation not found with ID: " + equationId));

        return equation.getExpressionTree().evaluate(variables);
    }
}
