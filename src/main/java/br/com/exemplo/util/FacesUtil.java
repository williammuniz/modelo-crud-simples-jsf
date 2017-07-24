package br.com.exemplo.util;

import br.com.exemplo.controladores.Web;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class FacesUtil {

    public static void addWarn(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
    }

    public static void addInfo(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

    public static void addError(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
    }

    public static void addFatal(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail));
    }

    public static void addAll(List<FacesMessage> messages) {
        for (FacesMessage message : messages) {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public static void addErrorPadrao(Throwable ex) {
        addError("Operação não realizada.", "Por favor tente novamente, se o problema persistir entre em contato com o suporte técnico. Detalhes do Erro: " + ex.getMessage());
    }

    public static void navegaEmbora(Object selecionado, String caminhoPadrao) {
        String origem = Web.getCaminhoOrigem();
        if (!origem.isEmpty()) {
            if (origem.endsWith("novo/") || origem.endsWith("edita/")) {
                Web.poeNaSessao(selecionado);
            }
            redirecionamentoInterno(origem);
        } else {
            origem = caminhoPadrao + "listar/";
            redirecionamentoInterno(origem);
        }
    }

    public static void redirecionamentoInterno(String url) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(getRequestContextPath() + url);
        } catch (Exception ex) {
            Logger.getLogger(FacesUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getRequestContextPath() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    }
}
