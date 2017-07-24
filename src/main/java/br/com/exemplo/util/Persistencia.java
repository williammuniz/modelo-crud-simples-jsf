/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.exemplo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;

public class Persistencia {

    private static final Logger logger = Logger.getLogger("br.com.acimnews.util.Reflexao");
    public static final String UNIDADE_PERSISTENCIA = "modeloPU";

    public static Object getId(Object entidade) {
        try {
            Field f = getFieldId(entidade.getClass());
            f.setAccessible(true);
            return f.get(entidade);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Não foi possível encontrar a chave primária de " + entidade, ex);
        }
        return null;
    }

    public static Object getAttributeValue(Object entidade, String attrName) {
        try {
            Field f = entidade.getClass().getDeclaredField(attrName);
            f.setAccessible(true);
            return f.get(entidade);
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.log(Level.SEVERE, "Não foi possível encontrar o atributo " + attrName + " na classe " + entidade.getClass().getName(), ex);
        }
        return null;
    }

    public static void setId(Object entidade, Object valor) {
        try {
            Field f = getFieldId(entidade.getClass());
            f.setAccessible(true);
            f.set(entidade, valor);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Não foi possível encontrar a chave primária de " + entidade, ex);
        }
    }

    public static Field getFieldId(Class classe) {
        try {
            for (Field f : getAtributos(classe)) {
                if (f.isAnnotationPresent(Id.class)) {
                    return f;
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Não foi possível encontrar a chave primária de " + classe, ex);
        }
        return null;
    }

    public static List<Field> getAtributos(Class classe) {
        List<Field> lista = new ArrayList<Field>();
        if (!classe.getSuperclass().equals(Object.class)) {
            lista.addAll(getAtributos(classe.getSuperclass()));
        }
        for (Field f : classe.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            lista.add(f);
        }
        return lista;
    }

    public static Field primeiroAtributo(Class classe) {
        Field f = null;
        for (Field atributo : getAtributos(classe)) {
            if (Modifier.isStatic(atributo.getModifiers())) {
                continue;
            }
            if (f == null) {
                f = atributo;
            }
            if (!atributo.isAnnotationPresent(GeneratedValue.class)) {
                return atributo;
            } else if (f == null) {
                f = atributo;
            }
        }
        return f;
    }

    public static void duplicaColecoes(Object destino, Object origem) {
        try {
            for (Field f : getAtributos(origem.getClass())) {
                if (f.isAnnotationPresent(OneToMany.class) || (f.isAnnotationPresent(ManyToMany.class))) {
                    logger.log(Level.INFO, "Colecao " + f);
                    f.setAccessible(true);
                    Collection colecaoDestino = null;
                    Collection colecaoOrigem = (Collection) f.get(origem);
                    if (f.getType().equals(Set.class)) {
                        colecaoDestino = new HashSet();
                    }
                    if (f.getType().equals(List.class)) {
                        colecaoDestino = new ArrayList();
                    }
                    for (Object obj : colecaoOrigem) {
                        colecaoDestino.add(obj);
                    }
                    f.set(destino, colecaoDestino);
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Problema ao duplicar colecoes", ex);
        }
    }

    public static boolean atributoPossuiRelacionamento(Field f) {
        if (f.isAnnotationPresent(OneToOne.class)) {
            if (f.getAnnotation(OneToOne.class).mappedBy().trim().length() > 0 && f.getAnnotation(OneToOne.class).orphanRemoval() != true) { // .orphanRemoval() != true, pois pode ser null
                return true;
            }
        }
        if (f.isAnnotationPresent(OneToMany.class)) {
            if (f.getAnnotation(OneToMany.class).mappedBy().trim().length() > 0 && f.getAnnotation(OneToMany.class).orphanRemoval() != true) { // .orphanRemoval() != true, pois pode ser null
                return true;
            }
        }

        return false;
    }
}
