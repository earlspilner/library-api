package dev.earlspilner.library.feign;

import dev.earlspilner.library.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Alexander Dudkin
 */
@FeignClient(
        value = "users-service",
        url = "http://localhost:9091/api",
        configuration = FeignConfig.class
)
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}")
    UserDto getUser(@PathVariable String username);

}
