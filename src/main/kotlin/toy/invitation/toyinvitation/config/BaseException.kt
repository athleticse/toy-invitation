package toy.invitation.toyinvitation.config

class BaseException(baseResponseCode: BaseResponseCode): RuntimeException() {
    val baseResponseCode: BaseResponseCode = baseResponseCode
}