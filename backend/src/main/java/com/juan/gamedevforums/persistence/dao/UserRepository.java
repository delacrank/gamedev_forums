package com.juan.gamedevforums.persistence.dao;

import com.juan.gamedevforums.persistence.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByEmailIgnoreCase(String email);
    Boolean existsByUsernameIgnoreCase(String username);
    Boolean existsByEmailIgnoreCase(String email);
    Optional<User> findByUsername(String username);

    @Override
    void delete(User user);

}
