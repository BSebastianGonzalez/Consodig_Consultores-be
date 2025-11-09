package com.cc.be.controller;

import com.cc.be.dto.EvaluadorRequestDTO;
import com.cc.be.model.Evaluador;
import com.cc.be.service.EvaluadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluadores")
@RequiredArgsConstructor
public class EvaluadorController {

    private final EvaluadorService evaluadorService;

    @PostMapping
    public ResponseEntity<Evaluador> createEvaluador(@RequestBody EvaluadorRequestDTO dto) {
        return ResponseEntity.ok(evaluadorService.createEvaluador(dto));
    }

    @GetMapping
    public ResponseEntity<List<Evaluador>> getAllEvaluadores() {
        return ResponseEntity.ok(evaluadorService.getAllEvaluadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluador> getEvaluadorById(@PathVariable Long id) {
        return ResponseEntity.ok(evaluadorService.getEvaluadorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evaluador> updateEvaluador(@PathVariable Long id, @RequestBody EvaluadorRequestDTO dto) {
        return ResponseEntity.ok(evaluadorService.updateEvaluador(id, dto));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateEvaluador(@PathVariable Long id) {
        evaluadorService.deactivateEvaluador(id);
        return ResponseEntity.ok("Evaluador desactivado correctamente");
    }
}
