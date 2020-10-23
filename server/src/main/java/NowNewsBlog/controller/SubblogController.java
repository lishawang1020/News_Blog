package NowNewsBlog.controller;

import NowNewsBlog.dto.SubblogDto;
import NowNewsBlog.service.SubblogService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subblog")
@AllArgsConstructor
@Slf4j
public class SubblogController {

  private final SubblogService subblogService;

  @PostMapping
  public ResponseEntity<SubblogDto> CreateSubblog(@RequestBody SubblogDto subblogDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(subblogService.save(subblogDto));
  }

  @GetMapping
  public ResponseEntity<List<SubblogDto>> getAllSubblog() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(subblogService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SubblogDto> getSubblog(@PathVariable Long id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(subblogService.getSubblog(id));
  }
}
