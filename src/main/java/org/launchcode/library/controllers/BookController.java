package org.launchcode.library.controllers;

import jakarta.validation.Valid;
import org.launchcode.library.models.*;
import org.launchcode.library.models.data.BookCheckoutRepository;
import org.launchcode.library.models.data.BookRepository;
import org.launchcode.library.models.data.StudentBookRepository;
import org.launchcode.library.models.data.StudentRepository;
import org.launchcode.library.models.dto.StudentBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private BookCheckoutRepository bookCheckoutRepository;
    @Autowired
    private StudentBookRepository studentBookRepository;

    //codes for search book by Anitha
    static HashMap<String, String> bookSearchOptions = new HashMap<>();

    public BookController() {

        bookSearchOptions.put("all", "All");
        bookSearchOptions.put("name", "Book Name");
        bookSearchOptions.put("authorName", "Author Name");
        bookSearchOptions.put("genre", "Genre");

    }

    @GetMapping("/") //http://localhost:8080/books/
    public String index(Model model) {
        //anitha added booksearchoptions
        model.addAttribute("columns", bookSearchOptions);
        model.addAttribute("searchType","all");
        model.addAttribute("books", bookRepository.findAll());
        return "books/index";
    }
    //Anitha's code for search book
    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("columns", bookSearchOptions);
        model.addAttribute("searchType","all");
        model.addAttribute("books", bookRepository.findAll());
        return "books/index";
    }

    @PostMapping("/search/results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        Iterable<Book> books;
        ArrayList<Book> tempBooks = (ArrayList<Book>) bookRepository.findAll();
        books = BookData.findByColumnAndValue(searchType, searchTerm, bookRepository.findAll());
        model.addAttribute("columns", bookSearchOptions);
        model.addAttribute("title", "Books with " + bookSearchOptions.get(searchType) + ": " + searchTerm);
        model.addAttribute("searchType",searchType);
        model.addAttribute("searchTerm",searchTerm);
        model.addAttribute("books", books);
        return "books/index";
    }

    //Anitha's code to view searched book
    @GetMapping("/books/view/{bookId}")
    public String viewBook(@PathVariable("bookId")String bookId,Model model) {

        Optional<Book> book = bookRepository.findById(Integer.valueOf(bookId));
        if(book.isPresent()){
            Book selectedBook = book.get();
            model.addAttribute("book", selectedBook);
        }//else throw error
        return "books/view";
    }

    //add a book
    @GetMapping("/add") //http://localhost:8080/books/add
    public String displayAddBookForm(Model model) {

        model.addAttribute(new Book());
        model.addAttribute("title", "Add a book");

        return "books/add";
    }

    @PostMapping("/add")  //http://localhost:8080/books/add
    public String processAddBookForm(@ModelAttribute @Valid Book newBook,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "books/add";
        }
        newBook.setAvailableCopiesToIssue(newBook.getCopies());
        bookRepository.save(newBook);
        return "redirect:";

        //added redirect route to success message
       // return "redirect:/books/add?success";
    }

    //update a book
    @GetMapping("/update") //http://localhost:8080/books/update?bookId=xx
    public String displayUpdateBookForm(@RequestParam Integer bookId, Model model){
        model.addAttribute("title", "Update book");

        Optional<Book> result = bookRepository.findById(bookId);
        Book book = result.get();
        model.addAttribute(book);
        //model.addAttribute(new BooksInventory());

        return "books/update";
    }

    @PostMapping("/update") //http://localhost:8080/books/update
    public String processUpdateBookForm(@ModelAttribute @Valid Book book,@ModelAttribute @Valid BooksInventory booksInventory,
                                    Errors errors,
                                    Model model) {
        if (!errors.hasErrors()) {

            if(booksInventory.getBooksToAdd()>0) {
                int copies=book.getCopies()+ booksInventory.getBooksToAdd();
                int availableCopies=book.getAvailableCopiesToIssue()+booksInventory.getBooksToAdd();

                book.setCopies(copies);
                book.setAvailableCopiesToIssue(availableCopies);
            }
            if(booksInventory.getBooksToRemove()>0){
                int copies= book.getCopies()- booksInventory.getBooksToRemove();

                int availableCopies= book.getAvailableCopiesToIssue()- booksInventory.getBooksToRemove();
                book.setCopies(copies);
                book.setAvailableCopiesToIssue(availableCopies);
            }
            bookRepository.save(book);
            //return "redirect:detail?bookId=" + book.getId();

//just for now i redirected to view page.
            return "redirect:/books/";
        }
        //return "redirect:update";
        return "redirect:/books/update";

    }

    @GetMapping("/inventory") //http://localhost:8080/books/update?bookId=xx
    public String displayInventoryForm(@RequestParam Integer bookId, Model model){
        model.addAttribute("title", "Manage Book Inventory");

        Optional<Book> result = bookRepository.findById(bookId);
        Book book = result.get();
        model.addAttribute(book);
        model.addAttribute("bookId",bookId);

        model.addAttribute(new BooksInventory());

        return "books/inventory";
    }
    @PostMapping("/inventory")
    public String processInventoryBookForm(@ModelAttribute @Valid BooksInventory booksInventory,
                                           @RequestParam(required = true) Integer bookId,
                                            Errors errors,
                                            Model model) {
        Optional<Book> resultBook = bookRepository.findById(bookId);
        Book book = resultBook.get();

        if (!errors.hasErrors()) {

            if(booksInventory.getBooksToAdd()>0) {
                int copies=book.getCopies()+ booksInventory.getBooksToAdd();
                int availableCopies=book.getAvailableCopiesToIssue()+booksInventory.getBooksToAdd();

                book.setCopies(copies);
                book.setAvailableCopiesToIssue(availableCopies);
            }
            if(booksInventory.getBooksToRemove()>0){
                int copies= book.getCopies()- booksInventory.getBooksToRemove();

                int availableCopies= book.getAvailableCopiesToIssue()- booksInventory.getBooksToRemove();
                book.setCopies(copies);
                book.setAvailableCopiesToIssue(availableCopies);
            }
            bookRepository.save(book);
            //return "redirect:detail?bookId=" + book.getId();

//just for now i redirected to view page.
            return "redirect:/books/";
        }
        //return "redirect:update";
        return "redirect:/books/inventory";

    }
    //details of the book
    @GetMapping("detail") //http://localhost:8080/books/detail?bookId=xx
    public String displayBookDetail(@RequestParam Integer bookId, Model model){
        Optional<Book> result = bookRepository.findById(bookId);
        Book book = result.get();
        model.addAttribute(book);
        model.addAttribute("title", "Book Details");
        return "books/detail";
    }



    //delete
    @GetMapping("delete") //http://localhost:8080/books/delete?bookId=xx
    public String displayBookDelete(@RequestParam(required = true) Integer bookId, Model model){
        Optional<Book> result = bookRepository.findById(bookId);
        Book book = result.get();
        model.addAttribute(book);
        model.addAttribute("title", "Delete book");

        return "books/delete";
    }

    @PostMapping("delete") //http://localhost:8080/books/delete?bookId=xxxx
    public String processDeleteBook(@RequestParam(required = true) Integer bookId) {
        if (bookId != null) {
            //Anitha: add one line code to remove the hold if there is a hold already on the book by the student
            removeHoldWhileCheckingOut(bookId);
            bookRepository.deleteById(bookId);
        }
        return "redirect:";
    }

    //checkout
    @GetMapping("checkout") //http://localhost:8080/books/checkout
    public String displayCheckout(Model model, @RequestParam(required = true) Integer bookId){
        BookCheckout bookCheckout =new BookCheckout();
        Calendar c= Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        Date date=c.getTime();
        bookCheckout.setExpectedReturnDate(date);
        model.addAttribute(bookCheckout);
        model.addAttribute("bookId",bookId);
        model.addAttribute("title", "Checkout book");
        model.addAttribute("allstudents",studentRepository.findAll());

        return "books/checkout";
    }

    @PostMapping("checkout") //http://localhost:8080/books/checkout?bookId=xxxx
    public String processCheckout(@ModelAttribute @Valid BookCheckout newBookCheckout,@RequestParam(required = true) Integer bookId, @RequestParam(required = true) Integer studentId,
                                  Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "books/checkout";
        }
        newBookCheckout.setCheckout(true);
        Optional<Book> resultBook = bookRepository.findById(bookId);
        Book book = resultBook.get();
        if(book.getAvailableCopiesToIssue() == 0){
            String errMsg = "Book "+bookId+ " not available to checkout";
            BookCheckout bookCheckout =new BookCheckout();
            Calendar c= Calendar.getInstance();
            c.add(Calendar.DATE, 30);
            Date date=c.getTime();
            bookCheckout.setExpectedReturnDate(date);
            model.addAttribute(bookCheckout);
            model.addAttribute("bookId",bookId);
            model.addAttribute("title", "Checkout book");
            model.addAttribute("allstudents",studentRepository.findAll());
            model.addAttribute("errorMsg", errMsg);

            return "books/checkout";
        }
        newBookCheckout.setBook(book);
        //student code later
        Optional<Student> resultStudent = studentRepository.findById(studentId);
        Student student = resultStudent.get();
        newBookCheckout.setStudent(student);
        //newBookCheckout.setStudentId(studentId);
        newBookCheckout.setIssueDate(new Date());
        int availableCopies = book.getAvailableCopiesToIssue();
        availableCopies--;
        bookCheckoutRepository.save(newBookCheckout);
        book.setAvailableCopiesToIssue(availableCopies);
        bookRepository.save(book);
        return "redirect:";
    }

    //Checkin
    @GetMapping("checkin") //http://localhost:8080/books/checkin
    public String displayCheckin(Model model, @RequestParam(required = true) Integer bookId){
        model.addAttribute("bookId",bookId);
        model.addAttribute("title", "Checkin book");
        model.addAttribute("allstudents",studentRepository.findAll());
        model.addAttribute("allbooks", bookRepository.findAll());

        return "books/checkin";
    }

    @PostMapping("checkin") //http://localhost:8080/books/checkout?bookId=xxxx
    public String processCheckin(Model model, @RequestParam(required = true) Integer bookId, @RequestParam(required = true) Integer studentId) {
        Optional<Book> result = bookRepository.findById(bookId);
        Book book = result.get();
        BookCheckout bookCheckout=bookCheckoutRepository.findByBookIdAndStudentIdAndIsCheckout(bookId,studentId,true);
        if(bookCheckout ==null){
           String message="Book "+bookId+" is not checked out by student "+studentId;
           model.addAttribute("errorMsg", message);
            model.addAttribute("bookId",bookId);
            model.addAttribute("title", "Checkin book");
            model.addAttribute("allstudents",studentRepository.findAll());
            return "books/checkin";

        }
        bookCheckout.setCheckout(false);
        bookCheckout.setActualReturnDate(new Date());
        int availableCopies = book.getAvailableCopiesToIssue();
        availableCopies++;
        bookCheckoutRepository.save(bookCheckout);
        book.setAvailableCopiesToIssue(availableCopies);
        bookRepository.save(book);
        return "redirect:";
    }

    //Anitha code for hold a book
    @GetMapping("hold/{bookId}")
    public String displayHold(@PathVariable("bookId")Integer bookId,Model model) {
        StudentBookDto studentBookDto = new StudentBookDto();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.get();
        studentBookDto.setBookId(bookId);
        studentBookDto.setBookName(book.getName());
        model.addAttribute("studentBookDto",studentBookDto);//for displaying bookid and student id
        model.addAttribute("allstudents",studentRepository.findAll());//for displaying drop down for student
        model.addAttribute("bookHoldList",getStudentBookList(bookId));//displaying list of hold on bottom
        return "books/hold";
    }

    //Anitha code for hold a book
    @PostMapping("hold")
    public String processHold(@ModelAttribute @Valid StudentBookDto studentBookDto,
                              Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "books/hold";
        }
        if(isStudentAlreadyHoldTheBook(studentBookDto.getBookId(),studentBookDto.getStudentId())){
            model.addAttribute("errorMsg", "Student already has the book on hold");
            model.addAttribute("bookHoldList",getStudentBookList(studentBookDto.getBookId()));
            model.addAttribute("allstudents",studentRepository.findAll());
            return "books/hold";
        }
        //creating object and setting field value
        int studentId = studentBookDto.getStudentId();
        Optional<Book> result = bookRepository.findById(studentBookDto.getBookId());
        Book book = result.get();
        StudentBook studentBook = new StudentBook();
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Student student = studentOptional.get();
        StudentBookId studentBookId = new StudentBookId();
        studentBookId.setBookId(book.getId());
        studentBookId.setStudentId(student.getId());
        studentBook.setId(studentBookId);
        studentBook.setStudent(student);
        studentBook.setBook(book);
        studentBook.setIssueDate(studentBookDto.getIssueDate());
        studentBook.setHeldUntilDate(studentBookDto.getHeldUntilDate());
        studentBook.setCheckOut(false);
        studentBook.setHold(true);
        //reduces copies available
        int availableCopies = book.getAvailableCopiesToIssue();
        studentBookRepository.save(studentBook);
        availableCopies--;
        book.setAvailableCopiesToIssue(availableCopies);//book table
        bookRepository.save(book);
        return "redirect:";
    }

    //unholding
    @GetMapping("unhold")
    public String processUnHold(@RequestParam(required = true) Integer bookId,
                                @RequestParam(required = true) Integer studentId,
                              Model model) {
        removeHold(bookId,studentId);
        Optional<Book> result = bookRepository.findById(bookId);
        Book book = result.get();
        //increase copies available
        int availableCopies = book.getAvailableCopiesToIssue();
        availableCopies++;
        book.setAvailableCopiesToIssue(availableCopies);
        bookRepository.save(book);
        return "redirect:";

    }

    //checks bookId is there and add to studentBookList
    private List<StudentBook> getStudentBookList(int bookId){
        List<StudentBook> studentBookList = new ArrayList<>();
        Iterable<StudentBook> studentBookIterable = studentBookRepository.findAll();
        for (StudentBook studentBook : studentBookIterable){
            if(studentBook.getBook().getId() == bookId){
                studentBookList.add(studentBook);
            }
        }
        return studentBookList;
    }
    //checks if students have that book on hold already
    private boolean isStudentAlreadyHoldTheBook(int bookId, int studentId){
        Iterable<StudentBook> studentBookList = studentBookRepository.findAll();
        boolean isStudentAlreadyHoldTheBook = false;
        for (StudentBook studentBook : studentBookList){
            if(studentBook.getBook().getId() == bookId && studentBook.getStudent().getId() == studentId ){
                isStudentAlreadyHoldTheBook = true;
            }
        }
        return isStudentAlreadyHoldTheBook;
    }

    //removes the records
    private void removeHold(int bookId, int studentId){
        Iterable<StudentBook> studentBookList = studentBookRepository.findAll();
        for (StudentBook studentBook : studentBookList){
            if(studentBook.getBook().getId() == bookId && studentBook.getStudent().getId() == studentId ){
                studentBookRepository.delete(studentBook);
            }
        }
    }

    //Anitha code for deleting book on hold
    //gets all record from studentBook and check if bookId is equals to getBook and getId then it will delete that book
    private void removeHoldWhileCheckingOut(int bookId){
        Iterable<StudentBook> studentBookList = studentBookRepository.findAll();
        for (StudentBook studentBook : studentBookList){
            if(studentBook.getBook().getId() == bookId){
                studentBookRepository.delete(studentBook);
            }
        }

    }
}
