// WalletService.java
package com.wf.proj_user_wallets.service;

import com.wf.proj_user_wallets.dto.WalletDto;

import java.util.List;

public interface WalletService {
    List<WalletDto> getUserWallets(Long userId);
    WalletDto getWalletById(Long walletId);
    WalletDto createWallet(Long userId, WalletDto walletDto);
    WalletDto updateWallet(Long userId, Long walletId, WalletDto walletDto);
    void deleteWallet(Long userId, Long walletId);
}
