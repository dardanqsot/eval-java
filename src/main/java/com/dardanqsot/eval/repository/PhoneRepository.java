package com.dardanqsot.eval.repository;

import com.dardanqsot.eval.model.Phone;
import com.dardanqsot.eval.model.User;

import java.util.List;

public interface PhoneRepository extends GenericRepo<Phone, Integer>{
    List<Phone> findByUser(User user);
}
