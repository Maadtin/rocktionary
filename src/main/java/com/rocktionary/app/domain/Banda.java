package com.rocktionary.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Banda.
 */
@Entity
@Table(name = "banda")
public class Banda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "localizacion")
    private String localizacion;

    @Column(name = "datacreacion")
    private LocalDate datacreacion;

    @Column(name = "anosactivo")
    private LocalDate anosactivo;

    @Column(name = "temas")
    private String temas;

    @Column(name = "discografica")
    private String discografica;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "pais")
    private String pais;

    @Column(name = "estado")
    private String estado;

    @Column(name = "genero")
    private String genero;

    @Column(name = "biografia")
    private String biografia;

    @ManyToMany
    @JoinTable(name = "banda_discografica",
               joinColumns = @JoinColumn(name="bandas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="discograficas_id", referencedColumnName="id"))
    private Set<Discografica> discograficas = new HashSet<>();

    @OneToMany(mappedBy = "banda")
    @JsonIgnore
    private Set<Componente> componentes = new HashSet<>();

    @OneToMany(mappedBy = "banda")
    @JsonIgnore
    private Set<PuntuacionBanda> puntuaciones = new HashSet<>();

    @OneToMany(mappedBy = "banda")
    @JsonIgnore
    private Set<ComentarBanda> comentarios = new HashSet<>();

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

    public Banda nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public Banda localizacion(String localizacion) {
        this.localizacion = localizacion;
        return this;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public LocalDate getDatacreacion() {
        return datacreacion;
    }

    public Banda datacreacion(LocalDate datacreacion) {
        this.datacreacion = datacreacion;
        return this;
    }

    public void setDatacreacion(LocalDate datacreacion) {
        this.datacreacion = datacreacion;
    }

    public LocalDate getAnosactivo() {
        return anosactivo;
    }

    public Banda anosactivo(LocalDate anosactivo) {
        this.anosactivo = anosactivo;
        return this;
    }

    public void setAnosactivo(LocalDate anosactivo) {
        this.anosactivo = anosactivo;
    }

    public String getTemas() {
        return temas;
    }

    public Banda temas(String temas) {
        this.temas = temas;
        return this;
    }

    public void setTemas(String temas) {
        this.temas = temas;
    }

    public String getDiscografica() {
        return discografica;
    }

    public Banda discografica(String discografica) {
        this.discografica = discografica;
        return this;
    }

    public void setDiscografica(String discografica) {
        this.discografica = discografica;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Banda foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Banda fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Banda logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Banda logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getPais() {
        return pais;
    }

    public Banda pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public Banda estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getGenero() {
        return genero;
    }

    public Banda genero(String genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getBiografia() {
        return biografia;
    }

    public Banda biografia(String biografia) {
        this.biografia = biografia;
        return this;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public Set<Discografica> getDiscograficas() {
        return discograficas;
    }

    public Banda discograficas(Set<Discografica> discograficas) {
        this.discograficas = discograficas;
        return this;
    }

    public Banda addDiscografica(Discografica discografica) {
        this.discograficas.add(discografica);
        discografica.getBandas().add(this);
        return this;
    }

    public Banda removeDiscografica(Discografica discografica) {
        this.discograficas.remove(discografica);
        discografica.getBandas().remove(this);
        return this;
    }

    public void setDiscograficas(Set<Discografica> discograficas) {
        this.discograficas = discograficas;
    }

    public Set<Componente> getComponentes() {
        return componentes;
    }

    public Banda componentes(Set<Componente> componentes) {
        this.componentes = componentes;
        return this;
    }

    public Banda addComponente(Componente componente) {
        this.componentes.add(componente);
        componente.setBanda(this);
        return this;
    }

    public Banda removeComponente(Componente componente) {
        this.componentes.remove(componente);
        componente.setBanda(null);
        return this;
    }

    public void setComponentes(Set<Componente> componentes) {
        this.componentes = componentes;
    }

    public Set<PuntuacionBanda> getPuntuaciones() {
        return puntuaciones;
    }

    public Banda puntuaciones(Set<PuntuacionBanda> puntuacionBandas) {
        this.puntuaciones = puntuacionBandas;
        return this;
    }

    public Banda addPuntuacione(PuntuacionBanda puntuacionBanda) {
        this.puntuaciones.add(puntuacionBanda);
        puntuacionBanda.setBanda(this);
        return this;
    }

    public Banda removePuntuacione(PuntuacionBanda puntuacionBanda) {
        this.puntuaciones.remove(puntuacionBanda);
        puntuacionBanda.setBanda(null);
        return this;
    }

    public void setPuntuaciones(Set<PuntuacionBanda> puntuacionBandas) {
        this.puntuaciones = puntuacionBandas;
    }

    public Set<ComentarBanda> getComentarios() {
        return comentarios;
    }

    public Banda comentarios(Set<ComentarBanda> comentarBandas) {
        this.comentarios = comentarBandas;
        return this;
    }

    public Banda addComentario(ComentarBanda comentarBanda) {
        this.comentarios.add(comentarBanda);
        comentarBanda.setBanda(this);
        return this;
    }

    public Banda removeComentario(ComentarBanda comentarBanda) {
        this.comentarios.remove(comentarBanda);
        comentarBanda.setBanda(null);
        return this;
    }

    public void setComentarios(Set<ComentarBanda> comentarBandas) {
        this.comentarios = comentarBandas;
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
        Banda banda = (Banda) o;
        if (banda.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), banda.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Banda{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", localizacion='" + getLocalizacion() + "'" +
            ", datacreacion='" + getDatacreacion() + "'" +
            ", anosactivo='" + getAnosactivo() + "'" +
            ", temas='" + getTemas() + "'" +
            ", discografica='" + getDiscografica() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + logoContentType + "'" +
            ", pais='" + getPais() + "'" +
            ", estado='" + getEstado() + "'" +
            ", genero='" + getGenero() + "'" +
            ", biografia='" + getBiografia() + "'" +
            "}";
    }
}
