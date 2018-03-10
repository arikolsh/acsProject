package com.acsproject.contacts;

import com.acsproject.alert.AlertType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ToString
@Getter
@AllArgsConstructor
@Table(name = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "type")
    private AlertType type;

    @Column(name = "target")
    private String target;

    protected Contact() {
    }
}