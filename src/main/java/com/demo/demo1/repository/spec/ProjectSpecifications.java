package com.demo.demo1.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import com.demo.demo1.entity.Project;
import com.demo.demo1.entity.ProjectAssignment;
import com.demo.demo1.entity.ProjectStatus;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

/**
 * Reusable, composable Specification builders for Project search/filtering.
 * All filtering is pushed to the database via JPA Criteria API - no in-memory filtering.
 */
public class ProjectSpecifications {

    private ProjectSpecifications() {
    }

    /** Matches project name OR project code (case-insensitive, partial match). */
    public static Specification<Project> hasNameOrCodeContaining(String term) {
        String pattern = "%" + term.toLowerCase() + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("name")), pattern),
                cb.like(cb.lower(root.get("pCode")), pattern)
        );
    }

    public static Specification<Project> hasStatus(ProjectStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    /** Projects that have a ProjectAssignment for the given engineer id. */
    public static Specification<Project> hasEngineerAssigned(Integer engineerId) {
        return (root, query, cb) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<ProjectAssignment> assignmentRoot = subquery.from(ProjectAssignment.class);
            subquery.select(assignmentRoot.get("project").get("id"))
                    .where(cb.equal(assignmentRoot.get("engineer").get("id"), engineerId));
            return root.get("id").in(subquery);
        };
    }
}