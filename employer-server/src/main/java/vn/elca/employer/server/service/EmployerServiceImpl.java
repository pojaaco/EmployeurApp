package vn.elca.employer.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.employer.server.exception.CrudException;
import vn.elca.employer.server.model.criteria.EmployerDeleteCriteria;
import vn.elca.employer.server.model.criteria.EmployerGetCriteria;
import vn.elca.employer.server.model.dto.EmployerDto;
import vn.elca.employer.server.model.entity.Employer;
import vn.elca.employer.server.repository.EmployerRepository;
import vn.elca.employer.server.mapper.EmployerMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployerServiceImpl implements EmployerService {
    private static final String NUMBER_FORMAT = "%06d";

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
    public EmployerDto saveEmployer(EmployerDto employerDto) {
        try {
            if (employerDto.getNumber() == null) {
                employerDto.setNumber(String.format(NUMBER_FORMAT, employerRepository.getNextSeqValue()));
            }
            Employer savedEmployer = employerRepository.save(employerMapper.toEntity(employerDto));
            return employerMapper.toDto(savedEmployer);
        } catch (Exception e) {
            throw new CrudException(e.getMessage());
        }
    }

    @Override
    public void deleteByCriteria(EmployerDeleteCriteria criteria) {
        try {
            employerRepository.deleteById(criteria.getId());
        } catch (Exception e) {
            throw new CrudException(e.getMessage());
        }
    }
}
