// WalletRepository.java
package com.wf.proj_user_wallets.repository;

import com.wf.proj_user_wallets.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
