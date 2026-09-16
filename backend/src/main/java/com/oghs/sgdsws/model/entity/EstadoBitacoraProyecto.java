package com.oghs.sgdsws.model.entity;

import java.util.Set;

import com.oghs.sgdsws.model.Estatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "TBL_ESTADO_BITACORA_PROYECTO")
public class EstadoBitacoraProyecto extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTADO_BITACORA_PROYECTO")
    private Long idEstadoBitacoraProyecto;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "ESTATUS")
    private Estatus estatus;

    @OneToMany(mappedBy = "estadoBitacoraProyecto")
    private Set<BitacoraProyecto> bitacoraProyecto;
    
}
