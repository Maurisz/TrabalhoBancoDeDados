package model.entities;

import java.sql.Date;
import java.util.Objects;


public class Animal {
    protected int id;
    private String nome;
    private String especie;
    private String raca;
    private Date dataNascimento;
    private double peso;

    public Animal(String nome, String especie, String raca, Date data, double peso){
        this.nome=nome;
        this.especie=especie;
        this.raca=raca;
        this.dataNascimento=data;
        this.peso=peso;
    }

    public Date getDataNascimento(){
        return dataNascimento;
    }
    public double getPeso(){
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

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id == animal.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
