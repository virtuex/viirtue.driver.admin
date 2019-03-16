package org.virtue.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.virtue.domain.MyCollect;

import java.util.List;

public interface CollectRepository extends JpaRepository<MyCollect, Long> {
    List<MyCollect> findByItemBankId(long itemBankId);
    List<MyCollect> findByUserId(long userId);
    void removeByUserIdAndItemBankId(long userID,long itemId);
}
