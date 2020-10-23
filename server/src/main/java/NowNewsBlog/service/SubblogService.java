package NowNewsBlog.service;

import static java.util.stream.Collectors.toList;

import NowNewsBlog.dto.SubblogDto;
import NowNewsBlog.exceptions.NowNewsBlogException;
import NowNewsBlog.mapper.SubblogMapper;
import NowNewsBlog.model.Subblog;
import NowNewsBlog.repository.SubblogRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class SubblogService {

  private final SubblogRepository subblogRepository;
  private final SubblogMapper subblogMapper;
  private final AuthService authService;

  @Transactional
  public SubblogDto save(SubblogDto subblogDto) {
    Subblog save = subblogRepository.save(subblogMapper.mapDtoToSubblog(subblogDto, authService.getCurrentUser()));
    subblogDto.setId(save.getId());
    return subblogDto;
  }

  @Transactional(readOnly = true)
  public List<SubblogDto> getAll() {
    return subblogRepository.findAll()
        .stream()
        .map(subblogMapper::mapSubblogToDto)
        .collect(toList());
  }

  public SubblogDto getSubblog(Long id) {
    Subblog subblog = subblogRepository.findById(id)
        .orElseThrow(() -> new NowNewsBlogException("No subblog found with id " + id));
    return subblogMapper.mapSubblogToDto(subblog);
  }
}
