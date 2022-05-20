package com.pmp.pensionerauth.modelrepo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pmp.pensionerauth.model.DAOUser;

  
@Repository
public interface DAOUserR extends JpaRepository<DAOUser, Integer> {
	
	DAOUser findByUserId(Long userId);

	DAOUser findByUserIdAndPassword(Long userId, String password);

	DAOUser findByPassword(String password);
}
