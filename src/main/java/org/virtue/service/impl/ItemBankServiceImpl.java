package org.virtue.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.virtue.domain.ItemBank;
import org.virtue.dao.ItemBankRepository;
import org.virtue.service.ItemBankService;

import java.util.List;
@Service
public class ItemBankServiceImpl implements ItemBankService {
    @Autowired
    private ItemBankRepository itemBankRepository;

    @Override
    public List<ItemBank> findAllItemBanks() {
        return itemBankRepository.findAll();
    }
}
