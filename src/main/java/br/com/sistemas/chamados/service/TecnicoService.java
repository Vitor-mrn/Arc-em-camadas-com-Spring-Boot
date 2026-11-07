package br.com.sistemas.chamados.service;
import br.com.sistemas.chamados.dto.TecnicoRequest;
import br.com.sistemas.chamados.dto.TecnicoResponse;
import br.com.sistemas.chamados.entity.Tecnico;
import br.com.sistemas.chamados.repository.TecnicoRepository;
import org.springframework.stereotype.Service;
import br.com.sistemas.chamados.exception.RecursoNaoEncontradoException;
import br.com.sistemas.chamados.exception.RegraNegocioException;
import java.util.List;

@Service
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;

    public TecnicoService(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    public TecnicoResponse criar(TecnicoRequest dados) {
        if (tecnicoRepository.existsByEmailIgnoreCase(dados.email())) {
            throw new RegraNegocioException("Já existe um técnico com esse e-mail");
        }
        Tecnico tecnico = new Tecnico(
            dados.nome(),
            dados.email()
        );

        Tecnico salvo = tecnicoRepository.save(tecnico);

        return converterParaDTO(salvo);
    }

    public List<TecnicoResponse> listar() {
        return tecnicoRepository.findAll()
            .stream()
            .map(this::converterParaDTO)
            .toList();
    }

    public TecnicoResponse buscarPorId(Long id) {
        Tecnico tecnico = buscarEntidadePorId(id);
        return converterParaDTO(tecnico);
    }

    public TecnicoResponse atualizar(Long id, TecnicoRequest dados) {
        Tecnico tecnico = buscarEntidadePorId(id);
        boolean emailEmUso = tecnicoRepository.existsByEmailIgnoreCase(dados.email());
        if (emailEmUso && !tecnico.getEmail().equalsIgnoreCase(dados.email())) {
        throw new RegraNegocioException("Já existe um técnico com esse e-mail");
        }
        tecnico.setNome(dados.nome());
        tecnico.setEmail(dados.email());
        Tecnico atualizado = tecnicoRepository.save(tecnico);
        return converterParaDTO(atualizado);
    }

    public void excluir(Long id) {
        Tecnico tecnico = buscarEntidadePorId(id);
        tecnicoRepository.delete(tecnico);
    }

    private Tecnico buscarEntidadePorId(Long id) {
    return tecnicoRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Técnico não encontrado"));
    }

    private TecnicoResponse converterParaDTO(Tecnico tecnico) {
        return new TecnicoResponse(
            tecnico.getId(),
            tecnico.getNome(),
            tecnico.getEmail()
        );
    }
}