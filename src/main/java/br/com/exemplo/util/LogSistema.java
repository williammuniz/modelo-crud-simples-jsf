/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.exemplo.util;

import java.util.Date;
import javax.faces.application.FacesMessage;

/**
 *
 * @author William
 */
public class LogSistema {
    private Date ocorridoEm;        
    private FacesMessage mensagem;    
    private Boolean foiVisualizada;

    public LogSistema() {
    }

    public LogSistema(Date ocorridoEm, FacesMessage mensagem) {
        this.ocorridoEm = ocorridoEm;
        this.mensagem = mensagem;        
    }

    public LogSistema(Date ocorridoEm, FacesMessage mensagem, Boolean foiVisualizada) {
        this.ocorridoEm = ocorridoEm;
        this.mensagem = mensagem;
        this.foiVisualizada = foiVisualizada;
    }
    
    
    
    public FacesMessage getMensagem() {
        return mensagem;
    }

    public void setMensagem(FacesMessage mensagem) {
        this.mensagem = mensagem;
    }

    public Date getOcorridoEm() {
        return ocorridoEm;
    }

    public void setOcorridoEm(Date ocorridoEm) {
        this.ocorridoEm = ocorridoEm;
    }

    public Boolean getFoiVisualizada() {
        return foiVisualizada;
    }

    public void setFoiVisualizada(Boolean foiVisualizada) {
        this.foiVisualizada = foiVisualizada;
    }    
}
