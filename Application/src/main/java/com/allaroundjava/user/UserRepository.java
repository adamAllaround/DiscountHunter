package com.allaroundjava.user;

import com.allaroundjava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long> {
}
