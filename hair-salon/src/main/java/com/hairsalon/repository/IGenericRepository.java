package com.hairsalon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/*La anotación @NoRepositoryBean es para que no se considere una instancia de repository
durante la creación de beans, es decir que no se creé una implementación de la interfaz
*/
@NoRepositoryBean 
public interface IGenericRepository<T,ID> extends JpaRepository<T,ID>{

    T disable(ID id,String column);
    
}