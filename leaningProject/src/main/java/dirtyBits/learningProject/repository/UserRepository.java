package dirtyBits.learningProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dirtyBits.learningProject.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
