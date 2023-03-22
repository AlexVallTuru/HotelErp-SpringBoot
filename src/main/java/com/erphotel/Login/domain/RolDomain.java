package com.erphotel.Login.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name="rols")
public class RolDomain implements Serializable{

        private static final long serialVersionUID=1L;

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private long id_rol;

        @NotEmpty
        private String name;

        //PREGUNTAR SI ES NECESARIO YA QUE EN SU EJEMPLO NO ESTA
        //@ManyToOne
        //@JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
        //private LoginDomain login;

}
