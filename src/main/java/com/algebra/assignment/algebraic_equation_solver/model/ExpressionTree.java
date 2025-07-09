package com.algebra.assignment.algebraic_equation_solver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionTree {

    private Node root;

    // Reconstructs the infix expression by traversing the tree
    public String reconstructInfix() {
        return reconstructInfix(root);
    }

    private String reconstructInfix(Node node) {
        if (node == null) {
            return "";
        }

        if (node.isOperand()) {
            return node.getValue();
        }

        String left = reconstructInfix(node.getLeft());
        String right = reconstructInfix(node.getRight());

        return "(" + left + " " + node.getValue() + " " + right + ")";
    }

    // Evaluates the expression tree with given variable values
    public double evaluate(Map<String, Double> variables) {
        return evaluateNode(root, variables);
    }

    private double evaluateNode(Node node, Map<String, Double> variables) {
        if (node.isOperand()) {
            String val = node.getValue();
            try {
                return Double.parseDouble(val);
            } catch (NumberFormatException e) {
                if (variables.containsKey(val)) {
                    return variables.get(val);
                } else {
                    throw new IllegalArgumentException("Missing variable: " + val);
                }
            }
        }

        double leftVal = evaluateNode(node.getLeft(), variables);
        double rightVal = evaluateNode(node.getRight(), variables);

        switch (node.getValue()) {
            case "+":
                return leftVal + rightVal;
            case "-":
                return leftVal - rightVal;
            case "*":
                return leftVal * rightVal;
            case "/":
                return leftVal / rightVal;
            case "^":
                return Math.pow(leftVal, rightVal);
            default:
                throw new UnsupportedOperationException("Unknown operator: " + node.getValue());
        }
    }
}
