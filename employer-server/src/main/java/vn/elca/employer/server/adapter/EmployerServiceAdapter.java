package vn.elca.employer.server.adapter;

import vn.elca.employer.common.*;

public interface EmployerServiceAdapter {
    EmployerGetResponse getByRequest(EmployerGetRequest request);

    EmployerSetResponse setByRequest(EmployerSetRequest request);

    EmployerDeleteResponse deleteByRequest(EmployerDeleteRequest request);
}
