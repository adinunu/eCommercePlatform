package id.org.test.data.service.mobile.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.org.test.data.model.PublicRegistrationActivation;
import id.org.test.data.repository.PublicRegistrationActivationRepository;
import id.org.test.data.service.mobile.RegistrationMobileService;
import id.org.test.data.service.mobile.wrapper.RegistrationMobileWrapper;

@Service
@Transactional
public class RegistrationMobileServiceImpl implements RegistrationMobileService {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationMobileServiceImpl.class);
	
	private final PublicRegistrationActivationRepository registrationRepository;


	@Autowired
	public RegistrationMobileServiceImpl(PublicRegistrationActivationRepository registrationRepository) {
		this.registrationRepository = registrationRepository;
	}
	
	private PublicRegistrationActivation toEntity(RegistrationMobileWrapper wrapper) throws Exception {
		PublicRegistrationActivation model = new PublicRegistrationActivation();
		if (wrapper.getId() != null) {
			model = registrationRepository.findOne(wrapper.getId()); // edit mode
		}
		model.setBusinessName(wrapper.getBusinessName());
		model.setBusinessSubDomain(wrapper.getBusinessSubDomain());
		model.setWebsite(wrapper.getWebsite());
		model.setMailRegistrant(wrapper.getMailRegistrant());
		model.setPassword(wrapper.getPassword());
		model.setActivationCode(wrapper.getActivationCode());
		model.setActivated(wrapper.getActivated());
		model.setNumberOfStore(wrapper.getNumberOfStore());
		model.setFirstName(wrapper.getFirstName());
		model.setLastName(wrapper.getLastName());
		model.setCity(wrapper.getCity());
		model.setPhoneNum(wrapper.getPhoneNum());
		
		return model;
	}

	private RegistrationMobileWrapper toWrapper(PublicRegistrationActivation entity) {
		RegistrationMobileWrapper wrapper = new RegistrationMobileWrapper();
		if (entity != null) {
			wrapper.setBusinessName(entity.getBusinessName());
			wrapper.setBusinessSubDomain(entity.getBusinessSubDomain());
			wrapper.setWebsite(entity.getWebsite());
			wrapper.setMailRegistrant(entity.getMailRegistrant());
			wrapper.setPassword(entity.getPassword());
			wrapper.setActivationCode(entity.getActivationCode());
			wrapper.setActivated(entity.getActivated());
			wrapper.setNumberOfStore(entity.getNumberOfStore());
			wrapper.setFirstName(entity.getFirstName());
			wrapper.setLastName(entity.getLastName());
			wrapper.setCity(entity.getCity());
			wrapper.setPhoneNum(entity.getPhoneNum());
		}
		return wrapper;
	}

	@Override
	public Long getNum() {
		return registrationRepository.count();
	}

	@Override
	public RegistrationMobileWrapper save(RegistrationMobileWrapper wrapper) throws Exception {
		return toWrapper(registrationRepository.save(toEntity(wrapper)));
	}

	@Override
	public RegistrationMobileWrapper getById(Long aLong) throws Exception {
		return toWrapper(registrationRepository.findOne(aLong));
	}

	@Override
	public Boolean delete(Long aLong) throws Exception {
		return null;
	}

	@Override
	public List<RegistrationMobileWrapper> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
