package com.algebra.assignment.algebraic_equation_solver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    private String value;
    private Node left;
    private Node right;

    public boolean isOperator() {
        return "+-*/^".contains(value);
    }

    public boolean isOperand() {
        return !isOperator();
    }
}
