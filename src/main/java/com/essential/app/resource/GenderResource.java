package com.essential.app.resource;

import com.essential.app.model.Gender;
import com.essential.app.resource.dto.GenderDto;
import com.essential.app.resource.dto.UserDto;
import com.essential.app.service.GenderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/genders")
@PreAuthorize("isAuthenticated()")
public class GenderResource {

    private GenderService genderService;
    private ModelMapper modelMapper;

    @Autowired
    public GenderResource(GenderService genderService, ModelMapper modelMapper) {
        this.genderService = genderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(produces = "application/json")
    public List<Object> findAll(@PageableDefault(sort={"id"}, direction= Sort.Direction.DESC, page=0, size=10) Pageable pageable) {
        return modelMapper.map(genderService.findAll(pageable), new TypeToken<List<GenderDto>>(){}.getType());
    }

    @GetMapping(value = "/{name}", produces = "application/json")
    public Object findById(@PathVariable String name) {
       return genderService.findByNameIgnoreCase(name).get();
    }

    //TODO: Implementar CSRF
    @PostMapping(produces = "application/json", consumes = "application/json" )
    public void create(@Valid @RequestBody GenderDto genderDto) {
        genderService.create(genderDto);
    }

    @PutMapping(value = "/{name}", produces = "application/json")
    public void update(@PathVariable String name, @Valid @RequestBody GenderDto genderDto) {
        genderDto.setName(genderDto.getName());
        genderService.update(name, genderDto);
    }

    @DeleteMapping(value = "/{name}", produces = "application/json")
    public void delete(@PathVariable String name) {
        genderService.delete(name);
    }

}
