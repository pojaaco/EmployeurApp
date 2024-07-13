package vn.elca.employer.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.employer.server.exception.CrudException;
import vn.elca.employer.server.model.criteria.EmployerDelCriteria;
import vn.elca.employer.server.model.criteria.EmployerGetCriteria;
import vn.elca.employer.server.model.dto.EmployerDto;
import vn.elca.employer.server.repository.EmployerRepository;
import vn.elca.employer.server.mapper.EmployerMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployerServiceImpl implements EmployerService {
    @Autowired
    private EmployerMapper employerMapper;

    @Autowired
    private EmployerRepository employerRepository;

    @Override
    public List<EmployerDto> getByCriteria(EmployerGetCriteria criteria) {
        return employerRepository.getByCriteria(criteria)
                .stream()
                .map(employerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveEmployer(EmployerDto employerDto) {
        try {
            employerDto.setNumber(String.format("%06d", employerRepository.getNextSeqValue()));
            employerRepository.saveEmployer(employerMapper.toEntity(employerDto));
        } catch (Exception e) {
            throw new CrudException(e.getMessage());
        }
    }

    @Override
    public void delByCriteria(EmployerDelCriteria criteria) {
        try {
            employerRepository.deleteById(criteria.getId());
        } catch (Exception e) {
            throw new CrudException(e.getMessage());
        }
    }
}
