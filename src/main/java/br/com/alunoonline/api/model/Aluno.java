package br.com.alunoonline.api.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Aluno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser em branco")
    @Size(min = 2, max = 150, message = "O nome deve ter entre 2 e 30 caracteres")
    private String name;

    @Email(message = "Informe um e-mail válido para o campo, deve conter @")
    private String email;

    @CPF
    private String cpf;

    private Integer anoNascimento;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    private Endereco endereco;
}
