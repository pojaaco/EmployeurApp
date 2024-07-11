package vn.elca.backend.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.backend.service.EmployeurService;
import vn.elca.backend.util.mapper.EmployeurMapper;
import vn.elca.common.EmployeurProto;
import vn.elca.common.EmployeurSearchRequest;
import vn.elca.common.EmployeurSearchResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeurAdapterImpl implements EmployeurAdapter {
    @Autowired
    EmployeurMapper employeurMapper;

    @Autowired
    EmployeurService employeurService;

    @Override
    public EmployeurSearchResponse searchByCriteria(EmployeurSearchRequest request) {
        List<EmployeurProto> results = employeurService.searchByCriteria(employeurMapper.toCriteria(request))
                .stream()
                .map(employeurMapper::toProto)
                .collect(Collectors.toList());
        return EmployeurSearchResponse.newBuilder()
                .addAllEmployeurs(results)
                .build();
    }
}
