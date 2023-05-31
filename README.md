1. Sequence for writing api :
    - Decide the api endpoints
    - Write the dto for those api in dto layer. Dtos are used to accept request body from Http and send them to service layer for processing.
    - Write the model for that api in model layer. Model will be used to write and read from DB. Inside models you have to define relationships b/w schemas.
    - controller: Describe the api endpoints in the controller layer.
    - service : Write the services for thoses api to interact with repository layer
    - serviceImpl : Write serviceImplementations for actual working of the services.
    - repository : Write the repository layer finally to interact with DB.

2. This project has 2 models : User and Wallets:
    - User model is defined
    - Wallet model is defined
    - One to many relationship from User to Wallet is defined in model/User.java
    - Many to one relationship from wallets to a single user is defined in model/Wallet.java
