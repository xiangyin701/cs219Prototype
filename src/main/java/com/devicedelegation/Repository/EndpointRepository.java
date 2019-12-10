package com.devicedelegation.Repository;

import com.devicedelegation.Entity.Delegator;
import org.springframework.data.repository.CrudRepository;

public interface EndpointRepository extends CrudRepository<Delegator, Integer> {
}
