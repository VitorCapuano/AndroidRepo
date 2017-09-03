package com.example.logonrm.chaveirochamado.model;

/**
 * Created by logonrm on 29/08/2017.
 */

public class Chamado {
    private int codFunc;
    private String sppinerContent;
    private boolean finalizado;

    public Chamado(int codFunc, String sppinerContent, boolean finalizado) {
        this.codFunc = codFunc;
        this.sppinerContent = sppinerContent;
        this.finalizado = finalizado;
    }

    @Override
    public String toString() {
        return "Chamado{" +
                "codFunc=" + codFunc +
                ", sppinerContent='" + sppinerContent + '\'' +
                ", finalizado=" + finalizado +
                '}';
    }

    public int getCodFunc() {
        return codFunc;
    }

    public void setCodFunc(int codFunc) {
        this.codFunc = codFunc;
    }

    public String getSppinerContent() {
        return sppinerContent;
    }

    public void setSppinerContent(String sppinerContent) {
        this.sppinerContent = sppinerContent;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
}
