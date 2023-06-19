// WalletServiceImpl.java
package com.wf.proj_user_wallets.service.impl;

import com.wf.proj_user_wallets.dto.WalletDto;
import com.wf.proj_user_wallets.model.User;
import com.wf.proj_user_wallets.model.Wallet;
import com.wf.proj_user_wallets.repository.UserRepository;
import com.wf.proj_user_wallets.repository.WalletRepository;
import com.wf.proj_user_wallets.service.WalletService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public WalletServiceImpl(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public List<WalletDto> getUserWallets(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getWallets().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WalletDto getWalletById(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        return convertToDto(wallet);
    }

    @Override
    public WalletDto createWallet(Long userId, WalletDto walletDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = convertToEntity(walletDto);
        wallet.setUser(user);

        Wallet createdWallet = walletRepository.save(wallet);
        return convertToDto(createdWallet);
    }

    @Override
    public WalletDto updateWallet(Long userId, Long walletId, WalletDto walletDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet existingWallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (!existingWallet.getUser().equals(user)) {
            throw new RuntimeException("Access denied");
        }

        BeanUtils.copyProperties(walletDto, existingWallet);
        Wallet updatedWallet = walletRepository.save(existingWallet);
        return convertToDto(updatedWallet);
    }

    @Override
    public void deleteWallet(Long userId, Long walletId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet existingWallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (!existingWallet.getUser().equals(user)) {
            throw new RuntimeException("Access denied");
        }

        walletRepository.delete(existingWallet);
    }

    private WalletDto convertToDto(Wallet wallet) {
        WalletDto walletDto = new WalletDto();
        BeanUtils.copyProperties(wallet, walletDto);
        return walletDto;
    }

    private Wallet convertToEntity(WalletDto walletDto) {
        Wallet wallet = new Wallet();
        BeanUtils.copyProperties(walletDto, wallet);
        return wallet;
    }
}
