package toy.invitation.toyinvitation.config

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(provider: JwtAuthenticationProvider): OncePerRequestFilter() {

    private var jwtAuthenticationProvider: JwtAuthenticationProvider = provider

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = jwtAuthenticationProvider.resolveToken(request)
        if (token != null && jwtAuthenticationProvider.validateToken(token)) {
            val authentication: Authentication = jwtAuthenticationProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}