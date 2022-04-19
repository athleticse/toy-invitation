package toy.invitation.toyinvitation.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController @Autowired constructor(
    private val accountService: AccountService
) {

    @GetMapping("/account")
    fun selectAccount(@RequestBody dto: AccountDTO): Any {
        return accountService.selectAccount()
    }
}