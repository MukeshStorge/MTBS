package com.mtbs.branches;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mtbs.models.Branch;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/branchess")
@Tag(name = "Master - Branches")
public class BranchesController {

    @Autowired
    private BranchesRepository branchesRepository;

    @GetMapping("/all")
    public List<Branch> showAllBranchess() {
        return (List<Branch>) branchesRepository.findAll();
    }

    @GetMapping("/{branchId}")
    public Optional<Branch> showBranchesById(@RequestParam(required = true) Integer branchId) {
        return branchesRepository.findById(branchId);
    }

    @PostMapping("/add")
    public ResponseEntity<Branch> showAllBranches(@RequestBody Branch branch) {
			return ResponseEntity.status(HttpStatus.CREATED).body(branchesRepository.save(branch));
    }

    @DeleteMapping("/delete/{branchId}")
    public void deleteBranchesById(@RequestParam(required = true) Integer branchId) {
         branchesRepository.deleteById(branchId);
    }

}
