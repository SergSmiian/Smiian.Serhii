package com.service;

import com.model.Auto;
import com.repository.AutoRepository;
import com.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;

public class AutoService extends VehicleService<Auto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private final AutoRepository autoRepository;

    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    @Override
    CrudRepository<Auto> getRepository() {
        return autoRepository;
    }

    @Override
    Auto createVehicle() {
        return new Auto(
                "Model-" + getRandom().nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(getRandom().nextDouble(1000.0)),
                "Model-" + getRandom().nextInt(1000)
        );
    }

    public void optionalExamples() {
        final Auto auto = createAndSave(1).get(0);
        final String id = auto.getId();

//        simpleCheck(id);
        isPresent(id);
        ifPresent(id);
        orElse(id);
        orElseThrow(id);
        or(id);
        orElseGet(id);
        filter(id);
        map(id);
        ifPresentOrElse(id);
    }
        /*private void simpleCheck(String id) { EXAMPLE
        final Auto auto = autoRepository.getById(id);
        if (auto != null) {
            System.out.println(auto.getModel());
        }
    }*/

    private void isPresent(String id) {
        final Optional<Auto> autoOptional1 = autoRepository.findById(id);
        if (autoOptional1.isPresent()) {
            System.out.println(autoOptional1.get().getModel());
        }

        final Optional<Auto> autoOptional2 = autoRepository.findById("123");
        if (autoOptional2.isPresent()) {
            System.out.println(autoOptional2.get().getModel());
        }

        if (autoOptional2.isEmpty()) {
            System.out.println("Auto with id \"123\" not found");
        }
    }

    private void ifPresent(String id) {
        autoRepository.findById(id).ifPresent(auto -> {
            System.out.println(auto.getModel());
        });

        autoRepository.findById("123").ifPresent(auto -> {
            System.out.println(auto.getModel());
        });
    }

    private void orElse(String id) {
        final Auto auto1 = autoRepository.findById(id).orElse(createOne());
        System.out.println(auto1.getModel());

        System.out.println("~".repeat(10));

        final Auto auto2 = autoRepository.findById("123").orElse(createOne());
        System.out.println(auto2.getModel());
    }

    private void orElseThrow(String id) {
        final Auto auto1 = autoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find auto with id " + id));
        System.out.println(auto1.getModel());

        System.out.println("~".repeat(10));

        try {
            final Auto auto2 = autoRepository.findById("123")
                    .orElseThrow(() -> new IllegalArgumentException("Cannot find auto with id " + "123"));
            System.out.println(auto2.getModel());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void or(String id) {
        final Optional<Auto> auto1 = autoRepository.findById(id).or(() -> Optional.of(createOne()));
        auto1.ifPresent(auto -> {
            System.out.println(auto.getModel());
        });

        System.out.println("~".repeat(10));

        final Optional<Auto> auto2 = autoRepository.findById("123").or(() -> Optional.of(createOne()));
        auto2.ifPresent(auto -> {
            System.out.println(auto.getModel());
        });
    }

    private void orElseGet(String id) {
        final Auto auto = autoRepository.findById(id).orElse(createOne());
        final Auto auto1 = autoRepository.findById(id).orElseGet(() -> createOne());
        System.out.println(auto1.getModel());

        System.out.println("~".repeat(10));

        final Auto auto2 = autoRepository.findById("123").orElseGet(() -> {
            System.out.println("Cannot find auto with id " + "123");
            return createOne();
        });
        System.out.println(auto2.getModel());
    }

    private void filter(String id) {
        autoRepository.findById(id)
                .filter(auto -> !auto.getBodyType().equals(""))
                .ifPresent(auto -> {
                    System.out.println(auto.getModel());
                });

        autoRepository.findById(id)
                .filter(auto -> auto.getBodyType().equals(""))
                .ifPresent(auto -> {
                    System.out.println(auto.getModel());
                });
    }

    private void map(String id) {
        autoRepository.findById(id)
                .map(auto -> auto.getModel())
                .ifPresent(model -> {
                    System.out.println(model);
                });
    }

    private void ifPresentOrElse(String id) {
        autoRepository.findById(id).ifPresentOrElse(
                auto -> {
                    System.out.println(auto.getModel());
                },
                () -> {
                    System.out.println("Cannot find auto with id " + "123");
                }
        );

        autoRepository.findById("123").ifPresentOrElse(
                auto -> {
                    System.out.println(auto.getModel());
                },
                () -> {
                    System.out.println("Cannot find auto with id " + "123");
                }
        );
    }

    private Auto createOne() {
        return new Auto(
                "Model new",
                getRandomManufacturer(),
                BigDecimal.valueOf(getRandom().nextDouble(1000.0)),
                "Model-" + getRandom().nextInt(1000)
        );
    }


}
