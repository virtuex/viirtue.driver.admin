package org.virtue.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     List<User> findUserByusername(String username);
     User findUserByid(Long id);

}
