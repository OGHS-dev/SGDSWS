package com.oghs.sgdsws.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.oghs.sgdsws.model.Estatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author oghs
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "TBL_ROL", uniqueConstraints = {@UniqueConstraint(columnNames = {"codigo", "descripcion"})})
public class Rol extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROL")
    private Long idRol;

    @Column(name = "CODIGO", nullable = false)
    private String codigo;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "ESTATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Estatus estatus;
    
}
