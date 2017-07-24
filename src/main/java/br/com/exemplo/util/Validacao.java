package br.com.exemplo.util;

import com.google.common.collect.Lists;

import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Validacao implements Serializable {

    private List<Mensagem> mensagens;

    private Validacao() {
        mensagens = new LinkedList<Mensagem>();
    }

    public boolean isValido() {
        return mensagens.isEmpty();
    }

    public static Validacao newValidacao() {
        return new Validacao();
    }

    public void addMensagem(String sumario, String detalhe) {
        mensagens.add(new Mensagem(sumario, detalhe));
    }

    public void addTodasMensagens(List<Mensagem> mensagens) {
        this.mensagens.addAll(mensagens);
    }

    public List<FacesMessage> getFacesMensages() {
        List<FacesMessage> mesages = Lists.newArrayList();
        for (Mensagem mensagen : mensagens) {
            mesages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagen.getDetalhe(), mensagen.getSumario()));
        }
        return mesages;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public class Mensagem {

        private String sumario;
        private String detalhe;

        private Mensagem() {
        }

        public Mensagem(String sumario, String detalhe) {
            this.detalhe = detalhe;
            this.sumario = sumario;
        }

        public String getDetalhe() {
            return detalhe;
        }

        public String getSumario() {
            return sumario;
        }
    }
}
