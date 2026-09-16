package com.oghs.sgdsws.model.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author oghs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable implements Serializable {
    
    @Column(name = "USUARIO_CREACION", updatable = false)
    @CreatedBy
    protected String usuarioCreacion;

    @Column(name = "FECHA_CREACION", updatable = false)
    // @Temporal(TemporalType.TIMESTAMP)
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    protected Date fechaCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    @LastModifiedBy
    protected String usuarioModificacion;

    @Column(name = "FECHA_MODIFICACION")
    // @Temporal(TemporalType.TIMESTAMP)
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    @LastModifiedDate
    protected Date fechaModificacion;

}
