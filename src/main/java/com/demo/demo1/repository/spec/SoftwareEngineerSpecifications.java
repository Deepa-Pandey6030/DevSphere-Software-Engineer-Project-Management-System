package com.demo.demo1.repository.spec;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.demo.demo1.entity.Leave;
import com.demo.demo1.entity.LeaveStatus;
import com.demo.demo1.entity.ProjectAssignment;
import com.demo.demo1.entity.SkillProficiency;
import com.demo.demo1.entity.SoftwareEngineer;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

/**
 * Reusable, composable Specification builders for SoftwareEngineer search/filtering.
 * All filtering is pushed to the database via JPA Criteria API - no in-memory filtering.
 */
public class SoftwareEngineerSpecifications {

    private SoftwareEngineerSpecifications() {
    }

    public static Specification<SoftwareEngineer> hasNameContaining(String name) {
        String pattern = "%" + name.toLowerCase() + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("firstName")), pattern),
                cb.like(cb.lower(root.get("lastName")), pattern)
        );
    }

    public static Specification<SoftwareEngineer> hasEmailContaining(String email) {
        String pattern = "%" + email.toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("email")), pattern);
    }

    public static Specification<SoftwareEngineer> hasDepartmentId(Integer departmentId) {
        return (root, query, cb) -> cb.equal(root.get("department").get("id"), departmentId);
    }

    public static Specification<SoftwareEngineer> hasEmploymentStatus(String status) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get("employmentStatus")), status.toLowerCase());
    }

    /** Engineers who have a SkillProficiency for the given technology id. */
    public static Specification<SoftwareEngineer> hasTechnologyId(Integer technologyId) {
        return (root, query, cb) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<SkillProficiency> skillRoot = subquery.from(SkillProficiency.class);
            subquery.select(skillRoot.get("engineer").get("id"))
                    .where(cb.equal(skillRoot.get("technology").get("id"), technologyId));
            return root.get("id").in(subquery);
        };
    }

    /** Engineers who have a ProjectAssignment for the given project id. */
    public static Specification<SoftwareEngineer> hasProjectId(Integer projectId) {
        return (root, query, cb) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<ProjectAssignment> assignmentRoot = subquery.from(ProjectAssignment.class);
            subquery.select(assignmentRoot.get("engineer").get("id"))
                    .where(cb.equal(assignmentRoot.get("project").get("id"), projectId));
            return root.get("id").in(subquery);
        };
    }

    /**
     * Engineers who are AVAILABLE on the given date, defined as:
     *   1. no APPROVED leave covering the date, AND
     *   2. total ProjectAssignment allocation percentage covering the date is less than 100.
     * This is the single availability rule for the system; the same rule (expressed the same
     * way) backs the detailed per-engineer availability check in SoftwareEngineerService.
     */
    public static Specification<SoftwareEngineer> isAvailableOn(LocalDate date) {
        return (root, query, cb) -> {
            Subquery<Integer> leaveSubquery = query.subquery(Integer.class);
            Root<Leave> leaveRoot = leaveSubquery.from(Leave.class);
            leaveSubquery.select(leaveRoot.get("engineer").get("id"))
                    .where(cb.and(
                            cb.equal(leaveRoot.get("status"), LeaveStatus.APPROVED),
                            cb.lessThanOrEqualTo(leaveRoot.get("startDate"), date),
                            cb.greaterThanOrEqualTo(leaveRoot.get("endDate"), date)
                    ));
            Predicate notOnApprovedLeave = cb.not(root.get("id").in(leaveSubquery));

            Subquery<Integer> overAllocatedSubquery = query.subquery(Integer.class);
            Root<ProjectAssignment> assignmentRoot = overAllocatedSubquery.from(ProjectAssignment.class);
            overAllocatedSubquery.select(assignmentRoot.get("engineer").get("id"))
                    .where(cb.and(
                            cb.lessThanOrEqualTo(assignmentRoot.get("assignmentStartDate"), date),
                            cb.or(
                                    cb.isNull(assignmentRoot.get("assignmentEndDate")),
                                    cb.greaterThanOrEqualTo(assignmentRoot.get("assignmentEndDate"), date)
                            )
                    ))
                    .groupBy(assignmentRoot.get("engineer").get("id"))
                    .having(cb.ge(cb.sum(assignmentRoot.get("allocationPercentage")), 100.0));
            Predicate notOverAllocated = cb.not(root.get("id").in(overAllocatedSubquery));

            return cb.and(notOnApprovedLeave, notOverAllocated);
        };
    }
}