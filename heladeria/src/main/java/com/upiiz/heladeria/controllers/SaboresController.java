package com.upiiz.heladeria.controllers;

import com.upiiz.heladeria.models.Pedido;
import com.upiiz.heladeria.models.Sabor;
import com.upiiz.heladeria.models.Usuario;
import com.upiiz.heladeria.services.PedidoService;
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
@RequestMapping("/heladeria")
public class SaboresController {

    @Autowired
    private SaborServicio saborServicio;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProduccionService produccionService;

    @GetMapping({"", "/"})
    public String baseRedirect() {
        return "redirect:/heladeria/index";
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("totalSabores",     saborServicio.contarTodos());
        model.addAttribute("totalPedidos",     pedidoService.contarTodos());
        model.addAttribute("totalProducciones",produccionService.contarTodos());
        model.addAttribute("pedidosPendientes",pedidoService.contarPorEstado(Pedido.Estado.pendiente));
        model.addAttribute("pedidosEntregados",pedidoService.contarPorEstado(Pedido.Estado.entregado));
        model.addAttribute("pedidosCancelados",pedidoService.contarPorEstado(Pedido.Estado.cancelado));
        model.addAttribute("tipoSabores",      saborServicio.contarPorTipo());
        return "heladeria/index";
    }

    @GetMapping("/listado_sabores")
    public String listadoSabores(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("sabores",      saborServicio.listarTodos());
        model.addAttribute("totalSabores", saborServicio.contarTodos());
        model.addAttribute("tipoSabores",  saborServicio.contarPorTipo());
        return "heladeria/vista/listado_sabores";
    }

    @GetMapping("/agregar_sabor")
    public String agregarSabor(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("sabor", new Sabor());
        return "heladeria/vista/agregar_sabor";
    }

    @PostMapping("/guardar")
    public String guardarSabor(@Valid @ModelAttribute Sabor sabor, BindingResult result, HttpSession session) {
        if (result.hasErrors()) return "heladeria/vista/agregar_sabor";
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario != null) {
            sabor.setUsuarioId(usuario.getUsuarioId());
            saborServicio.guardar(sabor);
            return "redirect:/heladeria/listado_sabores";
        }
        return "redirect:/login";
    }

    @GetMapping("/editar_sabor/{id}")
    public String editarSabor(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        Sabor sabor = saborServicio.buscarPorId(id);
        if (sabor == null) throw new IllegalArgumentException("ID de sabor inválido: " + id);
        model.addAttribute("sabor", sabor);
        return "heladeria/vista/editar_sabor";
    }

    @PostMapping("/actualizar")
    public String actualizarSabor(@Valid @ModelAttribute Sabor sabor, BindingResult result, HttpSession session) {
        if (result.hasErrors()) return "heladeria/vista/editar_sabor";
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario != null) {
            sabor.setUsuarioId(usuario.getUsuarioId());
            saborServicio.guardar(sabor);
            return "redirect:/heladeria/listado_sabores";
        }
        return "redirect:/login";
    }

    @GetMapping("/eliminar_sabor/{id}")
    public String eliminarSaborVista(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("sabor", saborServicio.buscarPorId(id));
        return "heladeria/vista/eliminar_sabor";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        saborServicio.eliminar(id);
        return "redirect:/heladeria/listado_sabores";
    }
}
