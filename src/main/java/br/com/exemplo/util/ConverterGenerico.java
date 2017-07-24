
package br.com.exemplo.util;


import br.com.exemplo.supers.SuperFacade;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ConverterGenerico implements Converter, Serializable {

    private Class clazz;
    private SuperFacade superFacade;

    public ConverterGenerico(Class clazz, SuperFacade superFacade) {
        this.clazz = clazz;
        this.superFacade = superFacade;
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Object chave = Persistencia.getFieldId(clazz).getType().getConstructor(String.class).newInstance(value);
           return superFacade.recuperar(clazz, chave);
        } catch (Exception e) {
            new Exception("Problema ao converter objeto" + e);
        }

        return null;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return String.valueOf(Persistencia.getId(value));
        } else {
            return null;
        }
    }
}
