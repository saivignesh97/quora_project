package com.upgrad.quora.service.business;


import com.upgrad.quora.service.dao.UserAuthDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBusinessService {

    @Autowired
    private UserAuthDao userAuthDao;

    @Autowired
    private UserDao userDao;

    /**
     * @param accesstoken
     * @return Returns User Auth Entity
     * @throws AuthorizationFailedException
     * @throws UserNotFoundException
     */
    public UserAuthEntity getAccessToken(final String accesstoken) throws AuthorizationFailedException, UserNotFoundException {

        UserAuthEntity userAuthEntity = userAuthDao.getAccessToken(accesstoken);
        if (userAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        if (userAuthEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get user details");
        }
        return userAuthEntity;

    }

    /**
     * @param uuid
     * @return Returns the user entity with given uuid
     * @throws UserNotFoundException
     */
    public UserEntity getUserByUUID(String uuid) throws UserNotFoundException {
        UserEntity userEntity = userDao.getUserByUUID(uuid);
        if (userEntity == null) {
            throw new UserNotFoundException("USR-001", "User with entered uuid does not exist");
        }
        return userEntity;
    }
}
