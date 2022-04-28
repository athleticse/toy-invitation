package toy.invitation.toyinvitation.config.res

class BaseException(baseResponseCode: BaseResponseCode): RuntimeException() {
    val baseResponseCode: BaseResponseCode = baseResponseCode
}