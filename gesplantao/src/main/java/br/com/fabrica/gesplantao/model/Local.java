package br.com.fabrica.gesplantao.model;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tb_local")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor

public class Local implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;


    @Column(name = "siglaLocal", nullable = false)
    private String siglaLocal;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @OneToMany(mappedBy = "local")
    private List<Rodizio> rodizio = new ArrayList<>();


    public Local(String nome, String siglaLocal, String endereco) {
        this.nome = nome;
        this.siglaLocal = siglaLocal;
        this.endereco = endereco;

    }

    public static Builder builder() {
        return new Builder();
    }

    public String getNome() {
        return nome;
    }

    public String getSiglaLocal() {
        return siglaLocal;
    }

    public String getEndereco() {
        return endereco;
    }

    public static class Builder {
        private String nome;
        private String siglaLocal;
        private String endereco;

        private Builder() {
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder siglaLocal(String siglaLocal) {
            this.siglaLocal = siglaLocal;
            return this;
        }

        public Builder endereco(String endereco) {
            this.endereco = endereco;
            return this;
        }

        public Local build() {
            Local local = new Local();
            local.nome = this.nome;
            local.siglaLocal = this.siglaLocal;
            local.endereco = this.endereco;
            return local;
        }
    }
}

