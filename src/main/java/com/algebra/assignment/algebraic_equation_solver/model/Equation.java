package com.algebra.assignment.algebraic_equation_solver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equation {

    private String equationId;
    private String originalEquation;
    private ExpressionTree expressionTree;

}
