package com.rocktionary.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cancion.
 */
@Entity
@Table(name = "cancion")
public class Cancion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "duracion")
    private Double duracion;

    @Column(name = "letra")
    private String letra;

    @ManyToOne
    private Album album;

    @OneToMany(mappedBy = "cancion")
    @JsonIgnore
    private Set<PuntuacionCancion> puntuaciones = new HashSet<>();

    @OneToMany(mappedBy = "cancion")
    @JsonIgnore
    private Set<ComentarCancion> comentarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Cancion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getDuracion() {
        return duracion;
    }

    public Cancion duracion(Double duracion) {
        this.duracion = duracion;
        return this;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public String getLetra() {
        return letra;
    }

    public Cancion letra(String letra) {
        this.letra = letra;
        return this;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public Album getAlbum() {
        return album;
    }

    public Cancion album(Album album) {
        this.album = album;
        return this;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Set<PuntuacionCancion> getPuntuaciones() {
        return puntuaciones;
    }

    public Cancion puntuaciones(Set<PuntuacionCancion> puntuacionCancions) {
        this.puntuaciones = puntuacionCancions;
        return this;
    }

    public Cancion addPuntuacione(PuntuacionCancion puntuacionCancion) {
        this.puntuaciones.add(puntuacionCancion);
        puntuacionCancion.setCancion(this);
        return this;
    }

    public Cancion removePuntuacione(PuntuacionCancion puntuacionCancion) {
        this.puntuaciones.remove(puntuacionCancion);
        puntuacionCancion.setCancion(null);
        return this;
    }

    public void setPuntuaciones(Set<PuntuacionCancion> puntuacionCancions) {
        this.puntuaciones = puntuacionCancions;
    }

    public Set<ComentarCancion> getComentarios() {
        return comentarios;
    }

    public Cancion comentarios(Set<ComentarCancion> comentarCancions) {
        this.comentarios = comentarCancions;
        return this;
    }

    public Cancion addComentario(ComentarCancion comentarCancion) {
        this.comentarios.add(comentarCancion);
        comentarCancion.setCancion(this);
        return this;
    }

    public Cancion removeComentario(ComentarCancion comentarCancion) {
        this.comentarios.remove(comentarCancion);
        comentarCancion.setCancion(null);
        return this;
    }

    public void setComentarios(Set<ComentarCancion> comentarCancions) {
        this.comentarios = comentarCancions;
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
        Cancion cancion = (Cancion) o;
        if (cancion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cancion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cancion{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", duracion='" + getDuracion() + "'" +
            ", letra='" + getLetra() + "'" +
            "}";
    }
}
