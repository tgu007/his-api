package lukelin.his.dto.conveter;

import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.basic.template.MedicalRecordTemplate;
import lukelin.his.domain.entity.basic.codeEntity.UserRole;
import lukelin.his.domain.entity.basic.template.TemplateTag;
import lukelin.his.domain.entity.basic.template.TemplateTagMenu;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.dto.basic.req.filter.TagFilterDto;
import lukelin.his.dto.basic.resp.entity.TreatmentListMiniRespDto;
import lukelin.his.dto.basic.resp.entity.TreatmentRespDto;
import lukelin.his.dto.basic.resp.setup.EmployeeDto;
import lukelin.his.dto.basic.resp.setup.EmployeeListDto;
import lukelin.his.dto.basic.resp.setup.TreeNodeDto;
import lukelin.his.dto.basic.resp.template.MedicalRecordTemplateListDto;
import lukelin.his.dto.basic.resp.setup.UserRoleListDto;
import lukelin.his.dto.basic.resp.ward.PatientSignInWardDto;

import java.util.ArrayList;
import java.util.List;

public class BasicDtoConverter {
    public static List<MedicalRecordTemplateListDto> toMedicalRecordTemplateList(List<MedicalRecordTemplate> medicalRecordTemplateList) {
        List<MedicalRecordTemplateListDto> medicalRecordTemplateDtoList = new ArrayList<>();
        for (MedicalRecordTemplate template : medicalRecordTemplateList) {
            MedicalRecordTemplateListDto dto = template.toListDto();
            medicalRecordTemplateDtoList.add(dto);
        }

        return medicalRecordTemplateDtoList;
    }

    public static List<EmployeeListDto> toEmployeeListDto(List<Employee> employeeList) {
        List<EmployeeListDto> employeeDtoList = new ArrayList<>();
        for (Employee employee : employeeList) {
            employeeDtoList.add(employee.toListDto());
        }
        return employeeDtoList;
    }

    public static List<UserRoleListDto> toUserRoleListDto(List<UserRole> userRoleList) {
        List<UserRoleListDto> userRoleDtoList = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            userRoleDtoList.add(userRole.toListDto());
        }
        return userRoleDtoList;
    }

    public static List<TreeNodeDto> toTreeNodeDtoList(List<TemplateTagMenu> tagList, TagFilterDto filterDto) {
        List<TreeNodeDto> treeNodeList = new ArrayList<>();
        for (TemplateTagMenu menu : tagList)
            treeNodeList.add(menu.toDto(filterDto));
        return treeNodeList;
    }

    public static List<PatientSignInWardDto> toPatientSignInWardDtoList(List<Ward> wardList) {
        List<PatientSignInWardDto> wardDtoList = new ArrayList<>();
        for (Ward ward : wardList)
            wardDtoList.add(ward.toPatientWardDto());
        return wardDtoList;
    }

    public static List<TreatmentListMiniRespDto> toEmployeeTreatmentOperateList(List<Treatment> treatmentList, Employee employee) {
        List<TreatmentListMiniRespDto> treatmentDtoList = new ArrayList<>();
        for (Treatment treatment : treatmentList) {
            TreatmentListMiniRespDto dto = new TreatmentListMiniRespDto();
            dto.setUuid(treatment.getUuid());
            dto.setName(treatment.getName());
            dto.setOperate(employee.getTreatmentList().stream().anyMatch(t -> t.getUuid().equals(treatment.getUuid())));
            treatmentDtoList.add(dto);
        }
        return treatmentDtoList;
    }
}
