package br.com.alunoonline.api.model;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.validator.constraints.br.CPF;

import br.com.alunoonline.api.validators.EmailValidation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Professor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O Nome do professor não pode ser vazio")
    @Size(min = 2, max = 150, message = "O nome do professor " +
            "deve ser entre 2 e 150 caracteres")
    private String name;

    @EmailValidation
    private String email;

    @CPF
    private String cpf;

    @OneToMany(mappedBy = "professor")
    private Set<Disciplina> disciplinas;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    private Endereco endereco;

}
