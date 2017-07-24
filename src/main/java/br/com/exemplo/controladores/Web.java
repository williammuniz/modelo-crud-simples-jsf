package br.com.exemplo.controladores;

import br.com.exemplo.util.FacesUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Entity;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@ManagedBean
@ViewScoped
public class Web implements Serializable {

     public static final String SESSION_KEY_CONVERSATION = "objectConversation";
    public static final String SESSION_KEY_CAMINHO = "objectCaminho";

    /**
     * Creates a new instance of initControlador
     */
    public Web() {
    }  

    private static Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    private static Map<Class, Object> getMapConversation() {
        return (Map<Class, Object>) getSessionMap().get(SESSION_KEY_CONVERSATION);
    }

    private static Stack getStackCaminhos() {
        return (Stack) getSessionMap().get(SESSION_KEY_CAMINHO);
    }

    public static void poeNaSessao(Object object) {
        if (getMapConversation() == null) {
            getSessionMap().put(SESSION_KEY_CONVERSATION, new HashMap<Class, Object>());
        }
         getMapConversation().put(object.getClass(), object);
    }

    public static Object pegaDaSessao(Class classe) throws InstantiationException, IllegalAccessException {
        if (getMapConversation() != null) {
            Object object = getMapConversation().get(classe);
            if (object != null) {
                getMapConversation().remove(classe);
                preencheAtributos(object);
                return object;
            }
        }
        return classe.newInstance();
    }

    private static void preencheAtributos(Object object) throws InstantiationException, IllegalArgumentException, IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getType().isAnnotationPresent(Entity.class) && field.get(object) == null) {
                field.set(object, pegaDaSessao(field.getType()));
            }
        }
    }

    public static void setCaminhoOrigem(String caminho) {
        if (getStackCaminhos() == null) {
            getSessionMap().put(SESSION_KEY_CAMINHO, new Stack<>());
        }
        getStackCaminhos().add(caminho);
    }

    public static String getCaminhoOrigem() {
        Stack<String> origens = getStackCaminhos();
        if (origens != null && !origens.isEmpty()) {
            String origem = origens.peek();
            origens.remove(origem);
            return origem;
        }
        return "";
    }
    public void navegacao(Object obj, String origem, String destino){
        poeNaSessao(obj);
        setCaminhoOrigem(origem);
        FacesUtil.redirecionamentoInterno(destino);
    }
    
    public void navegacao(Object selecionado, Object parente, String origem, String destino){
        poeNaSessao(selecionado);
        poeNaSessao(parente);
        setCaminhoOrigem(origem);
        FacesUtil.redirecionamentoInterno(destino);
    }
   
    public void navegacaoPadrao(String origem, String padrao, String destino){
        setCaminhoOrigem(origem);
        FacesUtil.redirecionamentoInterno(padrao+destino);
    }
}
