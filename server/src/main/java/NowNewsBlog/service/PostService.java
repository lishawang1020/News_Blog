package NowNewsBlog.service;

import static java.util.stream.Collectors.toList;

import NowNewsBlog.dto.PostRequest;
import NowNewsBlog.dto.PostResponse;
import NowNewsBlog.exceptions.PostNotFoundException;
import NowNewsBlog.exceptions.SubblogNotFoundException;
import NowNewsBlog.mapper.PostMapper;
import NowNewsBlog.model.Post;
import NowNewsBlog.model.Subblog;
import NowNewsBlog.model.User;
import NowNewsBlog.repository.PostRepository;
import NowNewsBlog.repository.SubblogRepository;
import NowNewsBlog.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
  private final PostRepository postRepository;
  private final SubblogRepository subblogRepository;
  private final UserRepository userRepository;
  private final AuthService authService;
  private final PostMapper postMapper;

  public void save(PostRequest postRequest) {
    Subblog subblog = subblogRepository.findByName(postRequest.getSubblogName())
        .orElseThrow(() -> new SubblogNotFoundException(postRequest.getSubblogName()));
    postRepository.save(postMapper.map(postRequest, subblog, authService.getCurrentUser()));
  }

  @Transactional(readOnly = true)
  public PostResponse getPost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new PostNotFoundException(id.toString()));
    return postMapper.mapToDto(post);
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts() {
    return postRepository.findAll()
        .stream()
        .map(postMapper::mapToDto)
        .collect(toList());
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsBySubblog(Long subblogId) {
    Subblog subblog = subblogRepository.findById(subblogId)
        .orElseThrow(() -> new SubblogNotFoundException(subblogId.toString()));
    List<Post> posts = postRepository.findAllBySubblog(subblog);
    return posts.stream().map(postMapper::mapToDto).collect(toList());
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
    return postRepository.findByUser(user)
        .stream()
        .map(postMapper::mapToDto)
        .collect(toList());
  }
}
