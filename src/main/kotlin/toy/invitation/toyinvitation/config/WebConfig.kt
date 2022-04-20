package toy.invitation.toyinvitation.config

import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Component
class WebConfig: WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .exposedHeaders("X-AUTH-TOKEN")
            .allowCredentials(true)
            .allowedOrigins("http://localhost:8080")
    }
}