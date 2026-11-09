package br.com.sistemas.chamados.controller;
import br.com.sistemas.chamados.dto.TecnicoRequest;
import br.com.sistemas.chamados.dto.TecnicoResponse;
import br.com.sistemas.chamados.service.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {
    private final TecnicoService tecnicoService;
    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }
    @PostMapping
    public ResponseEntity<TecnicoResponse> criar(
            @Valid @RequestBody TecnicoRequest dados) {

        TecnicoResponse tecnico = tecnicoService.criar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnico);
    }

    @GetMapping
    public ResponseEntity<List<TecnicoResponse>> listar() {
        return ResponseEntity.ok(tecnicoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TecnicoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tecnicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TecnicoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TecnicoRequest dados) {
        return ResponseEntity.ok(tecnicoService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        tecnicoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}