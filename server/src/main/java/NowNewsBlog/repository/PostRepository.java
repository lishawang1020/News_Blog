package NowNewsBlog.repository;

import NowNewsBlog.model.Post;
import NowNewsBlog.model.Subblog;
import NowNewsBlog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubblog(Subblog subblog);
    List<Post> findByUser(User user);
}
