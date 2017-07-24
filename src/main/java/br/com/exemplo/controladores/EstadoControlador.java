/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.exemplo.controladores;

import br.com.exemplo.entidades.Estado;
import br.com.exemplo.negocios.EstadoFacade;
import br.com.exemplo.supers.SuperFacade;
import br.com.exemplo.supers.SuperControlador;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLAction.PhaseId;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author William
 */
@ManagedBean(name = "estadoControlador")
@ViewScoped
@URLMappings(mappings = {
    @URLMapping(id = "novo-estado", pattern = "/cadastro/estado/novo/", viewId = "/faces/interno/cadastro/estado/editar.xhtml"),
    @URLMapping(id = "editar-estado", pattern = "/cadastro/estado/editar/#{estadoControlador.id}/", viewId = "/faces/interno/cadastro/estado/editar.xhtml"),
    @URLMapping(id = "listar-estado", pattern = "/cadastro/estado/listar/", viewId = "/faces/interno/cadastro/estado/listar.xhtml"),
    @URLMapping(id = "ver-estado", pattern = "/cadastro/estado/ver/#{estadoControlador.id}/", viewId = "/faces/interno/cadastro/estado/visualizar.xhtml")
})
public class EstadoControlador extends SuperControlador<Estado> implements Serializable {

 
    @Autowired
    private EstadoFacade estadoFacade;

    public EstadoControlador() {
        super(Estado.class);
    }

    @Override
    public SuperFacade getEsteFacade() {
        return estadoFacade;
    }

    @URLAction(mappingId = "listar-estado", phaseId = PhaseId.RENDER_RESPONSE, onPostback = false)
    @Override
    public void listar() {
        super.listar();
    }

    @URLAction(mappingId = "novo-estado", phaseId = PhaseId.RENDER_RESPONSE, onPostback = false)
    @Override
    public void novo() {
        super.novo();
    }

    @URLAction(mappingId = "editar-estado", phaseId = PhaseId.RENDER_RESPONSE, onPostback = false)
    @Override
    public void editar() {
        super.editar();
    }

    @URLAction(mappingId = "ver-estado", phaseId = PhaseId.RENDER_RESPONSE, onPostback = false)
    @Override
    public void visualizar() {
        super.visualizar();
    }


    public List<Estado> getLista() {
        return estadoFacade.listar();
    }

    public List<Estado> completarEstaEntidade(String parte) {
        return estadoFacade.listarFiltrando(parte.trim(), "titulo", "id");
    }

    public List<SelectItem> selectItensEstado() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        toReturn.add(new SelectItem(null, ""));
        for (Estado catDaVez : estadoFacade.listar()) {
            toReturn.add(new SelectItem(catDaVez, catDaVez.toString()));
        }
        return toReturn;
    }
}
