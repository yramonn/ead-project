package com.ead.courseservice.repositories;

import com.ead.courseservice.models.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel> {

    @Query(value="select * from tb_modules where course_course_id = :courseId", nativeQuery = true)
    List<ModuleModel> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

    @Query(value="select * from tb_modules where course_course_id = :courseId and module_id = :moduleId", nativeQuery = true)
    Optional<ModuleModel> findModuleIntoCourse(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);


//--> Example EntityGraph
//    @EntityGraph(attributePaths = {"course"})
//    ModuleModel findByTitle(String title);

//--> Example Modifying
//    @Modifying
//    @Query(value = "delete from tb_modules where course_course_id = :courseId", nativeQuery = true)
//    void deleteModulesIntoCourse(@Param("courseId") UUID courseId);

}
