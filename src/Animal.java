import java.sql.Date;


public class Animal {
    private String nome;
    private String especie;
    private String raca;
    private Date dataNascimento;
    private double peso;
    private Proprietario dono;

    public Animal(String nome, String especie, String raca, Date data, double peso, Proprietario proprietario){
        this.nome=nome;
        this.especie=especie;
        this.raca=raca;
        this.dataNascimento=data;
        this.peso=peso;
        this.dono=proprietario;
    }

    public Proprietario getDono(){
        return dono;
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

}
