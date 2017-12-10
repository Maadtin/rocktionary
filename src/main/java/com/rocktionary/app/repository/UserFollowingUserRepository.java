package com.rocktionary.app.repository;

import com.rocktionary.app.domain.UserFollowingUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the UserFollowingUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserFollowingUserRepository extends JpaRepository<UserFollowingUser, Long> {

    @Query("select user_following_user from UserFollowingUser user_following_user where user_following_user.usuarioOrigen.login = ?#{principal.username}")
    List<UserFollowingUser> findByUsuarioOrigenIsCurrentUser();

    @Query("select user_following_user from UserFollowingUser user_following_user where user_following_user.usuarioDestino.login = ?#{principal.username}")
    List<UserFollowingUser> findByUsuarioDestinoIsCurrentUser();

}
