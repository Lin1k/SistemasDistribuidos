import java.io.Serializable;

public class Carro implements Serializable, Comparable<Carro> {
    private String renavan;
    private String nome;
    private int anoDeFabricacao;
    private int quantidadeDisponivel;
    private double preco;
    private Categoria categoria;

    public enum Categoria {
        ECONOMICO,
        INTERMEDIARIO,
        EXECUTIVO,
        CARRO
    }

    public Carro(String renavan, String nome, Categoria categoria, int quantidadeDisponivel, double preco,
            int anoDeFabricacao) {
        this.renavan = renavan;
        this.nome = nome;
        this.categoria = categoria;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.preco = preco;
        this.anoDeFabricacao = anoDeFabricacao;
    }

    @Override
    public String toString() {
        return "Carro [Renavan: " + renavan + ", Nome: " + nome + ", Ano de Fabricação: " + anoDeFabricacao +
                ", Quantidade Disponível: " + quantidadeDisponivel + ", Preço: R$" + preco + ", Categoria: " + categoria
                + "]";
    }

    public String getRenavan() {
        return renavan;
    }

    public void setRenavan(String renavan) {
        this.renavan = renavan;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnoDeFabricacao() {
        return anoDeFabricacao;
    }

    public void setAnoDeFabricacao(int anoDeFabricacao) {
        this.anoDeFabricacao = anoDeFabricacao;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public int compareTo(Carro o) {
        return this.nome.compareTo(o.getNome());
    }
}