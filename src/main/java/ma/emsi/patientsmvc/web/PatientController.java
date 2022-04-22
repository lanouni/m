package ma.emsi.patientsmvc.web;

import lombok.AllArgsConstructor;
import ma.emsi.patientsmvc.entites.Pat;
import ma.emsi.patientsmvc.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping(path = "/index")
    public String patients(
            Model model,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5")int size,
            @RequestParam(name = "keyword",defaultValue = "")String keyword){
        Page<Pat> pagePatients = patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);

        return "patients";
    }

    @GetMapping("/delete")
    public String delete(Long id,String keyword,String page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }

    @GetMapping("/patients")
    @ResponseBody
    public List<Pat> listPatient(){
        return patientRepository.findAll();
    }

    @GetMapping("/formPatient")
    public String formPatient(Model model){
        model.addAttribute("patient",new Pat());
        return "formPatient";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @GetMapping("/save")
    public  String save(Model model, @Valid Pat patient, BindingResult bindingResult,
                        @RequestParam(defaultValue = "0") int page ,
                        @RequestParam(defaultValue = "")String keyword){
        if (bindingResult.hasErrors()) return  "formPatient";
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/editPatient")
    public String editPatient(Model model,Long id, String keyword, int page){
        Pat patient = patientRepository.findById(id).orElse(null);
        if (patient ==null) throw new RuntimeException("patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatient";
    }
}