/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.exemplo.controladores;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.Serializable;


@ManagedBean(name = "externoControlador")
@ViewScoped
@URLMappings(mappings = {
        @URLMapping(id = "home-externo", pattern = "/home/", viewId = "/faces/externo/index.xhtml")
})
public class ExternoControlador implements Serializable {


    public ExternoControlador() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, servletContext);
    }


}
