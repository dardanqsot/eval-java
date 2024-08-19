package com.dardanqsot.eval.repository;

import com.dardanqsot.eval.model.User;

import java.util.UUID;

public interface UserRepository extends GenericRepo<User, Integer>{

    User findOneByEmail(String email);
    User findByIdUser(UUID uuid);
}
