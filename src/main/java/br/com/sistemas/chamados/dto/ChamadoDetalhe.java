package br.com.sistemas.chamados.dto;

import java.util.List;

public record ChamadoDetalhe(
    ChamadoResponse chamado,
    List<ComentarioResponse> comentarios 
    
) {}