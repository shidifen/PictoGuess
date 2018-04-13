package org.sdr.dao;

import org.sdr.model.Picture;
import org.sdr.model.User;

public interface PictureDao {

        public void insertUserPicture (User user, Picture picture);
        public Picture findById(long id);
}
