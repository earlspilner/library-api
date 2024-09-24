package dev.earlspilner.loans.feign;

import dev.earlspilner.loans.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Alexander Dudkin
 */
@FeignClient(
        name = "users-service",
        configuration = FeignConfig.class
)
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/users/{username}")
    UserDto getUser(@PathVariable String username);

}
