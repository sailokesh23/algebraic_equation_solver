package com.algebra.assignment.algebraic_equation_solver.controller;

import com.algebra.assignment.algebraic_equation_solver.dto.*;
import com.algebra.assignment.algebraic_equation_solver.model.Equation;
import com.algebra.assignment.algebraic_equation_solver.service.EquationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/equations")
public class EquationController {

    private final EquationService equationService;

    @Autowired
    public EquationController(EquationService equationService) {
        this.equationService = equationService;
    }

    // POST /api/equations/store
    @PostMapping("/store")
    public ResponseEntity<StoreEquationResponse> storeEquation(@RequestBody StoreEquationRequest request) {
        Equation stored = equationService.storeEquation(request.getEquation());

        StoreEquationResponse response = new StoreEquationResponse(
                "Equation stored successfully",
                stored.getEquationId()
        );
        return ResponseEntity.ok(response);
    }

    // GET /api/equations
    @GetMapping
    public ResponseEntity<List<EquationDto>> getAllEquations() {
        List<Equation> equations = equationService.getAllEquations();
        List<EquationDto> dtos = equations.stream()
                .map(eq -> new EquationDto(eq.getEquationId(), eq.getOriginalEquation()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // POST /api/equations/{id}/evaluate
    @PostMapping("/{id}/evaluate")
    public ResponseEntity<EvaluateEquationResponse> evaluateEquation(
            @PathVariable String id,
            @RequestBody EvaluateEquationRequest request) {

        double result = equationService.evaluateEquation(id, request.getVariables());

        EvaluateEquationResponse response = new EvaluateEquationResponse(
                id,
                result,
                request.getVariables()
        );
        return ResponseEntity.ok(response);
    }
}
