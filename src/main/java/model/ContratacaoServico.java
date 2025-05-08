package model;

import java.time.LocalDate;

public class ContratacaoServico {
    private int id;
    private int clienteId;
    private int pacoteId;
    private int servicoId;
    private LocalDate dataContratacao;
    private int contratoId; // Adicionado para suportar o DAO

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public int getPacoteId() { return pacoteId; }
    public void setPacoteId(int pacoteId) { this.pacoteId = pacoteId; }

    public int getServicoId() { return servicoId; }
    public void setServicoId(int servicoId) { this.servicoId = servicoId; }

    public LocalDate getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }

    public int getContratoId() { return contratoId; } // Adicionado
    public void setContratoId(int contratoId) { this.contratoId = contratoId; } // Adicionado
}