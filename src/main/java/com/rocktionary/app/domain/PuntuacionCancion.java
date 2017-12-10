package com.rocktionary.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PuntuacionCancion.
 */
@Entity
@Table(name = "puntuacion_cancion")
public class PuntuacionCancion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valoracion")
    private Integer valoracion;

    @Column(name = "fecha_puntuacion")
    private ZonedDateTime fechaPuntuacion;

    @ManyToOne
    private User user;

    @ManyToOne
    private Cancion cancion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public PuntuacionCancion valoracion(Integer valoracion) {
        this.valoracion = valoracion;
        return this;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public ZonedDateTime getFechaPuntuacion() {
        return fechaPuntuacion;
    }

    public PuntuacionCancion fechaPuntuacion(ZonedDateTime fechaPuntuacion) {
        this.fechaPuntuacion = fechaPuntuacion;
        return this;
    }

    public void setFechaPuntuacion(ZonedDateTime fechaPuntuacion) {
        this.fechaPuntuacion = fechaPuntuacion;
    }

    public User getUser() {
        return user;
    }

    public PuntuacionCancion user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public PuntuacionCancion cancion(Cancion cancion) {
        this.cancion = cancion;
        return this;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PuntuacionCancion puntuacionCancion = (PuntuacionCancion) o;
        if (puntuacionCancion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), puntuacionCancion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PuntuacionCancion{" +
            "id=" + getId() +
            ", valoracion='" + getValoracion() + "'" +
            ", fechaPuntuacion='" + getFechaPuntuacion() + "'" +
            "}";
    }
}
