package vn.elca.backend.adapter;

import vn.elca.common.EmployeurSearchRequest;
import vn.elca.common.EmployeurSearchResponse;

public interface EmployeurAdapter {
    EmployeurSearchResponse searchByCriteria(EmployeurSearchRequest request);
}
