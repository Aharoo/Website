package com.aharoo.repository;

import com.aharoo.auth.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Integer> {

	Optional<ApplicationUser> findByUsername(String username);

	Optional<ApplicationUser> findByEmail(String email);

	@Transactional
	@Modifying
	@Query("UPDATE ApplicationUser a " +
			"SET a.enabled = TRUE WHERE a.email = ?1")
	int enableAppUser(String email);

	@Transactional
	@Modifying
	@Query("UPDATE ApplicationUser a SET a.password = ?1 WHERE a.email = ?2")
	void updatePassword(String password,String email);
}
