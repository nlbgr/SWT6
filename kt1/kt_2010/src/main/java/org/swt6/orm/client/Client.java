package org.swt6.orm.client;

import org.swt6.orm.domain.Project;
import org.swt6.orm.utils.JpaUtil;

// https://drive.google.com/file/d/1L6uhkcHyIYmPF_HJX871fFb8n7ubBPuL/view?usp=drive_link
public class Client {

    public static void printBugListFromProjects(Long projectId) {
        JpaUtil.executeInTransaction(em -> {
            Project p = em.find(Project.class, projectId);

            System.out.println("Bugs for Project:");

            p.getMitarbeiters().forEach(m -> {
                System.out.println("  Employee: " + m.getName());
                m.getBugs().forEach(b -> {
                    System.out.println("    " + b.getDescription());
                });
            });
        });
    }

    public static void main(String[] args) {

    }
}
