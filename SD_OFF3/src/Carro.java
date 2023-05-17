import java.io.Serializable;

public class Carro implements Serializable {
    private String renavan;
    private String nome;
    private int anoDeFabricacao;
    private int quantidadeDisponivel;
    private double preco;
    private Categoria categoria;

    public enum Categoria {
        ECONOMICO,
        INTERMEDIARIO,
        EXECUTIVO
    }

    public Carro(String renavan, String nome, int anoDeFabricacao, int quantidadeDisponivel, double preco,
            Categoria categoria) {
        this.renavan = renavan;
        this.nome = nome;
        this.anoDeFabricacao = anoDeFabricacao;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.preco = preco;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Carro [Renavan: " + renavan + ", Nome: " + nome + ", Ano de Fabricação: " + anoDeFabricacao +
                ", Quantidade Disponível: " + quantidadeDisponivel + ", Preço: R$" + preco + ", Categoria: " + categoria
                + "]";
    }

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
}