package com.rtechnologies.youtubetool.Repository;

import com.rtechnologies.youtubetool.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
