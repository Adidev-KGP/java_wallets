// Wallet.java
package com.wf.proj_user_wallets.model;

import javax.persistence.*;

@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String walletAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // Constructors, getters, and setters

    public Wallet() {
    }

    public Wallet(String walletAddress, User user) {
        this.walletAddress = walletAddress;
        this.user = user;
    }

    // Getters and setters omitted for brevity
}
