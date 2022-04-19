package toy.invitation.toyinvitation.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl @Autowired constructor(
    private val account: AccountRepository
): AccountService {

    override fun selectAccount(): List<AccountDAO> {
        return account.findAll()
    }
}