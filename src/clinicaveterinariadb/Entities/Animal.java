package clinicaveterinariadb.Entities;

import java.math.BigDecimal;
import java.sql.Date;


public class Animal {
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private Date dataNascimento;
    private BigDecimal peso;
    private String cpfDono;
    private String nomeDono;

    @Override
    public String toString() {
        return "\nID : "+id+
                "\nNome : "+nome+
                "\nEspecie : "+especie+
                "\nRaca : "+raca+
                "\nData de Nascimento : "+dataNascimento+
                "\nPeso : "+peso+
                "\nNome do Dono : "+nomeDono;
    }

    public String getNomeDono() {
        return nomeDono;
    }

    public void setNomeDono(String nomeDono) {
        this.nomeDono = nomeDono;
    }

    public Animal(String nome, String especie, String raca, Date data, BigDecimal peso){
        this.nome=nome;
        this.especie=especie;
        this.raca=raca;
        this.dataNascimento=data;
        this.peso=peso;
    }

    public Date getDataNascimento(){
        return dataNascimento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getPeso(){
        return peso;
    }
    public String getRaca(){
        return raca;
    }
    public String getEspecie(){
        return especie;
    }
    public String getNome(){
        return nome;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCpfDono() {
        return cpfDono;
    }

    public void setCpfDono(String cpfDono) {
        this.cpfDono = cpfDono;
    }
}
