/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.exemplo.util.excecoes;

/**
 *
 * @author Gustavo
 */
public class ExcecaoEnvioEmail extends Exception {

    public ExcecaoEnvioEmail(String mensagem) {
        super(mensagem);
    }
}
