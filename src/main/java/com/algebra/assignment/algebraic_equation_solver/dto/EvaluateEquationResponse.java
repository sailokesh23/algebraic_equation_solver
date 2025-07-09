package com.algebra.assignment.algebraic_equation_solver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class EvaluateEquationResponse {
    private String equationId;
    private double result;
    private Map<String, Double> variables;
}
