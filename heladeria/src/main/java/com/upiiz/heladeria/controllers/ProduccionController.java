package com.upiiz.heladeria.controllers;

import com.upiiz.heladeria.models.Produccion;
import com.upiiz.heladeria.services.ProduccionService;
import com.upiiz.heladeria.services.SaborServicio;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produccion")
public class ProduccionController {

    @Autowired
    private ProduccionService produccionService;

    @Autowired
    private SaborServicio saborServicio;

    @GetMapping({"", "/"})
    public String listadoProduccion(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("producciones",         produccionService.listarTodos());
        model.addAttribute("totalProducciones",    produccionService.contarTodos());
        model.addAttribute("responsablesFrecuentes", produccionService.contarPorResponsable());
        return "heladeria/vista/listado_produccion";
    }

    @GetMapping("/agregar_produccion")
    public String agregar(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("produccion", new Produccion());
        model.addAttribute("sabores", saborServicio.listarTodos());
        return "heladeria/vista/agregar_produccion";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Produccion produccion, BindingResult result,
                          Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        if (result.hasErrors()) {
            model.addAttribute("sabores", saborServicio.listarTodos());
            return "heladeria/vista/agregar_produccion";
        }
        produccionService.guardar(produccion);
        return "redirect:/produccion";
    }

    @GetMapping("/editar_produccion/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        Produccion produccion = produccionService.buscarPorId(id);
        if (produccion == null) throw new IllegalArgumentException("ID de producción inválido: " + id);
        model.addAttribute("produccion", produccion);
        model.addAttribute("sabores", saborServicio.listarTodos());
        return "heladeria/vista/editar_produccion";
    }

    @PostMapping("/actualizar")
    public String actualizar(@Valid @ModelAttribute Produccion produccion, BindingResult result,
                             Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        if (result.hasErrors()) {
            model.addAttribute("sabores", saborServicio.listarTodos());
            return "heladeria/vista/editar_produccion";
        }
        produccionService.guardar(produccion);
        return "redirect:/produccion";
    }

    @GetMapping("/eliminar_produccion/{id}")
    public String eliminarVista(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("produccion", produccionService.buscarPorId(id));
        return "heladeria/vista/eliminar_produccion";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        produccionService.eliminar(id);
        return "redirect:/produccion";
    }
}
