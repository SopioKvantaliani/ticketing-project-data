package com.cydeo.converter;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDtoConverter implements Converter<String, RoleDTO> {

    RoleService roleService;

    //We need converter because whatever we show on UI, Enum is shown as String and gives error. We need Converter for that.
    //we need converter when we have dependency and has a relationship between objects. e.g user has dependency on role;
    //We don't have TaskDTO converter because we don't have any object which required task.
    public RoleDtoConverter(@Lazy RoleService roleService) {
        this.roleService = roleService;
    }


    @Override
    public RoleDTO convert(String source) {
        if (source==null || source.equals((""))){

            return null;
        }

        return roleService.findById(Long.parseLong(source));

    }
}