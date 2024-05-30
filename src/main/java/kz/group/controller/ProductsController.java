package kz.group.controller;

import jakarta.validation.Valid;
import kz.group.entity.ProductsEntity;
import kz.group.DTO.ProductDto;
import kz.group.repository.ProductsRepository;
import kz.group.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService;
    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping({"","/"})
    public String showProductList(Model model){
        List<ProductsEntity> products = productsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model){
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/createProduct";
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result
    ){
        if(result.hasErrors()){
            return "products/createProduct";
        }

        ProductsEntity products = new ProductsEntity();
        products.setProductName(productDto.getName());
        products.setDescription(productDto.getDescription());
        products.setPrice(productDto.getPrice());
        products.setCreatedAt(LocalDateTime.now());
        products.setTotalClasses(productDto.getTotalClasses());
        productsRepository.save(products);

        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ) {
        try {
            ProductsEntity product = productsRepository.findById(id).get();
            model.addAttribute("product",product);

            ProductDto productDto = new ProductDto();
            productDto.setName(product.getProductName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setTotalClasses(product.getTotalClasses());

            model.addAttribute("productDto", productDto);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/products";
        }
        return "products/editProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result
    ){
        try {
            ProductsEntity product = productsRepository.findById(id).get();
            model.addAttribute("product",product);

            if(result.hasErrors()){
                return "products/editProduct";
            }

            product.setProductName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setTotalClasses(productDto.getTotalClasses());
            productsRepository.save(product);
        } catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/products";
    }



//    String getProduct(@RequestParam("id") Long id) {
//        Optional<ProductsEntity> optional = productsService.findById(id);
//        if(optional.isPresent()){
//            return optional.get().toString();
//        }
//        return "Такого продукта не существует";
//    }


}
