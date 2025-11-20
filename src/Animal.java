import java.sql.Date;


public class Animal {
    private int id;
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
}
