package NowNewsBlog.service;

import static NowNewsBlog.model.VoteType.UPVOTE;

import NowNewsBlog.dto.VoteDto;
import NowNewsBlog.exceptions.NowNewsBlogException;
import NowNewsBlog.exceptions.PostNotFoundException;
import NowNewsBlog.model.Post;
import NowNewsBlog.model.Vote;
import NowNewsBlog.repository.PostRepository;
import NowNewsBlog.repository.VoteRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VoteService {

  private final VoteRepository voteRepository;
  private final PostRepository postRepository;
  private final AuthService authService;

  @Transactional
  public void vote(VoteDto voteDto) {
    Post post = postRepository.findById(voteDto.getPostId())
        .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
    Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
    if (voteByPostAndUser.isPresent() &&
        voteByPostAndUser.get().getVoteType()
            .equals(voteDto.getVoteType())) {
      throw new NowNewsBlogException("You have already "
          + voteDto.getVoteType() + "'d for this post");
    }
    if (UPVOTE.equals(voteDto.getVoteType())) {
      post.setVoteCount(post.getVoteCount() + 1);
    } else {
      post.setVoteCount(post.getVoteCount() - 1);
    }
    voteRepository.save(mapToVote(voteDto, post));
    postRepository.save(post);
  }

  private Vote mapToVote(VoteDto voteDto, Post post) {
    return Vote.builder()
        .voteType(voteDto.getVoteType())
        .post(post)
        .user(authService.getCurrentUser())
        .build();
  }
}