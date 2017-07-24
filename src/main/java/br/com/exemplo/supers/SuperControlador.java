package br.com.exemplo.supers;

import br.com.exemplo.controladores.SistemaControlador;
import br.com.exemplo.util.Cadastravel;
import br.com.exemplo.util.FacesUtil;
import br.com.exemplo.util.Persistencia;
import br.com.exemplo.util.anotacoes.Operacoes;
import br.com.exemplo.util.excecoes.ValidacaoException;
import br.com.exemplo.util.anotacoes.CRUD;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public abstract class SuperControlador<T> {

    @Autowired
    protected SistemaControlador sistemaControlador;
    protected Operacoes operacao;
    protected Class<T> classe;
    public T selecionado;
    private Long id;
    private Map<Field, String> atributosPesquisaveis;
    private static final String ALIAS = "obj";
    private List lista;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Field, String> getAtributosPesquisaveis() {
        return atributosPesquisaveis;
    }

    public SuperControlador() {
    }

    public SuperControlador(Class<T> classe) {
        this.classe = classe;
        injetarDependenciasSpring();
        montaMapaAtributosPesquisaveis();

    }

    public final void injetarDependenciasSpring() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, servletContext);
    }

    public abstract SuperFacade getEsteFacade();

    public T getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(T selecionado) {
        this.selecionado = selecionado;
    }

    public Operacoes getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacoes operacao) {
        this.operacao = operacao;
    }

    public void listar() {
        setOperacao(Operacoes.LISTAR);
    }

    public void novo() {
        setOperacao(Operacoes.NOVO);
        try {
            this.selecionado = classe.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(SuperControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SuperControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editar() {
        setOperacao(Operacoes.EDITAR);
        this.selecionado = (T) getEsteFacade().recuperar(classe, id);
    }

    public void visualizar() {
        setOperacao(Operacoes.VER);
        editar();
    }

    public void salvar() {
        try {
            if (this.getOperacao().equals(Operacoes.NOVO)) {
                getEsteFacade().inserir(this.selecionado);
            }
            if (this.getOperacao().equals(Operacoes.EDITAR)) {
                getEsteFacade().alterar(this.selecionado);
            }
            FacesUtil.addInfo("Operação realizada com sucesso!", "O " + this.selecionado.toString() + " foi <b>" + this.operacao.getDescricao() + "</b> com sucesso!");
        } catch (RuntimeException ex) {
            if (ex instanceof ValidacaoException) {
                FacesUtil.addError(ex.getMessage(), ex.getMessage());
            } else {
                FacesUtil.addErrorPadrao(ex);
            }
            return;
        } catch (Exception ex) {
            FacesUtil.addErrorPadrao(ex);
            return;
        }

        navegarEmbora();
    }

    public void navegarEmbora() {
        FacesUtil.navegaEmbora(selecionado, ((Cadastravel) selecionado).getCaminhoPadrao());
    }

    public void excluir(Object obj) {
        try {
            getEsteFacade().excluir(obj);
            FacesUtil.addInfo("Operação realizada com sucesso.", "Registro excluido com sucesso");
        } catch (RuntimeException ex) {
            FacesUtil.addError("Operação não permitida.", ex.getMessage());
        } catch (Exception ex) {
            FacesUtil.addErrorPadrao(ex);
        }
    }

    public List<Field> getCamposDoSelecionado() {
        List<Field> campos = Lists.newLinkedList();
        for (Field f : Persistencia.getAtributos(classe)) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(CRUD.class)) {
                if (f.getAnnotation(CRUD.class).visualizavel()) {
                    campos.add(f);
                }
            }
        }
        return campos;
    }

    public List<Field> getCamposPesquisaveis() {
        List<Field> campos = Lists.newLinkedList();
        for (Field f : Persistencia.getAtributos(classe)) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(CRUD.class)) {
                if (f.getAnnotation(CRUD.class).pesquisavel()) {
                    campos.add(f);
                }
            }
        }
        return campos;
    }

    public String obterLabelCampo(Field f) {
        f.setAccessible(true);
        if (f.isAnnotationPresent(CRUD.class)) {
            return f.getAnnotation(CRUD.class).label();
        }
        return "";
    }

    public Object obterConteudoCampo(Field f, Object selecionado) {
        this.selecionado = (T) selecionado;
        return obterConteudoCampo(f);
    }

    public Object obterConteudoCampo(Field f) {
        f.setAccessible(true);
        if (f.isAnnotationPresent(CRUD.class)) {
            return Persistencia.getAttributeValue(selecionado, f.getName());
        }
        return "";
    }

    private void montaMapaAtributosPesquisaveis() {
        atributosPesquisaveis = Maps.newHashMap();
        for (Field f : getCamposPesquisaveis()) {
            atributosPesquisaveis.put(f, null);
        }
    }

    public List getLista() {
        if (lista == null) {
            lista = getEsteFacade().listar(10);
        }
        return lista;
    }

    public void pesquisar() {
        String juncao = " where ";
        String query = "select " + ALIAS + " from " + classe.getSimpleName() + " " + ALIAS;
        for (Field f : atributosPesquisaveis.keySet()) {
            String valor = atributosPesquisaveis.get(f);
            if (valor != null && !valor.isEmpty()) {
                query += juncao + montaCondicao(f);
                juncao = " and ";
            }
        }
        query += " order by " + ALIAS + ".id";
        lista = getEsteFacade().getEntityManager().createQuery(query).getResultList();
        System.out.println("query " + query);
    }

    public String montaCondicao(Field f) {
        Class tipo = f.getType();
        if (tipo.equals(String.class)) {
            return "lower(" + ALIAS + "." + f.getName() + ") like '%" + atributosPesquisaveis.get(f).toLowerCase() + "%'";
        }
        if (tipo.equals(Long.class)) {
            return " cast(" + ALIAS + "." + f.getName() + " as text) like '%" + atributosPesquisaveis.get(f) + "%'";
        }

        return "";
    }

    public String getNomeEntidade() {
        if (classe.isAnnotationPresent(CRUD.class)) {
            return classe.getAnnotation(CRUD.class).label();
        }
        return classe.getSimpleName();
    }

    public void vaiParaLista() {
        String listar = ((Cadastravel) selecionado).getCaminhoPadrao() + "listar/";
        FacesUtil.redirecionamentoInterno(listar);
    }

    public void vaiParaNovo() {
        String listar = ((Cadastravel) selecionado).getCaminhoPadrao() + "novo/";
        FacesUtil.redirecionamentoInterno(listar);
    }
}
