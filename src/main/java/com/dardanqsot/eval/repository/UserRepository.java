package com.dardanqsot.eval.repository;

import com.dardanqsot.eval.model.User;

public interface UserRepository extends GenericRepo<User, Integer>{

    User findOneByEmail(String email);
}
