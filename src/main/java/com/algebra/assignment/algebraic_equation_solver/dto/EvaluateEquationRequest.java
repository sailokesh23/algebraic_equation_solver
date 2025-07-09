package com.algebra.assignment.algebraic_equation_solver.dto;

import lombok.Data;

import java.util.Map;

@Data
public class EvaluateEquationRequest {
    private Map<String, Double> variables;
}
