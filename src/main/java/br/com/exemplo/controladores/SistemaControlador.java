package br.com.exemplo.controladores;

import br.com.exemplo.util.LogSistema;
import br.com.exemplo.util.Util;
import com.ocpsoft.pretty.PrettyContext;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author William
 */
@Controller
@Scope(value = "session")
public class SistemaControlador implements Serializable {

    private List<Object[]> caminhos;
    private Boolean mostraRodape;
    private Boolean mostraCabecalho;

    /**
     * Creates a new instance of SistemaControlador
     */
    @SuppressWarnings("unchecked")
    public SistemaControlador() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, servletContext);
        this.caminhos = new ArrayList<Object[]>();
    }

    public void tratarCaminhos(String destino) {
        Object[] obj = new Object[2];
        obj[0] = PrettyContext.getCurrentInstance().getRequestURL().toString();
        obj[1] = destino;
        this.caminhos.add(obj);
        Util.redirecionamentoInterno(destino);
    }

    public List<Object[]> getCaminhos() {
        return caminhos;
    }

    public void setCaminhos(List<Object[]> relacionamentos) {
        this.caminhos = relacionamentos;
    }

    public void navegarPara(String destino) {
        limparObjetosConversacao();
        Util.redirecionamentoInterno(destino);
    }

    public void navegarParaUrl(String destino) {
        limparObjetosConversacao();
        Util.redirecionarParaURL(destino);
    }

    public void limparObjetosConversacao() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("objetosConversacao", null);
    }

    public Boolean getMostraCabecalho() {
        return mostraCabecalho;
    }

    public void setMostraCabecalho(Boolean mostraCabecalho) {
        this.mostraCabecalho = mostraCabecalho;
    }

    public Boolean getMostraRodape() {
        return mostraRodape;
    }

    public void setMostraRodape(Boolean mostraRodape) {
        this.mostraRodape = mostraRodape;
    }

    public String getUsuario() {
        String login = "SEM_LOGIN";
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Principal principal = facesContext.getExternalContext().getUserPrincipal();
            if (principal != null) {
                login = principal.getName();
            }
        } catch (Exception ex) {
            System.out.println("Impossível determinar o Usuário");
            ex.printStackTrace();
        }
        return login;
    }

    public String recuperarClasseGravidadeMensagem(FacesMessage mensagem) {
        String retorno = "";
        if (mensagem == null) {
            return retorno;
        }
        if (mensagem.getSeverity().equals(FacesMessage.SEVERITY_INFO)) {
            retorno = "info";
        }
        if ((mensagem.getSeverity().equals(FacesMessage.SEVERITY_WARN))) {
            retorno = "warning";
        }
        if ((mensagem.getSeverity().equals(FacesMessage.SEVERITY_ERROR))) {
            retorno = "danger";
        }
        if ((mensagem.getSeverity().equals(FacesMessage.SEVERITY_FATAL))) {
            retorno = "danger";
        }

        return retorno;
    }

    public List<LogSistema> getMensagens() {
        return (List<LogSistema>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mensagensLogSistema");
    }

    public void limparTodasMensagens() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mensagensLogSistema", null);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("primeiraMensagem", null);
    }
}
