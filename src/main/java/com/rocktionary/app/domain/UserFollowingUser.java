package com.rocktionary.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserFollowingUser.
 */
@Entity
@Table(name = "user_following_user")
public class UserFollowingUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "since")
    private ZonedDateTime since;

    @ManyToOne
    private User usuarioOrigen;

    @ManyToOne
    private User usuarioDestino;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getSince() {
        return since;
    }

    public UserFollowingUser since(ZonedDateTime since) {
        this.since = since;
        return this;
    }

    public void setSince(ZonedDateTime since) {
        this.since = since;
    }

    public User getUsuarioOrigen() {
        return usuarioOrigen;
    }

    public UserFollowingUser usuarioOrigen(User user) {
        this.usuarioOrigen = user;
        return this;
    }

    public void setUsuarioOrigen(User user) {
        this.usuarioOrigen = user;
    }

    public User getUsuarioDestino() {
        return usuarioDestino;
    }

    public UserFollowingUser usuarioDestino(User user) {
        this.usuarioDestino = user;
        return this;
    }

    public void setUsuarioDestino(User user) {
        this.usuarioDestino = user;
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
        UserFollowingUser userFollowingUser = (UserFollowingUser) o;
        if (userFollowingUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userFollowingUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserFollowingUser{" +
            "id=" + getId() +
            ", since='" + getSince() + "'" +
            "}";
    }
}
