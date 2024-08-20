package com.dardanqsot.eval.service;

import com.dardanqsot.eval.model.Phone;
import com.dardanqsot.eval.model.User;

import java.util.List;

public interface PhoneService extends CRUD<Phone, Integer> {

    List<Phone> findByUser(User user);
}
