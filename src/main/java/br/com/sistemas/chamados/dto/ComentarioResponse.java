package br.com.sistemas.chamados.dto;

import br.com.sistemas.chamados.entity.Comentario;

public record ComentarioResponse(
    Long id,
    String texto,
    Long chamadoId
) {
    public static ComentarioResponse de(Comentario comentario) {
        return new ComentarioResponse(
            comentario.getId(),
            comentario.getTexto(),
            comentario.getChamado().getId()
        );
    }
}
