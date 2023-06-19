// WalletController.java
package com.wf.proj_user_wallets.controller;

import com.wf.proj_user_wallets.dto.WalletDto;
import com.wf.proj_user_wallets.model.Wallet;
import com.wf.proj_user_wallets.service.WalletService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{userId}/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public ResponseEntity<List<WalletDto>> getUserWallets(@PathVariable Long userId) {
        List<Wallet> wallets = walletService.getUserWallets(userId);
        List<WalletDto> walletDtos = wallets.stream()
                .map(wallet -> {
                    WalletDto walletDto = new WalletDto();
                    BeanUtils.copyProperties(wallet, walletDto);
                    return walletDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(walletDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletDto> getWallet(@PathVariable Long userId, @PathVariable Long id) {
        Wallet wallet = walletService.getWalletById(id);
        WalletDto walletDto = new WalletDto();
        BeanUtils.copyProperties(wallet, walletDto);
        return ResponseEntity.ok(walletDto);
    }

    @PostMapping
    public ResponseEntity<WalletDto> createWallet(@PathVariable Long userId, @RequestBody WalletDto walletDto) {
        Wallet wallet = new Wallet();
        BeanUtils.copyProperties(walletDto, wallet);
        Wallet createdWallet = walletService.createWallet(userId, wallet);
        WalletDto createdWalletDto = new WalletDto();
        BeanUtils.copyProperties(createdWallet, createdWalletDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWalletDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WalletDto> updateWallet(@PathVariable Long userId, @PathVariable Long id, @RequestBody WalletDto walletDto) {
        Wallet wallet = new Wallet();
        BeanUtils.copyProperties(walletDto, wallet);
        Wallet updatedWallet = walletService.updateWallet(userId, id, wallet);
        WalletDto updatedWalletDto = new WalletDto();
        BeanUtils.copyProperties(updatedWallet, updatedWalletDto);
        return ResponseEntity.ok(updatedWalletDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long userId, @PathVariable Long id) {
        walletService.deleteWallet(userId, id);
        return ResponseEntity.noContent().build();
    }
}
