package com.rocktionary.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Album.
 */
@Entity
@Table(name = "album")
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "genero")
    private String genero;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "num_canciones")
    private Integer numCanciones;

    @Column(name = "formato")
    private String formato;

    @Column(name = "num_copias")
    private Integer numCopias;

    @Column(name = "reviews")
    private String reviews;

    @ManyToOne
    private Discografica discografica;

    @OneToMany(mappedBy = "album")
    @JsonIgnore
    private Set<PuntuacionAlbum> puntuaciones = new HashSet<>();

    @OneToMany(mappedBy = "album")
    @JsonIgnore
    private Set<ComentarAlbum> comentarios = new HashSet<>();

    @OneToMany(mappedBy = "album")
    @JsonIgnore
    private Set<Cancion> canciones = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenero() {
        return genero;
    }

    public Album genero(String genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public Album nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumCanciones() {
        return numCanciones;
    }

    public Album numCanciones(Integer numCanciones) {
        this.numCanciones = numCanciones;
        return this;
    }

    public void setNumCanciones(Integer numCanciones) {
        this.numCanciones = numCanciones;
    }

    public String getFormato() {
        return formato;
    }

    public Album formato(String formato) {
        this.formato = formato;
        return this;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Integer getNumCopias() {
        return numCopias;
    }

    public Album numCopias(Integer numCopias) {
        this.numCopias = numCopias;
        return this;
    }

    public void setNumCopias(Integer numCopias) {
        this.numCopias = numCopias;
    }

    public String getReviews() {
        return reviews;
    }

    public Album reviews(String reviews) {
        this.reviews = reviews;
        return this;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public Discografica getDiscografica() {
        return discografica;
    }

    public Album discografica(Discografica discografica) {
        this.discografica = discografica;
        return this;
    }

    public void setDiscografica(Discografica discografica) {
        this.discografica = discografica;
    }

    public Set<PuntuacionAlbum> getPuntuaciones() {
        return puntuaciones;
    }

    public Album puntuaciones(Set<PuntuacionAlbum> puntuacionAlbums) {
        this.puntuaciones = puntuacionAlbums;
        return this;
    }

    public Album addPuntuacione(PuntuacionAlbum puntuacionAlbum) {
        this.puntuaciones.add(puntuacionAlbum);
        puntuacionAlbum.setAlbum(this);
        return this;
    }

    public Album removePuntuacione(PuntuacionAlbum puntuacionAlbum) {
        this.puntuaciones.remove(puntuacionAlbum);
        puntuacionAlbum.setAlbum(null);
        return this;
    }

    public void setPuntuaciones(Set<PuntuacionAlbum> puntuacionAlbums) {
        this.puntuaciones = puntuacionAlbums;
    }

    public Set<ComentarAlbum> getComentarios() {
        return comentarios;
    }

    public Album comentarios(Set<ComentarAlbum> comentarAlbums) {
        this.comentarios = comentarAlbums;
        return this;
    }

    public Album addComentario(ComentarAlbum comentarAlbum) {
        this.comentarios.add(comentarAlbum);
        comentarAlbum.setAlbum(this);
        return this;
    }

    public Album removeComentario(ComentarAlbum comentarAlbum) {
        this.comentarios.remove(comentarAlbum);
        comentarAlbum.setAlbum(null);
        return this;
    }

    public void setComentarios(Set<ComentarAlbum> comentarAlbums) {
        this.comentarios = comentarAlbums;
    }

    public Set<Cancion> getCanciones() {
        return canciones;
    }

    public Album canciones(Set<Cancion> cancions) {
        this.canciones = cancions;
        return this;
    }

    public Album addCancione(Cancion cancion) {
        this.canciones.add(cancion);
        cancion.setAlbum(this);
        return this;
    }

    public Album removeCancione(Cancion cancion) {
        this.canciones.remove(cancion);
        cancion.setAlbum(null);
        return this;
    }

    public void setCanciones(Set<Cancion> cancions) {
        this.canciones = cancions;
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
        Album album = (Album) o;
        if (album.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), album.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Album{" +
            "id=" + getId() +
            ", genero='" + getGenero() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", numCanciones='" + getNumCanciones() + "'" +
            ", formato='" + getFormato() + "'" +
            ", numCopias='" + getNumCopias() + "'" +
            ", reviews='" + getReviews() + "'" +
            "}";
    }
}
