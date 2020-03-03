package ru.hh.school.service;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.dao.CompanyDao;
import ru.hh.school.dao.VacancyDao;
import ru.hh.school.dto.CompanyResponseDto;
import ru.hh.school.dto.VacancyRequestDto;
import ru.hh.school.dto.VacancyResponseDto;
import ru.hh.school.entity.Company;
import ru.hh.school.entity.Vacancy;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class VacancyService {

    private VacancyDao vacancyDao;
    private CompanyDao companyDao;

    @Inject
    public VacancyService(VacancyDao vacancyDao, CompanyDao companyDao) {
        this.vacancyDao = vacancyDao;
        this.companyDao = companyDao;
    }
    @Transactional
    public void saveNew(VacancyRequestDto vacancyDto) {
        Company company = companyDao.get(vacancyDto.getCompanyId());
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle(vacancyDto.getTitle());
//        vacancy.setCompany();
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setContacts(vacancyDto.getContacts());
        vacancy.setCompensation(vacancyDto.getSalary());
        vacancy.setCompany(company);
        //TODO проверить, чтобы добавлялось время
        vacancyDao.create(vacancy);
    }
    @Transactional
    public List<VacancyResponseDto> getAll() {
        return vacancyDao.getAll().stream()
                .map(VacancyService::mapped)
                .collect(Collectors.toList());
    }

    private static VacancyResponseDto mapped(Vacancy vacancy) {
        VacancyResponseDto vacancyDto = new VacancyResponseDto();
        vacancyDto.setId(vacancy.getId());
        vacancyDto.setTitle(vacancy.getTitle());
        vacancyDto.setCompany(CompanyService.mapped(vacancy.getCompany()));
        vacancyDto.setDateCreate(vacancy.getCreationDate().toString());
        return vacancyDto;
    }

}