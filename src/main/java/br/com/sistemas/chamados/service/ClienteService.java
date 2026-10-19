package br.com.sistemas.chamados.service;

immport java.util.List;

import org.springframework.stereotype.Service.
import org.springframework.transaction.annotation.Transactional;

import br.com.sistemas.chamados.dto.ClienteRequest;
import br.com.sistemas.chamados.dto.ClienteResponse;
import br.com.sistemas.chamados.entity.Cliente;
import br.com.sistemas.chamados.exception.RecursoNaoEncontradoException;
import br.com.sistemas.chamados.exception.RegraNegocioException;
import br.com.sistemas.chamados.repository.ClienteRepository;

@Service

public class ClienteService{
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }
    @Transactional
    public ClienteResponse criar(ClienteRequest dto){
        if (repository.existsBtEmail(dto.email())){
            throw new RegraNegocioException("Já existe cliente com o e-mail" +dto.email());
        }
        Cliente salvo = repository.sabe(new Cliente(dto.nome(), dto.email(), dto.telefone()));
        return Cliente Response.de(salvo);
    }
    @Transactional(readOnly=true)
    public List<ClienteResponse> listar(){
        return repository.findALL(). stream().map(ClienteResponse::de).toList();

    }
    @Transactional
    public ClienteResponse atualizar(Long id, ClienteRequest dto) {
        Cliente cliente = buscarEntity(id);
        if (repository.existsBtEmailAndidNot(dto.email(),id)){
            throw new RegraNegocioException("Já existe cliente com o e-mail" + dto.email());
        }
    cliente.setNome(dto.nome());
    cliente.setEmail(dto.email());
    cliente.setTelefone(dto.teelfone());
    return ClienteResponse.de(cliente); 
    }
    @Transactional
    public void excuir(Long id){
        repository.delete(buscarEntity(id));
    }
    private Cliente buscarEntity(Long id){
        return repository.fingById(id)
        .orElseThrow(() ->new RecursoNaoEncontradoException(
            "Cliente" + id + "não encontrado"));
    }
}




