package com.upiiz.heladeria.controllers;

import com.upiiz.heladeria.models.Pedido;
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
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private SaborServicio saborServicio;

    @Autowired
    private ProduccionService produccionService;

    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("pedidos",          pedidoService.listarTodos());
        model.addAttribute("totalPedidos",     pedidoService.contarTodos());
        model.addAttribute("pedidosPendientes",pedidoService.contarPorEstado(Pedido.Estado.pendiente));
        model.addAttribute("pedidosEntregados",pedidoService.contarPorEstado(Pedido.Estado.entregado));
        model.addAttribute("pedidosCancelados",pedidoService.contarPorEstado(Pedido.Estado.cancelado));
        return "heladeria/vista/list-pedidos";
    }

    @GetMapping("/agregar")
    public String mostrarFormulario(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("sabores", saborServicio.listarTodos());
        return "heladeria/vista/add-pedido";
    }

    @PostMapping("/agregar")
    public String guardar(@Valid @ModelAttribute Pedido pedido, BindingResult result,
                          Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        if (result.hasErrors()) {
            model.addAttribute("sabores", saborServicio.listarTodos());
            return "heladeria/vista/add-pedido";
        }
        pedidoService.guardar(pedido);
        return "redirect:/pedidos";
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarActualizar(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        Pedido pedido = pedidoService.buscarPorId(id);
        if (pedido == null) throw new IllegalArgumentException("Pedido no encontrado: " + id);
        model.addAttribute("pedido", pedido);
        model.addAttribute("sabores", saborServicio.listarTodos());
        return "heladeria/vista/actualizar-pedido";
    }

    @PostMapping("/actualizar")
    public String actualizar(@Valid @ModelAttribute Pedido pedido, BindingResult result,
                             Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        if (result.hasErrors()) {
            model.addAttribute("sabores", saborServicio.listarTodos());
            return "heladeria/vista/actualizar-pedido";
        }
        pedidoService.guardar(pedido);
        return "redirect:/pedidos";
    }

    @GetMapping("/eliminar/{id}")
    public String mostrarEliminar(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        Pedido pedido = pedidoService.buscarPorId(id);
        if (pedido == null) throw new IllegalArgumentException("Pedido no encontrado: " + id);
        model.addAttribute("pedido", pedido);
        return "heladeria/vista/delete-pedido";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long pedidoId, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        pedidoService.eliminar(pedidoId);
        return "redirect:/pedidos";
    }

    @GetMapping("/estadistica")
    public String estadistica(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("totalSabores",          saborServicio.contarTodos());
        model.addAttribute("totalPedidos",          pedidoService.contarTodos());
        model.addAttribute("totalProducciones",     produccionService.contarTodos());
        model.addAttribute("pedidosPendientes",     pedidoService.contarPorEstado(Pedido.Estado.pendiente));
        model.addAttribute("pedidosEntregados",     pedidoService.contarPorEstado(Pedido.Estado.entregado));
        model.addAttribute("pedidosCancelados",     pedidoService.contarPorEstado(Pedido.Estado.cancelado));
        model.addAttribute("pedidosPorMes",         pedidoService.contarPorMes());
        model.addAttribute("ultimosPedidos",        pedidoService.listarTodos());
        model.addAttribute("tipoSabores",           saborServicio.contarPorTipo());
        model.addAttribute("produccionPorMes",      produccionService.contarPorMes());
        model.addAttribute("responsablesFrecuentes",produccionService.contarPorResponsable());
        return "heladeria/vista/estadistica";
    }
}
