package com.rocktionary.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ComentarAlbum.
 */
@Entity
@Table(name = "comentar_album")
public class ComentarAlbum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fecha_comentario")
    private ZonedDateTime fechaComentario;

    @ManyToOne
    private User user;

    @ManyToOne
    private Album album;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public ComentarAlbum comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ZonedDateTime getFechaComentario() {
        return fechaComentario;
    }

    public ComentarAlbum fechaComentario(ZonedDateTime fechaComentario) {
        this.fechaComentario = fechaComentario;
        return this;
    }

    public void setFechaComentario(ZonedDateTime fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public User getUser() {
        return user;
    }

    public ComentarAlbum user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Album getAlbum() {
        return album;
    }

    public ComentarAlbum album(Album album) {
        this.album = album;
        return this;
    }

    public void setAlbum(Album album) {
        this.album = album;
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
        ComentarAlbum comentarAlbum = (ComentarAlbum) o;
        if (comentarAlbum.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comentarAlbum.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComentarAlbum{" +
            "id=" + getId() +
            ", comentario='" + getComentario() + "'" +
            ", fechaComentario='" + getFechaComentario() + "'" +
            "}";
    }
}
