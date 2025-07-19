package ch.kata.kata_02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.ApplicationModule;

@Slf4j
@ApplicationModule
public class SharedApplication {

    public static void main(String[] args) {
        log.info("Shared module initialized");
    }
}
