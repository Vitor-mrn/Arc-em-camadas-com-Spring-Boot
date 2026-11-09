
package br.com.sistemas.chamados.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import br.com.sistemas.chamados.enums.Prioridade;
import br.com.sistemas.chamados.enums.Status;

@Entity
@Table(name = "chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Prioridade prioridade;

    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "chamado")
    private List<Comentario> comentarios;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    public Chamado() {}

    public Chamado(String titulo, String descricao,
                   Prioridade prioridade, Cliente cliente,
                   Tecnico tecnico, Categoria categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.cliente = cliente;
        this.tecnico = tecnico;
        this.categoria = categoria;
    }

    @PrePersist
    public void antesDeSalvar() {
        if (dataAbertura == null) {
            dataAbertura = LocalDateTime.now();
        }

        if (status == null) {
            status = Status.ABERTO;
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;

        if (status == Status.FECHADO) {
            this.dataFechamento = LocalDateTime.now();
        } else {
            this.dataFechamento = null;
        }
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }
}
