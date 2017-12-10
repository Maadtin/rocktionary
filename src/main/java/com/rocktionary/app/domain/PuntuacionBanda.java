package com.rocktionary.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PuntuacionBanda.
 */
@Entity
@Table(name = "puntuacion_banda")
public class PuntuacionBanda implements Serializable {

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
    private Banda banda;

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

    public PuntuacionBanda valoracion(Integer valoracion) {
        this.valoracion = valoracion;
        return this;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public ZonedDateTime getFechaPuntuacion() {
        return fechaPuntuacion;
    }

    public PuntuacionBanda fechaPuntuacion(ZonedDateTime fechaPuntuacion) {
        this.fechaPuntuacion = fechaPuntuacion;
        return this;
    }

    public void setFechaPuntuacion(ZonedDateTime fechaPuntuacion) {
        this.fechaPuntuacion = fechaPuntuacion;
    }

    public User getUser() {
        return user;
    }

    public PuntuacionBanda user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Banda getBanda() {
        return banda;
    }

    public PuntuacionBanda banda(Banda banda) {
        this.banda = banda;
        return this;
    }

    public void setBanda(Banda banda) {
        this.banda = banda;
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
        PuntuacionBanda puntuacionBanda = (PuntuacionBanda) o;
        if (puntuacionBanda.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), puntuacionBanda.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PuntuacionBanda{" +
            "id=" + getId() +
            ", valoracion='" + getValoracion() + "'" +
            ", fechaPuntuacion='" + getFechaPuntuacion() + "'" +
            "}";
    }
}
