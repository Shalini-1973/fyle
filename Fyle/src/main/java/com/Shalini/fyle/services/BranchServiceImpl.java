package com.Shalini.fyle.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Shalini.fyle.dto.BranchDto;
import com.Shalini.fyle.entities.Banks;
import com.Shalini.fyle.entities.Branch;
import com.Shalini.fyle.repo.BankRepository;
import com.Shalini.fyle.repo.BrachRepositoryImpl;
import com.Shalini.fyle.repo.BranchRepository;

@Service
public class BranchServiceImpl {

	@Autowired
	private BranchRepository br;

	@Autowired
	private BrachRepositoryImpl bri;

	@Autowired
	private BankRepository bankRepository;

	public List<Branch> getResult(String data) {
		// TODO Auto-generated method stub
		List<Branch> result = br.getResult(data);
		return result;
	}

	public List<Branch> listAll() {
		// TODO Auto-generated method stub
		List<Branch> findAll = br.findAll();
		return findAll;
	}

	public List<Branch> getBranches(String q, int limit, int offset) {

		List<Branch> result = bri.getBranches(q, limit, offset);

		return result;
	}

	public List<BranchDto> convertToDto(Set<Branch> listbranch) {

		List<BranchDto> branchDtos = new ArrayList<>();
		for (Branch branch : listbranch) {
			BranchDto branchDto = new BranchDto();
			branchDto.setIfsc(branch.getIfsc());
			branchDto.setAddress(branch.getAddress());
			branchDto.setCity(branch.getCity());
			branchDto.setDistrict(branch.getDistrict());
			branchDto.setState(branch.getState());
			branchDto.setBranch(branch.getBranch());
			Banks bank = bankRepository.findById(branch.getBank_id()).get();
			branchDto.setBank_name(bank.getName());
			branchDtos.add(branchDto);
		}
		return branchDtos;
	}

	public Set<Branch> listToSet(List<Branch> branches) {

		Set<Branch> set = new HashSet<>();
		for (Branch branch : branches) {
			set.add(branch);
		}
		return set;

	}

	public List<Branch> getBranchesByCity(String q, int limit, int offset) {
		List<Branch> result = bri.getBranchesByCity(q, limit, offset);

		return result;
	}

}
