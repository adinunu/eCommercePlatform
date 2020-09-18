package id.org.test.data.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import id.org.test.data.model.PublicRegistrationActivation;

public interface PublicRegistrationActivationRepository extends JpaRepository<PublicRegistrationActivation, Long>, QueryDslPredicateExecutor<PublicRegistrationActivation> {

	Optional<PublicRegistrationActivation> findByBusinessSubDomainAndMailRegistrant(String subDomain, String mail);
	
	Optional<PublicRegistrationActivation> findById(Long id);
	
	Optional<PublicRegistrationActivation> findByBusinessSubDomain(String businessSubDomain);
	
	Optional<PublicRegistrationActivation> findByMailRegistrant(String mailRegistrant);
	
    @Query(value = "select a  from PublicRegistrationActivation a where a.mailRegistrant like %:text% or a.activationCode like %:text% ")
    Page<PublicRegistrationActivation> getPageable(@Param("text") String text, Pageable pageable);

    @Query("select a from PublicRegistrationActivation a where a.mailRegistrant=:mailRegistrant and a.activationCode=:activationCode")
    PublicRegistrationActivation getByRegistrantAndActivationCode(@Param("mailRegistrant") String mailRegistrant, @Param("activationCode") String activationCode);

    @Query("select a from PublicRegistrationActivation a where a.activationCode=:activationCode")
    PublicRegistrationActivation getByActivationCode(@Param("activationCode") String activationCode);

    @Query("select a from PublicRegistrationActivation a where a.businessName=:businessName")
    PublicRegistrationActivation getByBusinessName(@Param("businessName") String businessName);
    
    @Query("select a from PublicRegistrationActivation a where a.businessSubDomain=:businessSubDomain")
    PublicRegistrationActivation getByBusinessSubDomain(@Param("businessSubDomain") String businessSubDomain);
    
    @Query("select a from PublicRegistrationActivation a where a.mailRegistrant=:mailRegistrant")
    PublicRegistrationActivation getByMailRegistrant(@Param("mailRegistrant") String mailRegistrant);

    @Query("select a from PublicRegistrationActivation a where a.updatedDate <=:endDate and a.activated=false")
    List<PublicRegistrationActivation> getInactiveListUntilEndDate(@Param("endDate") Date endDate);

    @Query("select a from PublicRegistrationActivation a where a.createdDate >=:startDate and a.createdDate <=:endDate")
    List<PublicRegistrationActivation> findListByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query("select a from PublicRegistrationActivation a where a.activated = 0 and TIMESTAMPDIFF(HOUR,CREATED_DATE,:today) >= 24")
    List<PublicRegistrationActivation> listRegistrationExpired(@Param("today") Date today);
    
    @Query("select a from PublicRegistrationActivation a where a.activated = 0 and TIMESTAMPDIFF(HOUR,CREATED_DATE,:today) >= 1")
    List<PublicRegistrationActivation> listRegistrationExpiredPerHour(@Param("today") Date today);
    
}
