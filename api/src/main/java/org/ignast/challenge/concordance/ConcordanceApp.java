package org.ignast.challenge.concordance;

import java.nio.file.Path;
import java.util.Optional;
import lombok.val;
import org.ignast.challenge.concordance.controller.ConcordanceController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConcordanceApp {

    public static void main(final String[] args) {
        val controller = getController();

        getFilePath(args)
            .map(controller::generateConcordance)
            .map(Path::toString)
            .ifPresentOrElse(
                System.out::println,
                () ->
                    System.out.println("Input file required to be provided as a first command line argument")
            );
    }

    private static Optional<Path> getFilePath(String[] args) {
        if (args.length > 0) {
            return Optional.of(Path.of(args[0]));
        } else {
            return Optional.empty();
        }
    }

    private static ConcordanceController getController() {
        val context = new AnnotationConfigApplicationContext();
        context.scan(ConcordanceApp.class.getPackageName());
        context.refresh();
        ConcordanceController controller = context.getBean(ConcordanceController.class);
        return controller;
    }
}
