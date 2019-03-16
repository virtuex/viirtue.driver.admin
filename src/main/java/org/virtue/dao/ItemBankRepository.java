package org.virtue.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.virtue.domain.ItemBank;

import java.util.List;

@Repository
public interface ItemBankRepository extends JpaRepository<ItemBank, Long> {
    ItemBank findItemBankByItemBankId(long id);
    List<ItemBank> findItemBankByItemBankSubjectType(Integer type);
    List<ItemBank> findItemBankByItemBankDifficutLevel(Integer type);
    List<ItemBank> findItemBankByItemBankKownledgeType(Integer type);

}
