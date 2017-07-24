/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.exemplo.controladores;

import br.com.exemplo.entidades.Cidade;
import br.com.exemplo.entidades.Estado;
import br.com.exemplo.negocios.EstadoFacade;
import br.com.exemplo.util.ConverterGenerico;
import br.com.exemplo.negocios.CidadeFacade;
import br.com.exemplo.supers.SuperControlador;
import br.com.exemplo.supers.SuperFacade;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLAction.PhaseId;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;

@ManagedBean(name = "cidadeControlador")
@ViewScoped
@URLMappings(mappings = {
    @URLMapping(id = "novo-cidade", pattern = "/cadastro/cidade/novo/", viewId = "/faces/interno/cadastro/cidade/editar.xhtml"),
    @URLMapping(id = "editar-cidade", pattern = "/cadastro/cidade/editar/#{cidadeControlador.id}/", viewId = "/faces/interno/cadastro/cidade/editar.xhtml"),
    @URLMapping(id = "listar-cidade", pattern = "/cadastro/cidade/listar/", viewId = "/faces/interno/cadastro/cidade/listar.xhtml"),
    @URLMapping(id = "ver-cidade", pattern = "/cadastro/cidade/ver/#{cidadeControlador.id}/", viewId = "/faces/interno/cadastro/cidade/visualizar.xhtml")
})
public class CidadeControlador extends SuperControlador<Cidade> implements Serializable {

    @Autowired
    private CidadeFacade cidadeFacade;
    @Autowired
    private EstadoFacade estadoFacade;
    private Converter converterEstado;

    public CidadeControlador() {
        super(Cidade.class);
    }

    public Converter getConverterEstado() {
        if (converterEstado == null) {
            converterEstado = new ConverterGenerico(Estado.class, estadoFacade);
        }
        return converterEstado;
    }

    @Override
    public SuperFacade getEsteFacade() {
        return cidadeFacade;
    }

    @URLAction(mappingId = "listar-cidade", phaseId = PhaseId.RENDER_RESPONSE, onPostback = false)
    @Override
    public void listar() {
        super.listar();
    }

    @URLAction(mappingId = "novo-cidade", phaseId = PhaseId.RENDER_RESPONSE, onPostback = false)
    @Override
    public void novo() {
        super.novo();
    }

    @URLAction(mappingId = "editar-cidade", phaseId = PhaseId.RENDER_RESPONSE, onPostback = false)
    @Override
    public void editar() {
        super.editar();
    }

    @URLAction(mappingId = "ver-cidade", phaseId = PhaseId.RENDER_RESPONSE, onPostback = false)
    @Override
    public void visualizar() {
        super.visualizar();
    }  

    public List<Cidade> completarEstaEntidade(String parte) {
        return cidadeFacade.listarFiltrando(parte.trim(), "titulo", "id");
    }

    public List<SelectItem> selectItensCidade() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        toReturn.add(new SelectItem(null, " "));
        for (Cidade catDaVez : cidadeFacade.listar()) {
            toReturn.add(new SelectItem(catDaVez.getId(), catDaVez.toString()));
        }
        return toReturn;
    }
}
