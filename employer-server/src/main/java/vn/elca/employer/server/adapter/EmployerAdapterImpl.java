package vn.elca.employer.server.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.employer.common.*;
import vn.elca.employer.server.exception.CrudException;
import vn.elca.employer.server.service.EmployerService;
import vn.elca.employer.server.mapper.EmployerMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployerAdapterImpl implements EmployerAdapter {
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
            employerService.saveEmployer(employerMapper.toDto(request.getEmployer()));
            response = EmployerSetResponse.newBuilder()
                    .setIsOK(true)
                    .setMessage("OK")
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
    public EmployerDelResponse delByRequest(EmployerDelRequest request) {
        EmployerDelResponse response;
        try {
            employerService.delByCriteria(employerMapper.toCriteria(request));
            response = EmployerDelResponse.newBuilder()
                    .setIsOK(true)
                    .setMessage("OK")
                    .build();
        } catch (CrudException e) {
            response = EmployerDelResponse.newBuilder()
                    .setIsOK(false)
                    .setMessage(e.getMessage())
                    .build();
        }
        return response;
    }
}
