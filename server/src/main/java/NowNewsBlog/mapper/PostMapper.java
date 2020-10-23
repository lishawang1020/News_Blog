package NowNewsBlog.mapper;

import static NowNewsBlog.model.VoteType.DOWNVOTE;
import static NowNewsBlog.model.VoteType.UPVOTE;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import NowNewsBlog.dto.PostRequest;
import NowNewsBlog.dto.PostResponse;
import NowNewsBlog.model.Post;
import NowNewsBlog.model.Subblog;
import NowNewsBlog.model.User;
import NowNewsBlog.model.Vote;
import NowNewsBlog.model.VoteType;
import NowNewsBlog.repository.CommentRepository;
import NowNewsBlog.repository.VoteRepository;
import NowNewsBlog.service.AuthService;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private VoteRepository voteRepository;
  @Autowired
  private AuthService authService;

  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
  @Mapping(target = "description", source = "postRequest.description")
  @Mapping(target = "subblog", source = "subblog")
  @Mapping(target = "user", source = "user")
  @Mapping(target = "voteCount", constant = "0")
  public abstract Post map(PostRequest postRequest, Subblog subblog, User user);

  @Mapping(target = "id", source = "postId")
  @Mapping(target = "subblogName", source = "subblog.name")
  @Mapping(target = "userName", source = "user.username")
  @Mapping(target = "commentCount", expression = "java(commentCount(post))")
  @Mapping(target = "duration", expression = "java(getDuration(post))")
  @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
  @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
  public abstract PostResponse mapToDto(Post post);

  Integer commentCount(Post post) {
    return commentRepository.findByPost(post).size();
  }

  String getDuration(Post post) {
    return TimeAgo.using(post.getCreatedDate().toEpochMilli());
  }

  boolean isPostUpVoted(Post post) {
    return checkVoteType(post, UPVOTE);
  }

  boolean isPostDownVoted(Post post) {
    return checkVoteType(post, DOWNVOTE);
  }

  private boolean checkVoteType(Post post, VoteType voteType) {
    if (authService.isLoggedIn()) {
      Optional<Vote> voteForPostByUser =
          voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
              authService.getCurrentUser());
      return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
          .isPresent();
    }
    return false;
  }
}
