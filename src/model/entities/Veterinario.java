package model.entities;

import java.util.Objects;

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

    public void setNome(String nome) {
        this.nome = nome;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Veterinario that = (Veterinario) o;
        return Objects.equals(CRMV, that.CRMV) && Objects.equals(telefone, that.telefone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CRMV, telefone);
    }
}
