package NowNewsBlog.repository;

import NowNewsBlog.model.Subblog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubblogRepository extends JpaRepository<Subblog, Long> {

    Optional<Subblog> findByName(String subredditName);
}
