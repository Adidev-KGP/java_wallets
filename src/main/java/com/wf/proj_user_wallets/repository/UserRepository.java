// UserRepository.java
package com.wf.proj_user_wallets.repository;

import com.wf.proj_user_wallets.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
