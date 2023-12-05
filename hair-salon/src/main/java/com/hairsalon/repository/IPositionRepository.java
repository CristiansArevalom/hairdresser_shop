package com.hairsalon.repository;


import org.springframework.stereotype.Repository;

import com.hairsalon.model.Position;

//Se hereda de IGenericRepository por si se desea cambiar la interfaz de JPA
@Repository
public interface IPositionRepository extends IGenericRepository<Position,Integer>{
    
 
}
