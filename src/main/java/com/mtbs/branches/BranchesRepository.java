package com.mtbs.branches;

import org.springframework.data.repository.CrudRepository;

import com.mtbs.models.Branch;

public interface BranchesRepository extends CrudRepository<Branch, Integer> {


}
