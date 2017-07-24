/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.exemplo.util.anotacoes;

/**
 *
 * @author William
 */
public enum Operacoes {
    NOVO("Inserido(a)"),
    EDITAR("Alterado(a)"),
    LISTAR("Listado(a)"),
    VER("Visto(a)"),
    EXCLUIR("Excluído(a)");
    
     private String descricao;

    private Operacoes(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
