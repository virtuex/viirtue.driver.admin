package org.virtue.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.virtue.domain.ItemBank;

@Repository
public interface ItemBankRepository extends JpaRepository<ItemBank, Long> {
    ItemBank findItemBankByItemBankId(long id);
}
