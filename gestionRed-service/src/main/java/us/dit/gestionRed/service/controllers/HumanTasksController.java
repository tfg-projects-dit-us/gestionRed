package us.dit.gestionRed.service.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jbpm.services.api.UserTaskService;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import us.dit.gestionRed.service.services.kie.ReviewService;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


import javax.servlet.http.HttpSession;

@Controller
public class HumanTasksController {
	private static final Logger logger = LogManager.getLogger();

    @Autowired
	private ReviewService review;
    
    @Autowired
    private UserTaskService userTaskService;
    
    // Proceso -> "gestionRed-kjar.serviceNoDisponible"
    // Proceso -> "gestionRed-kjar.verificaServicioNginx"
    
    /**
     * 
     * @param session
     * @param model
     * @return				HTML donde se presentan todas las tareas pendientes
     */
    @GetMapping("/tareasPendientes/{procesoID}")
    public String listarTareasPendientes(@PathVariable String procesoID, HttpSession session, Model model) {
    	logger.info("Buscando todas las tareas pendientes del usuario...");
    	
    	List<TaskSummary> tasksList = null;
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		logger.info("Datos de usuario (principal)" + principal);
		
        tasksList = review.findTasksToReview(principal.getUsername(), procesoID);
        model.addAttribute("tasks", tasksList);
        logger.info("Vuelve de consultar tareas");
		return "myTasks";
    }
    
    /**
     * 
     * @param taskId			Identificador de la tarea que se selecciona en el HTML
     * @param model
     * @return					HTML donde se presenta detalle de la tarea
     */
    @GetMapping("/tareaPendiente/{taskId}")
    public String listarTareaPendienteById(@PathVariable Long taskId, Model model) {
    	logger.info("Buscando la tarea " + taskId);
    	
    	Map<String, Object> inputData = userTaskService.getTaskInputContentByTaskId(taskId);
        
        model.addAttribute("dirIP", inputData.get("dirIP"));
        model.addAttribute("process_service", inputData.get("process_service"));
        model.addAttribute("sshPort", inputData.get("sshPort"));
        model.addAttribute("sshPass", inputData.get("sshPass"));
        model.addAttribute("taskId", taskId);
    	
		TaskInstance task = review.findById(taskId);
		
		logger.info("Tarea localizada " + task);
		model.addAttribute("task", task);
		return "task";
    }

    /**
     * 
     * @param taskId				Identificador de la tarea seleccionada para completar
     * @param tareaHumana			Resultado del checkbox (Booleano)
     * @param msj_tareaHumana		Msj devuelto del gestor
     * @return						Volvemos al HTML donde se listan todas las tareas pendientes
     */
    @PostMapping("/completeTask/{taskId}")
    public String completeTask(@PathVariable Long taskId, @RequestParam Boolean tareaHumana, @RequestParam String msj_tareaHumana) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		
		Map<String, Object> outputData = new HashMap<>();
		outputData.put("tareaHumana", tareaHumana);
		outputData.put("msj_tareaHumana", msj_tareaHumana);
	     
		userTaskService.complete(taskId, principal.getUsername(), outputData);
	     
		return "redirect:/tareasPendientes";
    }
}
