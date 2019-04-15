package org.virtue.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.virtue.domain.DriverUser;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<DriverUser, Long> {

     List<DriverUser> findUserByusername(String username);
     List<DriverUser> findUserByUsernameAndPassword(String username,String password);
     DriverUser findUserByid(Long id);
}
