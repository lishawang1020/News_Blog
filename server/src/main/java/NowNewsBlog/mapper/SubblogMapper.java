package NowNewsBlog.mapper;

import NowNewsBlog.dto.SubblogDto;
import NowNewsBlog.model.Post;
import NowNewsBlog.model.Subblog;
import NowNewsBlog.model.User;
import java.util.List;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel =  "spring")
public interface SubblogMapper {

  @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subblog.getPosts()))")
  SubblogDto mapSubblogToDto(Subblog subblog);

  default Integer mapPosts(List<Post> numberOfPosts) {return numberOfPosts.size();}

  @InheritConfiguration
  @Mapping(target = "posts", ignore = true)
  @Mapping(target = "user", source = "user")
  Subblog mapDtoToSubblog(SubblogDto subblogDto, User user);
}
