/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appengine.springboot;

// [START gae_java11_helloworld]
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import com.example.appengine.springboot.BookDto;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class SpringbootApplication {

  @Autowired
  BookService bookService;

  public static void main(String[] args) {
    SpringApplication.run(SpringbootApplication.class, args);
  }

  @GetMapping("/books/{id}")
  public BookDto getBook(@PathVariable long id) {
    return bookService.getById(id);
  }

  @GetMapping("/books/")
  public List<BookDto> getBooks(@RequestParam(name="finished", required = false) Optional<Boolean> finished) {
    return bookService.getAll(finished);
  }

  @PutMapping("/books/")
  public BookDto createBook(@RequestBody BookDto dto) {
    return bookService.create(dto);
  }

  @PostMapping("/books/{id}")
  public BookDto save(@RequestBody BookDto dto, @PathVariable long id) {
    return bookService.save(dto, id);
  }
}
// [END gae_java11_helloworld]
