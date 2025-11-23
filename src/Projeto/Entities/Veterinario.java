package Projeto.Entities;

public class Veterinario {
    private String nome;
    private String CRMV;
    private String especialidade;
    private String telefone;

    public Veterinario(String nome, String CRMV, String especialidade, String telefone){
        this.telefone=telefone;
        this.nome=nome;
        this.CRMV=CRMV;
        this.especialidade=especialidade;
    }

    public String getCRMV() {
        return CRMV;
    }
    public String getEspecialidade() {
        return especialidade;
    }
    public String getNome() {
        return nome;
    }
    public String getTelefone() {
        return telefone;
    }

    public void setCRMV(String CRMV) {
        this.CRMV = CRMV;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
