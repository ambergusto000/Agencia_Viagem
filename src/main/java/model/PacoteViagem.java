package model;

public class PacoteViagem {
    private int id;
    private String nome, destino, tipo;
    private int duracao;
    private double preco;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
}
