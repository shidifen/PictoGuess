package org.sdr.dao;

import org.sdr.model.User;

public interface UserDao {

        public void insert(User user);
        public User findByName(String name);
}


