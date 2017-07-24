package br.com.exemplo.controladores;

import br.com.exemplo.entidades.Usuario;
import br.com.exemplo.enums.TipoUsuario;
import br.com.exemplo.negocios.UsuarioFacade;
import br.com.exemplo.supers.SuperFacade;
import br.com.exemplo.util.FacesUtil;
import br.com.exemplo.util.anotacoes.Operacoes;
import br.com.exemplo.util.excecoes.ExcecaoEnvioEmail;
import br.com.exemplo.supers.SuperControlador;
import br.com.exemplo.util.Seguranca;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLAction.PhaseId;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "usuarioControlador")
@ViewScoped
@URLMappings(mappings = {
        @URLMapping(id = "novo-usuario", pattern = "/admin/usuario/novo/", viewId = "/faces/interno/admin/usuario/editar.xhtml"),
        @URLMapping(id = "editar-usuario", pattern = "/admin/usuario/editar/#{usuarioControlador.id}/", viewId = "/faces/interno/admin/usuario/editar.xhtml"),
        @URLMapping(id = "listar-usuario", pattern = "/admin/usuario/listar/", viewId = "/faces/interno/admin/usuario/listar.xhtml"),
        @URLMapping(id = "ver-usuario", pattern = "/admin/usuario/ver/#{usuarioControlador.id}/", viewId = "/faces/interno/admin/usuario/visualizar.xhtml")
})
public class UsuarioControlador extends SuperControlador<Usuario> implements Serializable {

    private static final PasswordEncoder PASSWORD_ENCODER = new ShaPasswordEncoder();
    @Autowired
    private UsuarioFacade usuarioFacade;

    @Autowired
    private UsuarioFacade usuarioService;

    private String senha;
    private String repeteSenha;
    private Usuario usuario;
    private String nomeUsuario;
    private String emailUsuario;

    public UsuarioControlador() {
        super(Usuario.class);
    }

    @Override
    public void salvar() {
        if (selecionado.getId() == null) {
            if (!"".equals(selecionado.getSenha().trim())) {
                String senhaInformada = selecionado.getSenha();
                selecionado.setSenha(PASSWORD_ENCODER.encodePassword(Seguranca.md5(senhaInformada), selecionado.getSalt()));
            }
        }
        super.salvar(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SuperFacade getEsteFacade() {
        return usuarioFacade;
    }

    @URLAction(mappingId = "listar-usuario", phaseId = PhaseId.RENDER_RESPONSE, onPostback = false)
    @Override
    public void listar() {
        super.listar();
    }

    @URLAction(mappingId = "novo-usuario", phaseId = PhaseId.RENDER_RESPONSE, onPostback = false)
    @Override
    public void novo() {
        super.novo();
    }

    @URLAction(mappingId = "editar-usuario", phaseId = PhaseId.RENDER_RESPONSE, onPostback = false)
    @Override
    public void editar() {
        setOperacao(Operacoes.EDITAR);
        this.selecionado = usuarioFacade.recuperar(getId());
    }

    @URLAction(mappingId = "ver-usuario", phaseId = PhaseId.RENDER_RESPONSE, onPostback = false)
    @Override
    public void visualizar() {
        super.visualizar();
    }

    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }


    public List<Usuario> getLista() {
        return usuarioService.listar();
    }

    public List<Usuario> completarEstaEntidade(String parte) {
        return usuarioFacade.listarFiltrando(parte.trim(), "login", "id");
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getRepeteSenha() {
        return repeteSenha;
    }

    public void setRepeteSenha(String repeteSenha) {
        this.repeteSenha = repeteSenha;
    }

    public List<SelectItem> selectItensUsuario() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        toReturn.add(new SelectItem(null, " "));
        for (Usuario catDaVez : usuarioFacade.listar()) {
            toReturn.add(new SelectItem(catDaVez.getId(), catDaVez.toString()));
        }
        return toReturn;
    }

    public List<SelectItem> getTiposUsuario() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        toReturn.add(new SelectItem(null, " "));
        for (TipoUsuario catDaVez : TipoUsuario.values()) {
            toReturn.add(new SelectItem(catDaVez, catDaVez.toString()));
        }
        return toReturn;
    }

    public void alteraSenha() {
        if (!senha.equals("") || !repeteSenha.equals("")) {
            if (senha.equals(repeteSenha)) {
//            UsuarioFacade facade = (UsuarioFacade) FabricaFacade.getInstance().getBean(UsuarioFacade.class);
                usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioAlteracaoSenha");
                if (usuario == null) {
                    FacesUtil.addWarn("Impossível continuar", "Usuário não selecionado");
                    return;
                }
                usuario.setSenha(new ShaPasswordEncoder().encodePassword(senha, usuario.getSalt()));
                usuarioFacade.alterar(usuario);
                usuario = null;
                FacesUtil.addInfo("Operação realizada com sucesso", "Para ter acesso as suas informações faça login");
            } else {
                FacesUtil.addWarn("Impossível continuar", "As senhas informadas não coincidem.");
            }
        } else {
            FacesUtil.addWarn("Impossível continuar", "Informe todos os campos.");
        }
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void recuperarSenha() {
        if ((!nomeUsuario.trim().equals("") || !emailUsuario.trim().equals(""))) {
            if (!nomeUsuario.trim().equals("Usuário") || !emailUsuario.trim().equals("Email")) {
                try {
                    usuarioFacade.iniciaRecuperaSenha(nomeUsuario, emailUsuario);
                    FacesUtil.addInfo("Operação realizada com sucesso. ", "Foi enviado um e-mail para seu endereço principal, para continuar siga os procedimentos nele contido.");
                } catch (ExcecaoEnvioEmail ex) {
                    FacesUtil.addError("Impossível Continuar", "Não foi identificado um email válido para o envio, contate o suporte.");
                } catch (Exception ex) {
                    FacesUtil.addError("Impossivel continuar", "Não foi possivel atender a solicitação, contate o suporte.");
                }
            } else {
                addMessage();
            }
        } else {
            addMessage();
        }
    }

    public void addMessage() {
        FacesUtil.addError("Impossível Continuar", "Por favor preencha os campos Nome de Usuário e Email.");
    }
}
