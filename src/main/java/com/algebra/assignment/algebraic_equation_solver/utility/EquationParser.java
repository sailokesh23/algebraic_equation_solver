package com.algebra.assignment.algebraic_equation_solver.utility;

import com.algebra.assignment.algebraic_equation_solver.model.ExpressionTree;
import com.algebra.assignment.algebraic_equation_solver.model.Node;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EquationParser {

    private static final Set<String> OPERATORS = Set.of("+", "-", "*", "/", "^");
    private static final Map<String, Integer> PRECEDENCE = Map.of(
            "+", 1,
            "-", 1,
            "*", 2,
            "/", 2,
            "^", 3
    );

    // Parses infix string -> builds ExpressionTree
    public ExpressionTree parse(String infix) {
        List<String> tokens = tokenize(infix);
        List<String> postfix = infixToPostfix(tokens);
        Node root = buildExpressionTree(postfix);
        return new ExpressionTree(root);
    }

    // 1. Tokenize input string
    private List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();

        for (char ch : input.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                continue;
            }
            if (isOperator(ch) || ch == '(' || ch == ')') {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                tokens.add(Character.toString(ch));
            } else {
                token.append(ch);
            }
        }
        if (token.length() > 0) {
            tokens.add(token.toString());
        }
        return tokens;
    }

    // 2. Infix to Postfix (Shunting Yard)
    private List<String> infixToPostfix(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();

        for (String token : tokens) {
            if (isOperator(token)) {
                while (!stack.isEmpty() && isOperator(stack.peek())
                        && hasHigherPrecedence(stack.peek(), token)) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                if (stack.isEmpty() || !stack.pop().equals("(")) {
                    throw new IllegalArgumentException("Mismatched parentheses.");
                }
            } else {
                output.add(token);
            }
        }

        while (!stack.isEmpty()) {
            String op = stack.pop();
            if (op.equals("(") || op.equals(")")) {
                throw new IllegalArgumentException("Mismatched parentheses.");
            }
            output.add(op);
        }
        return output;
    }

    // 3. Build Expression Tree from postfix
    private Node buildExpressionTree(List<String> postfix) {
        Deque<Node> stack = new ArrayDeque<>();

        for (String token : postfix) {
            if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalStateException("Invalid expression: insufficient operands for operator " + token);
                }
                Node right = stack.pop();
                Node left = stack.pop();
                stack.push(new Node(token, left, right));
            } else {
                stack.push(new Node(token, null, null));
            }
        }

        if (stack.size() != 1) {
            throw new IllegalStateException("Invalid expression: leftover operands");
        }

        return stack.pop();
    }


    private boolean isOperator(char ch) {
        return OPERATORS.contains(Character.toString(ch));
    }

    private boolean isOperator(String token) {
        return OPERATORS.contains(token);
    }

    private boolean hasHigherPrecedence(String op1, String op2) {
        return PRECEDENCE.get(op1) >= PRECEDENCE.get(op2);
    }
}
