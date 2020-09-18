package id.org.test.ms.auth.service;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

import id.org.test.ms.auth.domain.CustomClientDetails;
import id.org.test.ms.auth.repository.WheeClientDetailsRepository;

@Service
public class CustomClientDetailsService implements ClientDetailsService, ClientRegistrationService {
	
	private WheeClientDetailsRepository clientDtlRepo;
	private PasswordEncoder passwordEncoder;
	
	public CustomClientDetailsService(	final WheeClientDetailsRepository clientDtlRepo,
									final PasswordEncoder passwordEncoder) {
		this.clientDtlRepo = clientDtlRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
		Optional<CustomClientDetails> oclient = Optional.of(clientDtlRepo.findByClientId(clientId));
		if(oclient.isPresent()) {
			CustomClientDetails client = oclient.get();
			client.setClientSecret(secret);
			clientDtlRepo.save(client);
		}else {
			throw new NoSuchClientException("No such clientId=" + clientId);
		}
	}

	@Override
	public void removeClientDetails(String clientId) throws NoSuchClientException {
		Optional<CustomClientDetails> oclient = Optional.of(clientDtlRepo.findByClientId(clientId));
		if(oclient.isPresent()) {
			clientDtlRepo.delete(oclient.get());
		}else {
			throw new NoSuchClientException("No such clientId=" + clientId);
		}
	}

	@Override
	public List<ClientDetails> listClientDetails() {
		return newArrayList(clientDtlRepo.findAll());
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return clientDtlRepo.findByClientId(clientId);
	}

}
