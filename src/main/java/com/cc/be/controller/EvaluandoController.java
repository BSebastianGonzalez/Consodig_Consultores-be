package com.cc.be.controller;

import com.cc.be.dto.EvaluandoRequestDTO;
import com.cc.be.model.Evaluando;
import com.cc.be.service.EvaluandoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluandos")
@RequiredArgsConstructor
public class EvaluandoController {

    private final EvaluandoService evaluandoService;

    @PostMapping
    public Evaluando createEvaluando(@RequestBody EvaluandoRequestDTO evaluandoRequestDTO) {
        return evaluandoService.createEvaluando(evaluandoRequestDTO);
    }

    @GetMapping
    public List<Evaluando> getAllEvaluandos() {
        return evaluandoService.getAllEvaluandos();
    }

    @GetMapping("/{id}")
    public Evaluando getEvaluandoById(@PathVariable Long id) {
        return evaluandoService.getEvaluandoById(id);
    }

    @PutMapping("/{id}")
    public Evaluando updateEvaluando(@PathVariable Long id, @RequestBody EvaluandoRequestDTO evaluandoRequestDTO) {
        return evaluandoService.updateEvaluando(id, evaluandoRequestDTO);
    }

    @PutMapping("/{id}/deactivate")
    public void deactivateEvaluando(@PathVariable Long id) {
        evaluandoService.deactivateEvaluando(id);
    }
}
