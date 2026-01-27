public class Pet {
    private String nome;
    private TIPO tipo;
    private SEXO sexo;
    private String rua;
    private Integer casa;
    private String bairro;
    private String cidade;
    private Double idade;
    private Double peso;
    private String raca;

    public Pet() {
    }

    public Pet(String nome, TIPO tipo, SEXO sexo, String rua, Integer casa, String bairro, String cidade, Double idade, Double peso, String raca) {
        this.nome = nome;
        this.tipo = tipo;
        this.sexo = sexo;
        this.rua = rua;
        this.casa = casa;
        this.bairro = bairro;
        this.cidade = cidade;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TIPO getTipo() {
        return tipo;
    }

    public void setTipo(TIPO tipo) {
        this.tipo = tipo;
    }

    public SEXO getSexo() {
        return sexo;
    }

    public void setSexo(SEXO sexo) {
        this.sexo = sexo;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getCasa() {
        return casa;
    }

    public void setCasa(Integer casa) {
        this.casa = casa;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Double getIdade() {
        return idade;
    }

    public void setIdade(Double idade) {
        this.idade = idade;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "nome='" + nome + '\'' +
                ", tipo=" + tipo +
                ", sexo=" + sexo +
                ", idade=" + idade +
                ", peso=" + peso +
                ", raca='" + raca + '\'' +
                ", endereco='" + rua + ", " + casa + " - " + bairro + " / " + cidade + '\'' +
                '}';
    }
}

