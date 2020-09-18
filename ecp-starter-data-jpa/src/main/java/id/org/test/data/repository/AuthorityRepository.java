package id.org.test.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.org.test.data.model.Authorities;

import java.util.List;

public interface AuthorityRepository extends PagingAndSortingRepository<Authorities,Long> {

    @Query(value = "select a from Authorities a where a.user.username=:username")
    List<Authorities> getRoleByUserAccount(@Param("username") String username);
}
