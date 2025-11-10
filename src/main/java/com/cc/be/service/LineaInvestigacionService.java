package com.cc.be.service;

import com.cc.be.dto.LineaInvestigacionDTO;
import com.cc.be.model.LineaInvestigacion;
import com.cc.be.repository.LineaInvestigacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LineaInvestigacionService {
    private final LineaInvestigacionRepository lineaInvestigacionRepository;

    public LineaInvestigacion createLineaInvestigacion(LineaInvestigacionDTO dto) {
        LineaInvestigacion linea = new LineaInvestigacion();
        linea.setNombre(dto.getNombre());
        linea.setDescripcion(dto.getDescripcion());
        return lineaInvestigacionRepository.save(linea);
    }

    public List<LineaInvestigacion> getAllLineasInvestigacion() {
        return lineaInvestigacionRepository.findAll();
    }

    public LineaInvestigacion getLineaInvestigacionById(Long id) {
        return lineaInvestigacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Línea de investigación no encontrada"));
    }

    public LineaInvestigacion updateLineaInvestigacion(Long id, LineaInvestigacionDTO dto) {
        LineaInvestigacion linea = getLineaInvestigacionById(id);
        linea.setNombre(dto.getNombre());
        linea.setDescripcion(dto.getDescripcion());
        return lineaInvestigacionRepository.save(linea);
    }

    public void deleteLineaInvestigacion(Long id) {
        LineaInvestigacion linea = getLineaInvestigacionById(id);
        lineaInvestigacionRepository.delete(linea);
    }
}
