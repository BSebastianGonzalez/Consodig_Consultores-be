package com.cc.be.controller;

import com.cc.be.dto.LineaInvestigacionDTO;
import com.cc.be.model.LineaInvestigacion;
import com.cc.be.service.LineaInvestigacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lineas-investigacion")
public class LineaInvestigacionController {
    private final LineaInvestigacionService lineaInvestigacionService;

    @PostMapping
    public LineaInvestigacion create(@RequestBody LineaInvestigacionDTO dto) {
        return lineaInvestigacionService.createLineaInvestigacion(dto);
    }

    @GetMapping("/list")
    public List<LineaInvestigacion> getAll() {
        return lineaInvestigacionService.getAllLineasInvestigacion();
    }

    @GetMapping("/{id}")
    public LineaInvestigacion getById(@PathVariable Long id) {
        return lineaInvestigacionService.getLineaInvestigacionById(id);
    }

    @PutMapping("/update/{id}")
    public LineaInvestigacion update(@PathVariable Long id, @RequestBody LineaInvestigacionDTO dto) {
        return lineaInvestigacionService.updateLineaInvestigacion(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        lineaInvestigacionService.deleteLineaInvestigacion(id);
    }
}
