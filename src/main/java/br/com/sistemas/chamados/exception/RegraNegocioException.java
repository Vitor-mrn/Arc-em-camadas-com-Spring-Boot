package br.com.sistemas.chamados.exception;

public class RegraNegocio extends RuntimeException{
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}