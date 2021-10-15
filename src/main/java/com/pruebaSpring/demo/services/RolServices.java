package com.pruebaSpring.demo.services;

import com.pruebaSpring.demo.models.dao.IRolDao;
import com.pruebaSpring.demo.models.entity.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RolServices {

    @Autowired
    private IRolDao iRolDao;
    public List<Rol> listAllRol() { return iRolDao.findAll();}
}
