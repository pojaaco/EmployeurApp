package vn.elca.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.backend.model.criteria.EmployeurSearchCriteria;
import vn.elca.backend.model.dto.EmployeurDto;
import vn.elca.backend.repository.EmployeurRepository;
import vn.elca.backend.util.mapper.EmployeurMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeurServiceImpl implements EmployeurService {
    @Autowired
    EmployeurMapper employeurMapper;

    @Autowired
    EmployeurRepository employeurRepository;

    @Override
    @Transactional
    public List<EmployeurDto> searchByCriteria(EmployeurSearchCriteria criteria) {
        return employeurRepository.searchByCriteria(criteria)
                .stream()
                .map(employeurMapper::toDto)
                .collect(Collectors.toList());
    }
}
