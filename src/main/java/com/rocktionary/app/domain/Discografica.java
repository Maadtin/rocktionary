package com.rocktionary.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Discografica.
 */
@Entity
@Table(name = "discografica")
public class Discografica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "discografica")
    @JsonIgnore
    private Set<Album> albums = new HashSet<>();

    @ManyToMany(mappedBy = "discograficas")
    @JsonIgnore
    private Set<Banda> bandas = new HashSet<>();

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

    public Discografica nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public Discografica albums(Set<Album> albums) {
        this.albums = albums;
        return this;
    }

    public Discografica addAlbum(Album album) {
        this.albums.add(album);
        album.setDiscografica(this);
        return this;
    }

    public Discografica removeAlbum(Album album) {
        this.albums.remove(album);
        album.setDiscografica(null);
        return this;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Set<Banda> getBandas() {
        return bandas;
    }

    public Discografica bandas(Set<Banda> bandas) {
        this.bandas = bandas;
        return this;
    }

    public Discografica addBanda(Banda banda) {
        this.bandas.add(banda);
        banda.getDiscograficas().add(this);
        return this;
    }

    public Discografica removeBanda(Banda banda) {
        this.bandas.remove(banda);
        banda.getDiscograficas().remove(this);
        return this;
    }

    public void setBandas(Set<Banda> bandas) {
        this.bandas = bandas;
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
        Discografica discografica = (Discografica) o;
        if (discografica.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discografica.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Discografica{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
