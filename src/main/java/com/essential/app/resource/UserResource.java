package com.essential.app.resource;

import com.essential.app.resource.dto.UserDto;
import com.essential.app.resource.dto.UserUpdateDto;
import com.essential.app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
@PreAuthorize("isAuthenticated()")
public class UserResource {

    private UserService userService;
    private ModelMapper modelMapper;


    @Autowired
    public UserResource(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(produces = "application/json")
    public Page<UserDto> findAll(@PageableDefault(sort={"id"}, direction= Sort.Direction.DESC, page=0, size=10) Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Object findById(@PathVariable Long id) {
       return userService.findById(id).get();
    }

    //TODO: Implementar CSRF
    @PostMapping(produces = "application/json", consumes = "application/json" )
    public void create(@Valid @RequestBody UserDto userDto) {
        userService.create(userDto);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public void update(@PathVariable Long id, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        userUpdateDto.setId(id);
        userService.update(userUpdateDto);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
