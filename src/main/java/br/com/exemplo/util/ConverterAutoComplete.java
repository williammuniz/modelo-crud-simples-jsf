
package br.com.exemplo.util;

import br.com.exemplo.supers.SuperFacade;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.primefaces.context.RequestContext;


public class ConverterAutoComplete implements Converter, Serializable {

    protected static final Logger logger = Logger.getLogger("ConverterAutoComplete");
    private SuperFacade facade;
    private Class classe;

    public ConverterAutoComplete(Class classe, SuperFacade f) {
        this.facade = f;
        this.classe = classe;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value.trim().length() <= 0) {
                return null;
            }
            Object chave = Persistencia.getFieldId(classe).getType().getConstructor(String.class).newInstance(value);
            RequestContext.getCurrentInstance().update(component.getClientId());
            return facade.recuperar(classe, chave);
        } catch (Exception ex) {
            return null;            
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return Persistencia.getAttributeValue(value, Persistencia.getFieldId(classe).getName()).toString();
        } else {
            return null;
        }
    }
}
