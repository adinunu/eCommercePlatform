package id.org.test.data.service.registration;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import id.org.test.common.service.CommonService;
import id.org.test.data.model.Member;
import id.org.test.data.model.PublicRegistrationActivation;

public interface PublicRegistrationActivationService extends CommonService<PublicRegistrationActivation, Long> {

	Page<PublicRegistrationActivation> getPageableList(String param, int startPage, int pageSize, Sort sort)
			throws Exception;

	Map<String, Object> activateAccount(String activationCode) throws Exception;

	Map<String, Object> checkActivationCode(String code) throws Exception;

	PublicRegistrationActivation getByBusinessName(String businessName) throws Exception;

	PublicRegistrationActivation getByBusinessSubDomain(String businessSubDomain) throws Exception;

	PublicRegistrationActivation getByMailRegistrant(String mailRegistrant) throws Exception;

	List<PublicRegistrationActivation> findInActiveListLowerThanToday(Date yesterday) throws Exception;

	boolean deleteAll(List<PublicRegistrationActivation> objList) throws Exception;

	List<PublicRegistrationActivation> getListByDateRange(Date dStartDate, Date dEndDate) throws Exception;

	List<PublicRegistrationActivation> getRestrationExpiredList(Date today) throws Exception;

	boolean findByBusinessSubDomain(String wheeAccount);

	boolean findByMailRegistrant(String mailRegistrant);

	Member addAccount(PublicRegistrationActivation par);

}
