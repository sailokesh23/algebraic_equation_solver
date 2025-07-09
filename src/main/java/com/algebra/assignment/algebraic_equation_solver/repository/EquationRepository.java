package com.algebra.assignment.algebraic_equation_solver.repository;

import com.algebra.assignment.algebraic_equation_solver.model.Equation;

import java.util.List;
import java.util.Optional;

public interface EquationRepository {

    // Save a new equation
    Equation save(Equation equation);

    // Get all stored equations
    List<Equation> findAll();

    // Get a single equation by ID
    Optional<Equation> findById(String equationId);
}
