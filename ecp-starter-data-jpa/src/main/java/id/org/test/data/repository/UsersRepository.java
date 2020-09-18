package id.org.test.data.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import id.org.test.data.model.User;

public interface UsersRepository extends PagingAndSortingRepository<User, String> {
	
	Optional<User> findByUsername(String username);

    @Query(value = "select a from User a where a.username like %:sSearch% ")
    Page<User> getPageable(@Param("sSearch") String sSearch, Pageable pageable);
    
    @Query(value = "select role from User a where a.username =:username")
    String getRoleByUser(@Param("username") String username);
    
    @Query(value = "select a from User a where a.username =:username")
    User getByUser(@Param("username") String username);
    
    @Query(value = "select a from User a where a.username =:username")
    User getByUserName(@Param("username") String username);

}
