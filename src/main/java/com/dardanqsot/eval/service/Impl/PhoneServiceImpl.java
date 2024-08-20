package com.dardanqsot.eval.service.Impl;

import com.dardanqsot.eval.model.Phone;
import com.dardanqsot.eval.model.User;
import com.dardanqsot.eval.repository.GenericRepo;
import com.dardanqsot.eval.repository.PhoneRepository;
import com.dardanqsot.eval.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PhoneServiceImpl extends CRUDImpl<Phone, Integer> implements PhoneService {


    private final PhoneRepository repo;

    @Override
    protected GenericRepo<Phone, Integer> getRepo() {
        return repo;
    }


    @Override
    public List<Phone> findByUser(User user) {
        return repo.findByUser(user);
    }
}