package mvc.controller;

import mvc.model.dto.AdminDTO;
import mvc.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/addadmin")
    public ModelAndView getAddAdmin(){
        logger.warn("Before adding the admin. Admin form adding.");
        return new ModelAndView("addadmin","newAdmin", new AdminDTO());
    }

    @PostMapping("/addadmin")
    public String addNewAdmin(@ModelAttribute AdminDTO adminDTO){
        logger.warn("Adding new admin");
        System.out.println(adminDTO.getFirstName() + " " + adminDTO.getLastName());
        adminService.addAdmin(adminDTO);
        return "admin-success";
    }

    @GetMapping("/admins")
    public ModelAndView getAllAdmins(){
        logger.warn("List the admin list");
        List<AdminDTO> adminDTOList = adminService.getAllAdmins();
        return new ModelAndView("admins","adminList", adminDTOList);
    }

    @PostMapping("/deleteadmin")
    public String deleteAdmin(@ModelAttribute AdminDTO adminDTO){
        logger.warn("delete admin");
        adminService.deleteAdminById(adminDTO.getId());
        return "userpage";
    }
}
