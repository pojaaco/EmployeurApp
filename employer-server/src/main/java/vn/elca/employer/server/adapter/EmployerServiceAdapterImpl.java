package vn.elca.employer.server.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.employer.common.*;
import vn.elca.employer.server.exception.CrudException;
import vn.elca.employer.server.model.dto.EmployerDto;
import vn.elca.employer.server.service.EmployerService;
import vn.elca.employer.server.mapper.EmployerMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployerServiceAdapterImpl implements EmployerServiceAdapter {
    @Autowired
    EmployerMapper employerMapper;

    @Autowired
    EmployerService employerService;

    @Override
    public EmployerGetResponse getByRequest(EmployerGetRequest request) {
        List<EmployerProto> results = employerService.getByCriteria(employerMapper.toCriteria(request))
                .stream()
                .map(employerMapper::toProto)
                .collect(Collectors.toList());
        return EmployerGetResponse.newBuilder()
                .addAllEmployers(results)
                .build();
    }

    @Override
    public EmployerSetResponse setByRequest(EmployerSetRequest request) {
        EmployerSetResponse response;
        try {
            EmployerDto employer = employerService.saveEmployer(employerMapper.toDto(request.getEmployer()));
            response = EmployerSetResponse.newBuilder()
                    .setIsOK(true)
                    .setMessage("OK")
                    .setEmployer(employerMapper.toProto(employer))
                    .build();
        } catch (CrudException e) {
            response = EmployerSetResponse.newBuilder()
                    .setIsOK(false)
                    .setMessage(e.getMessage())
                    .build();
        }
        return response;
    }

    @Override
    public EmployerDeleteResponse deleteByRequest(EmployerDeleteRequest request) {
        EmployerDeleteResponse response;
        try {
            employerService.deleteByCriteria(employerMapper.toCriteria(request));
            response = EmployerDeleteResponse.newBuilder()
                    .setIsOK(true)
                    .setMessage("OK")
                    .build();
        } catch (CrudException e) {
            response = EmployerDeleteResponse.newBuilder()
                    .setIsOK(false)
                    .setMessage(e.getMessage())
                    .build();
        }
        return response;
    }
}
