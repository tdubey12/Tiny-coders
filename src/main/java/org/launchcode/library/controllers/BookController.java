package org.launchcode.library.controllers;

import jakarta.validation.Valid;
import org.launchcode.library.models.Book;
import org.launchcode.library.models.data.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("add") //http://localhost:8080/books/add
    public String displayAddBookForm(Model model) {

        model.addAttribute(new Book());

        return "books/add";
    }

    @PostMapping("add")  //http://localhost:8080/books/add
    public String processAddEmployerForm(@ModelAttribute @Valid Book newBook,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "books/add";
        }

        bookRepository.save(newBook);


        return "redirect:";
    }

}
