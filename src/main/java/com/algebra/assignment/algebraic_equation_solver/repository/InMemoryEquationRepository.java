package com.algebra.assignment.algebraic_equation_solver.repository;

import com.algebra.assignment.algebraic_equation_solver.model.Equation;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryEquationRepository implements EquationRepository {

    private final Map<String, Equation> equationStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Equation save(Equation equation) {
        String id = String.valueOf(idGenerator.getAndIncrement());
        equation.setEquationId(id);
        equationStore.put(id, equation);
        return equation;
    }

    @Override
    public List<Equation> findAll() {
        return new ArrayList<>(equationStore.values());
    }

    @Override
    public Optional<Equation> findById(String equationId) {
        return Optional.ofNullable(equationStore.get(equationId));
    }
}
