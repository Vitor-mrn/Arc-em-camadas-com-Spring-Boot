package br.com.sistemas.chamados.service;

import br.com.sistemas.chamados.dto.CategoriaRequest;
import br.com.sistemas.chamados.dto.CategoriaResponse;
import br.com.sistemas.chamados.entity.Categoria;
import br.com.sistemas.chamados.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaResponse criar(CategoriaRequest dados) {
        if (categoriaRepository.existsByNomeIgnoreCase(dados.nome())) {
            throw new IllegalArgumentException("Já existe uma categoria com esse nome");
        }
        Categoria categoria = new Categoria(
            dados.nome(),
            dados.descricao()
        );
        categoria = categoriaRepository.save(categoria);
        return converterParaDTO(categoria);
    }

    public List<CategoriaResponse> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();}

    public CategoriaResponse buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        return converterParaDTO(categoria);}

    public CategoriaResponse atualizar(Long id, CategoriaRequest dados) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        if (!categoria.getNome().equalsIgnoreCase(dados.nome())
                && categoriaRepository.existsByNomeIgnoreCase(dados.nome())) {
            throw new IllegalArgumentException("Já existe uma categoria com esse nome");
        }
        categoria.setNome(dados.nome());
        categoria.setDescricao(dados.descricao());
        categoria = categoriaRepository.save(categoria);
        return converterParaDTO(categoria);
    }
    public void excluir(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoria não encontrada");
        }
        categoriaRepository.deleteById(id);
    }
    private CategoriaResponse converterParaDTO(Categoria categoria) {
        return new CategoriaResponse(
            categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao()
        );
    }
}
