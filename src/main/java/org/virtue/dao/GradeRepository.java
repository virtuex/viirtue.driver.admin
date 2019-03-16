package org.virtue.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.virtue.domain.Grade;

import java.util.List;

@Repository
public interface GradeRepository  extends JpaRepository<Grade, Long> {
    List<Grade> findByUserId(long id);
}
