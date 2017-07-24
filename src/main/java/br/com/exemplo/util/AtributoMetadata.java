/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.exemplo.util;

import br.com.exemplo.util.anotacoes.CRUD;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.Size;

/**
 *
 * @author William
 */
public class AtributoMetadata {

    protected static final Logger logger = Logger.getLogger("br.com.sistemasprefeitura.util.AtributoMetadata");
    private String etiqueta;
    private Field atributo;
    private int max = 15;

    public AtributoMetadata(Field f) {
        atributo = f;
        if (f.isAnnotationPresent(CRUD.class)) {
            CRUD e = f.getAnnotation(CRUD.class);
            etiqueta = e.label();
        } else {
            String nome = f.getName();
            etiqueta = nome.substring(0, 1).toUpperCase() + nome.substring(1);
        }
    }

    public Field getAtributo() {
        return atributo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public String getString(Object objeto) {
        if (objeto == null) {
            return "";
        }
        atributo.setAccessible(true);
        try {
            Object valor = atributo.get(objeto);
            if (valor == null) {
                return "";
            }
//            if (atributo.isAnnotationPresent(br.com.sistemasprefeitura.util.anotacoes.Monetario.class)) {
//                NumberFormat nf = NumberFormat.getCurrencyInstance();
//                nf.setMinimumFractionDigits(2);
//                nf.setMaximumFractionDigits(3);
//                return nf.format(new BigDecimal(valor.toString()));
//            }
//            if (atributo.isAnnotationPresent(br.com.sistemasprefeitura.util.anotacoes.UFM.class)) {
//                NumberFormat nf = NumberFormat.getNumberInstance();
//                nf.setMinimumFractionDigits(2);
//                nf.setMaximumFractionDigits(4);
//                return nf.format(new BigDecimal(valor.toString()));
//            }
//            if (atributo.isAnnotationPresent(br.com.sistemasprefeitura.util.anotacoes.Porcentagem.class)) {
//                NumberFormat nf = NumberFormat.getPercentInstance();
//                nf.setMinimumFractionDigits(2);
//                nf.setMaximumFractionDigits(4);
//                return nf.format(new BigDecimal(valor.toString()).divide(new BigDecimal("100")));
//            }
//            if (atributo.getType().equals(Date.class)) {
//                Temporal t = atributo.getAnnotation(Temporal.class);
//                if (t.value() == TemporalType.TIME) {
//                    return Util.sdfh.format(valor);
//                }
//                if (t.value() == TemporalType.DATE) {
//                    return Util.sdf.format(valor);
//                } else {
//                    return Util.sdft.format(valor);
//                }

//            }
            if (atributo.getType().equals(Boolean.class)) {
                if (valor.equals(true)) {
                    return "Sim";
                } else {
                    return "Não";
                }
            }
            if ((atributo.getType().equals(List.class))) {
                String lista = valor.toString().replaceAll("\\[", "<table><tr><td>");
                lista = lista.replaceAll(",", "</td></tr><tr><td>");
                lista = lista.replaceAll("]", "</td></tr></table>");
                return lista;
            }
            if ((atributo.getType().equals(Map.class))) {
                String lista = valor.toString().replaceAll("\\{", "<table><tr><td>");
                lista = lista.replaceAll(",", "</td></tr><tr><td>");
                lista = lista.replaceAll("}", "</td></tr></table>");
                return lista;
            }
            return valor.toString();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Problemas na conversão de atributos", ex);
        }
        return "?" + objeto.toString();
    }

    public int getMax() {
        max = 15;
        Size s = atributo.getAnnotation(Size.class);
        if (s != null) {
            max = s.max();
        }
        if (max > 70) {
            max = 70;
        }
        return max;
    }
}
